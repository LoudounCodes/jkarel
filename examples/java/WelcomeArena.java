import org.loudouncodes.jkarel.*;

public class WelcomeArena {
    public static void main(String[] args) {
        Arena.openDefaultMap();
        Arena.setSize(8, 6);
        Arena.setPace(Pacing.FAST);
        Robot explorer = new Robot(2, 2, Direction.EAST, 2);
        explorer.putBeeper();
        explorer.move();
        explorer.turnLeft();
        explorer.move();
        System.out.println("Location: " + explorer.getLocation());
        System.out.println("Direction: " + explorer.getDirection());
        System.out.println("Inventory: " + explorer.getBeepers());
    }
}
