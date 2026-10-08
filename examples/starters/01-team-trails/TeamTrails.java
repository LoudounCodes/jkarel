// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
// Student starter: complete the TODOs for this lesson.
import java.awt.Color;
import org.loudouncodes.jkarel.*;

/** Extension lab 1: two teams leave colored trails inside a student-built arena. */
public class TeamTrails {
    public static void main(String[] args) {
        Arena.openDefaultMap();
        Arena.setSize(8, 6);
        Arena.setPace(Pacing.FAST);
        buildWalls();

        Robot red = new Robot(2, 2, Direction.EAST, 5);
        Robot blue = new Robot(2, 4, Direction.EAST, 5);
        // Drops use the robot color; team identity is stored in these variables.
        // TODO: Give each robot its team color.

        // Five drops need only four moves: both trails end in column 6.
        for (int step = 0; step < 5; step++) {
            // TODO: Have each robot drop one beeper before moving.
            if (step < 4) {
                if (red.frontIsClear()) red.move();
                if (blue.frontIsClear()) blue.move();
            }
        }
        System.out.println("Red inventory: " + red.getBeepers()
            + ", Blue inventory: " + blue.getBeepers());
    }

    private static void buildWalls() {
        // setSize changes the visible grid. These loops create the boundaries.
        for (int x = 1; x <= 8; x++) {
            Arena.addSouthWall(x, 1);
            Arena.addNorthWall(x, 6);
        }
        for (int y = 1; y <= 6; y++) {
            Arena.addWestWall(1, y);
            Arena.addEastWall(8, y);
        }
        // A divider between the two teams' lanes.
        for (int x = 2; x <= 6; x++) Arena.addNorthWall(x, 2);
    }
}
