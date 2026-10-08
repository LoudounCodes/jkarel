// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
package org.loudouncodes.jkarel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

/** Capture the actual Swing arena used by the lesson examples, without editing images.
 * Compile this with TeamTrails, CustomItems, and RobotLettering against jkarel.jar.
 * Run from the repository root with a view name: TeamTrails, CustomItems, RobotLettering.
 */
public class CaptureLabViews {
    private static void snapshot(String filename) throws Exception {
        snapshot(filename, null);
    }

    private static void snapshot(String filename, Rectangle detail) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                ArenaPanel panel = ArenaPanel.getCurrent();
                Rectangle region = detail == null
                    ? new Rectangle(0, 0, panel.getWidth(), panel.getHeight()) : detail;
                BufferedImage image = new BufferedImage(region.width, region.height,
                    BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = image.createGraphics();
                graphics.translate(-region.x, -region.y);
                panel.paint(graphics);
                graphics.dispose();
                ImageIO.write(image, "png", new File("docs/labs/assets/" + filename));
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        try {
            if (args[0].equals("CustomItems")) {
                // Use the example's actual nested classes to show the goal before removal.
                Arena.openDefaultMap();
                Arena.setSize(8, 6);
                Arena.setPace(Pacing.LUDICRUS);
                Constructor<?> goalConstructor = Class.forName("CustomItems$Goal")
                    .getDeclaredConstructor(int.class, int.class);
                goalConstructor.setAccessible(true);
                Item goal = (Item) goalConstructor.newInstance(4, 2);
                Arena.getModel().addUserItem(goal);
                Constructor<?> playerConstructor = Class.forName("CustomItems$Player")
                    .getDeclaredConstructor();
                playerConstructor.setAccessible(true);
                Robot player = (Robot) playerConstructor.newInstance();
                player.setColor(Color.BLUE);
                // Capture a detail directly from Swing so the small shapes print clearly.
                Rectangle detail = new Rectangle(70, 235, 260, 195);
                snapshot("custom-goal-start.png", detail);
                player.move(); player.move();
                if (!player.getLocation().equals(goal.getLocation()))
                    throw new AssertionError("Player must reach the goal");
                Arena.getModel().removeUserItem(goal);
                snapshot("custom-goal-finish.png", detail);
            } else {
                Class.forName(args[0]).getMethod("main", String[].class)
                    .invoke(null, (Object) new String[0]);
                snapshot(args[0].equals("TeamTrails") ? "team-trails.png" : "robot-lettering.png");
            }
        } finally {
            if (Arena.getArenaFrame() != null)
                SwingUtilities.invokeAndWait(() -> Arena.getArenaFrame().close());
        }
    }
}
