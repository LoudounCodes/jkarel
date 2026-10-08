import java.awt.Color;
import org.loudouncodes.jkarel.*;
import org.loudouncodes.jkarel.demo.AlphaBot;

public class RobotLettering {
    public static void main(String[] args) {
        Arena.openDefaultMap();
        Arena.setSize(24, 12);
        Arena.setPace(Pacing.LUDICRUS);
        AlphaBot sign = new AlphaBot();
        sign.setColor(Color.BLUE);
        sign.say("HI");
        System.out.println("Infinite inventory: " + (sign.getBeepers() == BeeperStack.INFINITY));
    }
}
