import org.loudouncodes.jkarel.*;

public class RoomBuilder {
    public static void main(String[] args) {
        Arena.openDefaultMap();
        Arena.setSize(8, 6);
        Arena.setPace(Pacing.FAST);
        for (int x = 1; x <= 8; x++) {
            Arena.addSouthWall(x, 1);
            Arena.addNorthWall(x, 6);
        }
        for (int y = 1; y <= 6; y++) {
            Arena.addWestWall(1, y);
            Arena.addEastWall(8, y);
        }
        for (int y = 2; y <= 5; y++) {
            if (y != 3) Arena.addEastWall(4, y);
        }
        Wall door = new Wall(4, 3, Arena.VERTICAL);
        Arena.getModel().addWall(door);
        Arena.addBeepers(5, 3, 3);
        Robot player = new Robot(4, 3, Direction.EAST, 0);
        System.out.println("Door closed, front clear: " + player.frontIsClear());
        Arena.getModel().removeWall(door);
        if (player.frontIsClear()) player.move();
        if (player.nextToABeeper()) player.pickBeeper();
        System.out.println("Player " + player.getLocation() + ", inventory " + player.getBeepers());
    }
}
