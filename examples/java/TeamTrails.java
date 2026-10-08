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
        red.setColor(Color.RED);
        blue.setColor(Color.BLUE);

        for (int step = 0; step < 5; step++) {
            red.putBeeper();
            blue.putBeeper();
            if (step < 4) {
                if (red.frontIsClear()) red.move();
                if (blue.frontIsClear()) blue.move();
            }
        }
        System.out.println("Both teams left five beepers, from column 2 through column 6.");
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
