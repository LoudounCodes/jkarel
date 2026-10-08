// Student starter: complete the TODOs for this lesson.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.loudouncodes.jkarel.*;

public class DescribeAMap {
    // The interface describes map data independently of installation.
    private static class TrainingMap implements MapDataSource {
        public int getWidth() { return 8; }
        public int getHeight() { return 6; }
        public Map<Location, BeeperStack> getBeepers() {
            // Return a fresh collection indexed by grid locations.
            Map<Location, BeeperStack> beepers = new HashMap<>();
            // TODO: Describe a two-beeper stack at column 4, row 2.
            return beepers;
        }
        public List<Wall> getWalls() {
            List<Wall> walls = new ArrayList<>();
            // TODO: Describe a north wall at column 3, row 3.
            return walls;
        }
    }
    // This is OUR adapter. Arena has no loadMap(MapDataSource) overload yet.
    private static void install(MapDataSource source) {
        Arena.openDefaultMap();
        Arena.setSize(source.getWidth(), source.getHeight());
        // Translate the interface data into existing model operations.
        for (Wall wall : source.getWalls()) Arena.getModel().addWall(wall);
        for (BeeperStack stack : source.getBeepers().values())
            Arena.getModel().putBeepers(stack.getLocation(), stack.getBeepers(), stack.getColor());
    }
    public static void main(String[] args) {
        Arena.setPace(Pacing.FAST);
        // Finish constructing the scene before creating the player.
        install(new TrainingMap());
        Robot player = new Robot(2, 2, Direction.EAST, 0);
        player.move(); player.move();
        while (player.nextToABeeper()) player.pickBeeper();
        System.out.println("Player " + player.getLocation() + ", inventory " + player.getBeepers());
    }
}
