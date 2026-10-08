// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
// Student starter: complete the TODOs for this lesson.
import org.loudouncodes.jkarel.*;

public class ScoutMoves {
    private static class Scout extends Robot {
        Scout() { super(3, 3, Direction.NORTH, 0); }
        public void turnRight() {
            // TODO: Turn right using the inherited turnLeft operation.
        }
        // A subclass can call protected backUp; check the rear first.
        public void retreat() {
            // TODO: Check behind the scout, then use protected backUp.
        }
    }
    public static void main(String[] args) {
        Arena.openDefaultMap();
        Arena.setPace(Pacing.FAST);
        Arena.addWestWall(3, 3);
        Scout scout = new Scout();
        System.out.println("Left clear: " + scout.leftIsClear());
        // This computes a direction value without turning the scout.
        Direction planned = scout.getDirection().right();
        System.out.println("Planned direction: " + planned);
        scout.turnRight();
        if (scout.frontIsClear()) scout.move();
        // Move backward without changing the eastward facing.
        scout.retreat();
        System.out.println("Scout " + scout.getLocation() + " " + scout.getDirection());
    }
}
