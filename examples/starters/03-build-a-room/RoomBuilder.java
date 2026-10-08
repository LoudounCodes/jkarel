// Student starter: complete the TODOs for this lesson.
import org.loudouncodes.jkarel.*;

public class RoomBuilder {
    public static void main(String[] args) {
        Arena.openDefaultMap();
        Arena.setSize(8, 6);
        Arena.setPace(Pacing.FAST);
        // A visible grid size does not build walls; these loops do.
        for (int x = 1; x <= 8; x++) {
            Arena.addSouthWall(x, 1);
            Arena.addNorthWall(x, 6);
        }
        for (int y = 1; y <= 6; y++) {
            Arena.addWestWall(1, y);
            Arena.addEastWall(8, y);
        }
        // Leave a gap in the divider, then fill it with a removable door.
        for (int y = 2; y <= 5; y++) {
            // TODO: Build the divider at column 4, leaving row 3 for the door.
        }
        // Keep this exact object so we can remove this wall later.
        Wall door = new Wall(4, 3, Arena.VERTICAL);
        Arena.getModel().addWall(door);
        Arena.addBeepers(5, 3, 3);
        Robot player = new Robot(4, 3, Direction.EAST, 0);
        System.out.println("Door closed, front clear: " + player.frontIsClear());
        // Opening the door changes the sensor result before the next move.
        // TODO: Open the door by removing this wall object from the model.
        if (player.frontIsClear()) player.move();
        if (player.nextToABeeper()) player.pickBeeper();
        System.out.println("Player " + player.getLocation() + ", inventory " + player.getBeepers());
    }
}
