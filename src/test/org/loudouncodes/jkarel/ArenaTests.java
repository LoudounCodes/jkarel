// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
package org.loudouncodes.jkarel;

import static org.junit.Assert.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import org.loudouncodes.jkarel.xml.Attributes;

public class ArenaTests {
    private static class TestMap implements MapDataSource {
        int width = 8, height = 6;
        List<Wall> walls = new ArrayList<>();
        Map<Location, BeeperStack> beepers = new HashMap<>();
        public int getWidth() { return width; }
        public int getHeight() { return height; }
        public List<Wall> getWalls() { return walls; }
        public Map<Location, BeeperStack> getBeepers() { return beepers; }
    }

    private void assertRejectedMap(MapDataSource source, Class<? extends RuntimeException> type) {
        try {
            Arena.loadMap(source);
            fail("Expected rejected map data");
        } catch (RuntimeException expected) {
            assertTrue(expected.toString(), type.isInstance(expected));
        }
    }

    private void assertInvalidAction(Runnable action) {
        try {
            action.run();
            fail("Expected an invalid-action exception");
        } catch (IllegalStateException expected) {
            assertNotNull(expected.getMessage());
        }
    }

    @Before
    public void reset() {
        Arena.model = new ArenaModel();
    }

    @Test
    public void sensorsCheckTheirOwnSideInEveryDirection() {
        for (Direction facing : Direction.values()) {
            for (Direction blocked : Direction.values()) {
                Arena.model = new ArenaModel();
                Robot robot = new Robot(4, 4, facing, 0);
                switch (blocked) {
                    case NORTH: Arena.addNorthWall(4, 4); break;
                    case SOUTH: Arena.addSouthWall(4, 4); break;
                    case EAST: Arena.addEastWall(4, 4); break;
                    case WEST: Arena.addWestWall(4, 4); break;
                }
                assertEquals(facing != blocked, robot.frontIsClear());
                assertEquals(facing.left() != blocked, robot.leftIsClear());
                assertEquals(facing.right() != blocked, robot.rightIsClear());
                assertEquals(facing.behind() != blocked, robot.backIsClear());
            }
        }
    }

    @Test
    public void droppedStackStaysAtOriginalLocationAfterRobotMoves() {
        Robot robot = new Robot(3, 3, Direction.EAST, 2);
        robot.setColor(Color.BLUE);
        robot.putBeeper();
        robot.putBeeper();
        robot.move();
        BeeperStack stack = Arena.getModel().getBeepers().get(new Location(3, 3));
        assertNotNull(stack);
        assertEquals(2, stack.getBeepers());
        assertEquals(Color.BLUE, stack.getColor());
        assertFalse(robot.nextToABeeper());
        robot.turnLeft(); robot.turnLeft(); robot.move();
        robot.pickBeeper();
        assertEquals(Color.BLUE, Arena.getModel().getBeepers().get(new Location(3, 3)).getColor());
        robot.pickBeeper();
        assertFalse(robot.nextToABeeper());
        assertEquals(2, robot.getBeepers());
    }

