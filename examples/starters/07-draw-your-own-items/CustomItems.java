// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
// Student starter: complete the TODOs for this lesson.
import java.awt.Color;
import java.awt.Graphics;
import org.loudouncodes.jkarel.*;

public class CustomItems {
    private static class Goal extends Item {
        Goal(int x, int y) { super(x, y); }
        // x and y are pixel centers supplied by the arena, not grid cells.
        public void render(Graphics graphics, int x, int y) {
            // TODO: Draw a visible goal centered on the supplied pixel x and y.
        }
    }
    // Override drawing while inheriting the normal robot actions and sensors.
    private static class Player extends Robot {
        Player() { super(2, 2, Direction.EAST, 0); }
        // x and y are pixel centers supplied by the arena, not grid cells.
        public void render(Graphics graphics, int x, int y) {
            // The starter uses normal robot drawing so the player is visible.
            super.render(graphics, x, y);
            // TODO: Replace that call with your own drawing using getColor().
        }
    }
    public static void main(String[] args) {
        Arena.openDefaultMap();
        Arena.setPace(Pacing.FAST);
        Goal goal = new Goal(4, 2);
        Arena.getModel().addUserItem(goal);
        Player player = new Player();
        player.setColor(Color.BLUE);
        for (int turn = 0; turn < 2; turn++) {
            if (player.frontIsClear()) player.move();
            // The game loop decides when the goal is reached; drawing does not.
            if (player.getLocation().equals(goal.getLocation())) {
                // TODO: Remove the goal item when this player reaches it.
                System.out.println("Reached the goal at " + player.getLocation());
            }
        }
    }
}
