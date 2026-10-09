// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
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
            beepers.put(new Location(4, 2), new BeeperStack(4, 2, 2));
            return beepers;
        }
        public List<Wall> getWalls() {
            List<Wall> walls = new ArrayList<>();
            walls.add(new Wall(3, 3, Arena.HORIZONTAL));
            return walls;
        }
    }
    public static void main(String[] args) {
        Arena.setPace(Pacing.FAST);
        // Finish constructing the scene before creating the player.
        // The library reads the description through the MapDataSource contract.
        Arena.loadMap(new TrainingMap());
        Robot player = new Robot(2, 2, Direction.EAST, 0);
        player.move(); player.move();
        while (player.nextToABeeper()) player.pickBeeper();
        System.out.println("Player " + player.getLocation() + ", inventory " + player.getBeepers());
    }
}
