// Student starter: complete the TODOs for this lesson.
import org.loudouncodes.jkarel.*;

public class MapStages {
    public static void main(String[] args) {
        // In jGRASP, keep both map files beside this program.
        String mapFolder = args.length == 0 ? "." : args[0];
        Arena.openDefaultMap();
        Arena.setPace(Pacing.FAST);
        Robot player = new Robot(2, 2, Direction.EAST, 0);
        // An anonymous listener needs to override only the callback we use.
        ArenaListener observer = new ArenaListener() {
            public void mapLoaded(String name) {
                // TODO: Report which map the arena has just loaded.
            }
        };
        Arena.addListener(observer);
        try {
            Arena.loadMap(mapFolder + "/stage-one.map");
            if (player.frontIsClear()) player.move();
            if (player.nextToABeeper()) player.pickBeeper();
            // Reloading replaces map contents but preserves this player.
            // Its position, facing, and inventory survive the transition.
            // TODO: Load stage-two.map without constructing a new player.
            System.out.println("Same player: " + player.getLocation() + ", inventory " + player.getBeepers());
            System.out.println("New wall blocks front: " + !player.frontIsClear());
        } finally {
            Arena.removeListener(observer);
        }
    }
}
