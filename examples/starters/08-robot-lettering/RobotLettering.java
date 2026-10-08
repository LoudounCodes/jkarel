// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
// Student starter: complete the TODOs for this lesson.
import java.awt.Color;
import org.loudouncodes.jkarel.*;
import org.loudouncodes.jkarel.demo.AlphaBot;

public class RobotLettering {
    public static void main(String[] args) {
        Arena.openDefaultMap();
        // Leave a clear route and enough room for the lettering routine.
        Arena.setSize(24, 12);
        Arena.setPace(Pacing.LUDICRUS);
        AlphaBot sign = new AlphaBot();
        // TODO: Choose the color for the sign and its beeper letters.
        // AlphaBot draws ASCII letters with moves and colored beeper drops.
        // TODO: Have the sign write HI, then try another short ASCII message.
        // Unlimited supplies use a named sentinel, not an ordinary count.
        System.out.println("Infinite inventory: " + (sign.getBeepers() == BeeperStack.INFINITY));
    }
}