    @Test
    public void robotAndBeeperRenderTheirColors() {
        Robot robot = new Robot(3, 3, Direction.NORTH, 1);
        robot.setColor(Color.BLUE); robot.putBeeper();
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(80, 80,
            java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D graphics = image.createGraphics();
        robot.render(graphics, 40, 40);
        graphics.dispose();
        boolean hasBlue = false;
        for (int y = 0; y < 80; y++)
            for (int x = 0; x < 80; x++)
                if (image.getRGB(x, y) == Color.BLUE.getRGB()) hasBlue = true;
        assertTrue("Robot icon must contain the selected color", hasBlue);
        graphics = image.createGraphics();
        Arena.getModel().getBeepers().get(new Location(3, 3)).render(graphics, 40, 40);
        graphics.dispose();
        assertEquals(Color.BLUE.getRGB(), image.getRGB(40, 32));
    }

    @Test
    public void darkBeeperColorsHaveVisibleCountLabels() {
        for (java.awt.Color color : List.of(Color.RED, Color.BLUE)) {
            BeeperStack stack = new BeeperStack(3, 3, 2);
            stack.setColor(color);
            java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(40, 40,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D graphics = image.createGraphics();
            stack.render(graphics, 20, 20); graphics.dispose();
            boolean hasWhite = false;
            for (int y = 0; y < 40; y++)
                for (int x = 0; x < 40; x++)
                    if (image.getRGB(x, y) == Color.WHITE.getRGB()) hasWhite = true;
            assertTrue("Count label must contrast with the stack", hasWhite);
        }
    }

    @Test
    public void latestColoredDropColorsWholeStack() {
        Robot red = new Robot(3, 3, Direction.EAST, 1);
        Robot blue = new Robot(3, 3, Direction.EAST, 1);
        red.setColor(Color.RED); blue.setColor(Color.BLUE);
        red.putBeeper(); blue.putBeeper();
        assertEquals(2, Arena.getModel().getBeepers().get(new Location(3, 3)).getBeepers());
        assertEquals(Color.BLUE, Arena.getModel().getBeepers().get(new Location(3, 3)).getColor());
    }

    @Test
    public void pickupAndDropNotifyCorrectActionAfterInventoryChanges() {
        Robot robot = new Robot(3, 3, Direction.EAST, 1);
        List<String> actions = new ArrayList<String>();
        ArenaListener listener = new ArenaListener() {
            public void beeperAdded(BeeperStack stack) { actions.add("added:" + stack.getBeepers()); }
            public void beeperDropped(Robot r) { actions.add("drop:" + r.getBeepers()); }
            public void beeperPickedUp(Robot r) { actions.add("pick:" + r.getBeepers()); }
            public void robotMoved(Robot r) { actions.add("move:" + r.getX()); }
        };
        Arena.addListener(listener);
        robot.putBeeper(); robot.pickBeeper(); robot.move();
        assertEquals(List.of("added:1", "drop:0", "pick:1", "move:4"), actions);
        Arena.removeListener(listener);
        robot.move();
        assertEquals(4, actions.size());
    }

    @Test
    public void listenerCanRemoveItselfDuringNotification() {
        final int[] calls = {0};
        Arena.addListener(new ArenaListener() {
            public void robotMoved(Robot r) { calls[0]++; Arena.removeListener(this); }
        });
        Robot robot = new Robot(3, 3, Direction.EAST, 0);
        robot.move(); robot.move();
        assertEquals(1, calls[0]);
    }

    @Test
    public void blockedMoveNotifiesCollisionWithoutChangingPosition() {
        Robot robot = new Robot(3, 3, Direction.EAST, 0);
        Arena.addEastWall(3, 3);
        final int[] collisions = {0};
        Arena.addListener(new ArenaListener() {
            public void wallCollision(Wall wall, Robot r) {
                assertEquals(3, wall.getX()); assertSame(robot, r); collisions[0]++;
            }
        });
        assertInvalidAction(robot::move);
        assertEquals(3, robot.getX());
        assertEquals(1, collisions[0]);
    }

    @Test
    public void backupChecksRearWall() {
        Robot robot = new Robot(3, 3, Direction.EAST, 0);
        Arena.addWestWall(3, 3);
        assertInvalidAction(robot::backUp);
        assertEquals(3, robot.getX());
    }

    @Test
    public void infiniteStacksAndInventoriesRemainInfinite() {
        Arena.addBeepers(3, 3, BeeperStack.INFINITY);
        Robot robot = new Robot(3, 3, Direction.EAST, BeeperStack.INFINITY);
        robot.setColor(Color.GREEN);
        robot.putBeeper(); robot.pickBeeper();
        BeeperStack stack = Arena.getModel().getBeepers().get(new Location(3, 3));
        assertEquals(BeeperStack.INFINITY, stack.getBeepers());
        assertEquals(BeeperStack.INFINITY, robot.getBeepers());
        assertEquals(Color.GREEN, stack.getColor());
    }

    @Test
    public void missingBeepersFailWithoutChangingInventory() {
        Robot robot = new Robot(3, 3, Direction.EAST, 0);
        assertInvalidAction(robot::pickBeeper);
        assertInvalidAction(robot::putBeeper);
        assertEquals(0, robot.getBeepers());
    }

    @Test
    public void xmlWallsHonorLengthInBothOrientations() {
        for (String style : List.of("horizontal", "vertical")) {
            Attributes attributes = new Attributes();
            attributes.put("x", "3"); attributes.put("y", "3");
            attributes.put("length", "3"); attributes.put("style", style);
            Arena.getModel().addObject_wall(attributes);
            int orientation = style.equals("horizontal") ? Arena.HORIZONTAL : Arena.VERTICAL;
            for (int i = 0; i < 3; i++)
                assertTrue(Arena.getModel().checkWall(3 + (orientation == Arena.HORIZONTAL ? i : 0),
                                                   3 + (orientation == Arena.VERTICAL ? i : 0), orientation));
        }
    }

    @Test
    public void defaultMapLoadsWithoutClosingAndPreservesRobots() {
        Robot robot = new Robot(3, 3, Direction.EAST, 1);
        final List<String> maps = new ArrayList<String>();
        Arena.addListener(new ArenaListener() {
            public void mapLoaded(String name) { maps.add(name); }
        });
        Arena.addEastWall(3, 3); robot.putBeeper();
        Arena.openDefaultMap();
        assertTrue(robot.frontIsClear()); assertFalse(robot.nextToABeeper());
        assertTrue(Arena.getModel().getRobots().contains(robot));
        assertEquals(List.of(Arena.DEFAULT_MAP), maps);
        assertEquals(new Location(10, 10), Arena.getModel().getSize());
    }

    @Test
    public void userItemEventsAndWallSnapshotsAreSafe() {
        List<Wall> before = Arena.getModel().getWalls();
        final List<String> actions = new ArrayList<String>();
        Arena.addListener(new ArenaListener() {
            public void userItemAdded(Item i) { actions.add("add"); }
            public void userItemDropped(Item i) { actions.add("remove"); }
        });
        Item item = new Item(2, 2) { public void render(Graphics g, int x, int y) {} };
        Arena.getModel().addUserItem(item);
        Arena.getModel().removeUserItem(item);
        Arena.getModel().removeUserItem(item);
        Arena.addNorthWall(3, 3);
        assertEquals(List.of("add", "remove"), actions);
        assertEquals(2, before.size());
        assertEquals(3, Arena.getModel().getWalls().size());
    }

    @Test
    public void describedLevelPreservesPlayersAndReportsInstallationInOrder() {
        Robot player = new Robot(3, 3, Direction.EAST, 2);
        player.setColor(Color.RED);
        Arena.addNorthWall(3, 3);
        Arena.addBeeper(3, 3);
        Arena.getModel().addUserItem(new Item(2, 2) {
            public void render(Graphics g, int x, int y) {}
        });
        TestMap source = new TestMap();
        Wall door = new Wall(3, 3, Arena.VERTICAL) {
            public void render(Graphics g, int x, int y) { g.drawLine(x, y, x, y + 5); }
        };
        source.walls.add(door);
        BeeperStack supplies = new BeeperStack(4, 3, 2);
        supplies.setColor(Color.BLUE);
        source.beepers.put(new Location(4, 3), supplies);
        BeeperStack infinite = new BeeperStack(5, 3, BeeperStack.INFINITY);
        infinite.setColor(Color.GREEN);
        source.beepers.put(new Location(5, 3), infinite);
        List<String> events = new ArrayList<>();
        Arena.addListener(new ArenaListener() {
            public void wallAdded(Wall wall) { events.add("wall"); }
            public void beeperAdded(BeeperStack stack) { events.add("beeper"); }
            public void mapLoaded(String name) {
                assertEquals(TestMap.class.getName(), name);
                assertEquals(new Location(8, 6), Arena.getModel().getSize());
                assertEquals(2, Arena.getModel().getBeepers().size());
                events.add("map");
            }
            public void robotMoved(Robot robot) { events.add("move"); }
        });

        Arena.loadMap(source);
        assertEquals(List.of("wall", "beeper", "beeper", "map"), events);
        assertEquals(List.of(player), Arena.getModel().getRobots());
        assertEquals(new Location(3, 3), player.getLocation());
        assertEquals(Direction.EAST, player.getDirection());
        assertEquals(2, player.getBeepers());
        assertEquals(Color.RED, player.getColor());
        assertTrue(Arena.getModel().getUserItems().isEmpty());
        assertFalse(player.nextToABeeper());
        assertFalse(player.frontIsClear());
        assertSame(door, Arena.getModel().getWalls().get(0));
        assertEquals(Color.BLUE, Arena.getModel().getBeepers().get(new Location(4, 3)).getColor());
        assertEquals(Color.GREEN, Arena.getModel().getBeepers().get(new Location(5, 3)).getColor());
        assertEquals(BeeperStack.INFINITY,
            Arena.getModel().getBeepers().get(new Location(5, 3)).getBeepers());

        Arena.getModel().removeWall(door);
        player.move();
        player.pickBeeper();
        player.putBeeper();
        assertEquals("move", events.get(4));
        assertEquals(2, supplies.getBeepers());
        assertEquals(Color.BLUE, supplies.getColor());
        source.walls.clear(); source.beepers.clear();
        assertEquals(2, Arena.getModel().getBeepers().size());
    }

    @Test
    public void mapDescriptionCanBeReusedAfterItsInstalledBeepersChange() {
        TestMap source = new TestMap();
        Location key = new Location(3, 3);
        BeeperStack supplies = new BeeperStack(3, 3, 2);
        source.beepers.put(key, supplies);
        Robot player = new Robot(3, 3, Direction.NORTH, 0);
        Arena.loadMap(source);
        player.pickBeeper();
        assertEquals(1, Arena.getModel().getBeepers().get(new Location(3, 3)).getBeepers());
        assertEquals(2, supplies.getBeepers());
        Arena.loadMap(source);
        assertEquals(2, Arena.getModel().getBeepers().get(new Location(3, 3)).getBeepers());
        assertNotSame(key, Arena.getModel().getBeepers().keySet().iterator().next());
        assertNotSame(supplies, Arena.getModel().getBeepers().get(key));
        assertEquals(1, player.getBeepers());

        Arena.loadMap(new MapDataSource() {
            public int getWidth() { return 4; }
            public int getHeight() { return 5; }
            public List<Wall> getWalls() { return List.of(); }
            public Map<Location, BeeperStack> getBeepers() { return Map.of(); }
        });
        assertEquals(new Location(4, 5), Arena.getModel().getSize());
        assertTrue(Arena.getModel().getWalls().isEmpty());
        assertTrue(Arena.getModel().getBeepers().isEmpty());
        assertTrue(Arena.getModel().getRobots().contains(player));
    }

    @Test
    public void invalidDescriptionsLeaveTheCurrentLevelAndListenersUntouched() {
        Arena.addEastWall(3, 3);
        Arena.addBeeper(3, 3);
        Robot player = new Robot(3, 3, Direction.EAST, 0);
        List<String> events = new ArrayList<>();
        Arena.addListener(new ArenaListener() {
            public void mapLoaded(String name) { events.add(name); }
            public void wallAdded(Wall wall) { events.add("wall"); }
            public void beeperAdded(BeeperStack stack) { events.add("beeper"); }
        });
        TestMap zeroWidth = new TestMap(); zeroWidth.width = 0;
        TestMap negativeHeight = new TestMap(); negativeHeight.height = -1;
        TestMap wrongKey = new TestMap();
        wrongKey.beepers.put(new Location(4, 3), new BeeperStack(5, 3, 1));
        for (TestMap source : List.of(zeroWidth, negativeHeight, wrongKey))
            assertRejectedMap(source, IllegalArgumentException.class);

        assertRejectedMap(null, NullPointerException.class);
        TestMap nullWalls = new TestMap(); nullWalls.walls = null;
        TestMap nullBeepers = new TestMap(); nullBeepers.beepers = null;
        TestMap nullWall = new TestMap(); nullWall.walls.add(null);
        TestMap nullStack = new TestMap(); nullStack.beepers.put(new Location(4, 3), null);
        for (TestMap source : List.of(nullWalls, nullBeepers, nullWall, nullStack))
            assertRejectedMap(source, NullPointerException.class);

        assertRejectedMap(new TestMap() {
            public Map<Location, BeeperStack> getBeepers() {
                throw new IllegalStateException("Source generation failed");
            }
        }, IllegalStateException.class);
        assertEquals(new Location(10, 10), Arena.getModel().getSize());
        assertFalse(player.frontIsClear());
        assertTrue(player.nextToABeeper());
        assertTrue(Arena.getModel().getRobots().contains(player));
        assertTrue(events.isEmpty());
    }
}
