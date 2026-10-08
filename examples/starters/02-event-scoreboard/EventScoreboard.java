// Student starter: complete the TODOs for this lesson.
import java.awt.Color;
import org.loudouncodes.jkarel.*;

/** Extension lab 2: observe moves and pickups without putting scoring in Robot. */
public class EventScoreboard {
    public static void main(String[] args) {
        Arena.openDefaultMap();
        Arena.setSize(8, 6);
        Arena.setPace(Pacing.FAST);
        for (int x = 2; x <= 6; x++) Arena.addNorthWall(x, 2);
        Arena.addEastWall(6, 2);
        Arena.addEastWall(6, 4);
        Arena.addBeeper(3, 2); Arena.addBeeper(4, 2);
        Arena.addBeeper(3, 4); Arena.addBeeper(4, 4);

        Robot red = new Robot(2, 2, Direction.EAST, 0);
        Robot blue = new Robot(2, 4, Direction.EAST, 0);
        red.setColor(Color.RED); blue.setColor(Color.BLUE);
        Scoreboard scores = new Scoreboard(red, blue);
        // Register before actions so the scoreboard receives their callbacks.
        // TODO: Register scores as an arena listener before the first turn.
        try {
            // The main program controls movement; the listener only observes it.
            for (int turn = 0; turn < 4; turn++) {
                takeTurn(red);
                takeTurn(blue);
            }
            scores.printResult();
        } finally {
            // Remove the observer even if an action throws an exception.
            // TODO: Unregister scores when this game finishes.
        }
    }

    private static void takeTurn(Robot robot) {
        if (robot.frontIsClear()) robot.move();
        if (robot.nextToABeeper()) robot.pickBeeper();
    }

    private static class Scoreboard implements ArenaListener {
        private final Robot red;
        private final Robot blue;
        private int redScore;
        private int blueScore;
        private int moves;

        Scoreboard(Robot red, Robot blue) {
            this.red = red;
            this.blue = blue;
        }

        @Override
        public void robotMoved(Robot robot) {
            // TODO: Count moves made by either of these two player objects.
        }

        // Object identity identifies the player, even if its color changes.
        // The pickup has already changed the inventory when this is called.
        @Override
        public void beeperPickedUp(Robot robot) {
            // TODO: Award a point to the player object that picked up a beeper.
            System.out.println("Red: " + redScore + "  Blue: " + blueScore);
        }

        void printResult() {
            System.out.println("Final: Red " + redScore + ", Blue " + blueScore
                + ", moves " + moves);
        }
    }
}
