// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
// Student starter: complete the TODOs for this lesson.
import org.loudouncodes.jkarel.*;

public class WelcomeArena {
    public static void main(String[] args) {
        // Arena calls prepare the shared world before we add a robot.
        Arena.openDefaultMap();
        Arena.setSize(8, 6);
        Arena.setPace(Pacing.FAST);
        // Constructor arguments: column, row, facing, starting inventory.
        Robot explorer = new Robot(2, 2, Direction.EAST, 2);
        // Robot calls change this robot; the dropped beeper stays behind.
        // TODO: Drop a beeper, move east, turn north, and move again.
        System.out.println("Location: " + explorer.getLocation());
        System.out.println("Direction: " + explorer.getDirection());
        System.out.println("Inventory: " + explorer.getBeepers());
    }
}
