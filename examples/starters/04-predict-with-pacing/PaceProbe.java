// Student starter: complete the TODOs for this lesson.
import org.loudouncodes.jkarel.*;

public class PaceProbe {
    public static void main(String[] args) {
        Arena.openDefaultMap();
        Arena.setPace(Pacing.FAST);
        Robot robot = new Robot(3, 3, Direction.EAST, 0);
        // Use --auto for an unattended graphical demonstration.
        boolean auto = args.length > 0 && args[0].equals("--auto");
        // STEP pauses after an action; Enter lets execution continue.
        if (!auto) {
            // TODO: Select STEP pacing here; keep --auto unattended.
        }
        System.out.println("With STEP selected, press Enter after each action.");
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
