import org.loudouncodes.jkarel.*;

public class ScoutMoves {
    private static class Scout extends Robot {
        Scout() { super(3, 3, Direction.NORTH, 0); }
        public void turnRight() { turnLeft(); turnLeft(); turnLeft(); }
        public void retreat() { if (backIsClear()) backUp(); }
    }
    public static void main(String[] args) {
        Arena.openDefaultMap();
        Arena.setPace(Pacing.FAST);
        Arena.addWestWall(3, 3);
        Scout scout = new Scout();
        System.out.println("Left clear: " + scout.leftIsClear());
        Direction planned = scout.getDirection().right();
        System.out.println("Planned direction: " + planned);
        scout.turnRight();
        if (scout.frontIsClear()) scout.move();
        scout.retreat();
        System.out.println("Scout " + scout.getLocation() + " " + scout.getDirection());
    }
}
