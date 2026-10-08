import java.awt.Color;
import java.awt.Graphics;
import org.loudouncodes.jkarel.*;

public class CustomItems {
    private static class Goal extends Item {
        Goal(int x, int y) { super(x, y); }
        public void render(Graphics graphics, int x, int y) {
            graphics.setColor(Color.GREEN);
            graphics.drawOval(x - 12, y - 12, 24, 24);
            graphics.drawLine(x - 8, y, x + 8, y);
            graphics.drawLine(x, y - 8, x, y + 8);
        }
    }
    private static class Player extends Robot {
        Player() { super(2, 2, Direction.EAST, 0); }
        public void render(Graphics graphics, int x, int y) {
            graphics.setColor(getColor());
            graphics.fillRect(x - 8, y - 8, 16, 16);
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
            if (player.getLocation().equals(goal.getLocation())) {
                Arena.getModel().removeUserItem(goal);
                System.out.println("Reached the goal at " + player.getLocation());
            }
        }
    }
}
