import org.loudouncodes.jkarel.*;

public class MapStages {
    public static void main(String[] args) {
        String mapFolder = args.length == 0 ? "." : args[0];
        Arena.openDefaultMap();
        Arena.setPace(Pacing.FAST);
        Robot player = new Robot(2, 2, Direction.EAST, 0);
        ArenaListener observer = new ArenaListener() {
            public void mapLoaded(String name) { System.out.println("Loaded: " + name); }
        };
        Arena.addListener(observer);
        try {
            Arena.loadMap(mapFolder + "/stage-one.map");
            if (player.frontIsClear()) player.move();
            if (player.nextToABeeper()) player.pickBeeper();
            Arena.loadMap(mapFolder + "/stage-two.map");
            System.out.println("Same player: " + player.getLocation() + ", inventory " + player.getBeepers());
            System.out.println("New wall blocks front: " + !player.frontIsClear());
        } finally {
            Arena.removeListener(observer);
        }
    }
}
