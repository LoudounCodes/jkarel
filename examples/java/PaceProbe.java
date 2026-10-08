import org.loudouncodes.jkarel.*;

public class PaceProbe {
    public static void main(String[] args) {
        Arena.openDefaultMap();
        Arena.setPace(Pacing.FAST);
        Robot robot = new Robot(3, 3, Direction.EAST, 0);
        // Use --auto for an unattended graphical demonstration.
        boolean auto = args.length > 0 && args[0].equals("--auto");
        // STEP pauses after an action; Enter lets execution continue.
        Arena.setPace(auto ? Pacing.FAST : Pacing.STEP);
        System.out.println("After each action, press Enter in the run console.");
        robot.move();
        System.out.println(robot.getLocation() + " " + robot.getDirection());
        // Turning changes facing while preserving the location.
        robot.turnLeft();
        System.out.println(robot.getLocation() + " " + robot.getDirection());
        robot.move();
        System.out.println(robot.getLocation() + " " + robot.getDirection());
        Arena.setPace(Pacing.FAST);
    }
}
