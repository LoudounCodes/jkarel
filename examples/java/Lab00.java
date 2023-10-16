import org.loudouncodes.jkarel.Arena;
import org.loudouncodes.jkarel.Robot;
import org.loudouncodes.jkarel.Direction;
import org.loudouncodes.jkarel.BeeperStack;
import org.loudouncodes.jkarel.Pacing;
import java.awt.Color;
  
  
public class Lab00 {
  
  public static void main(String[] args) {
    Arena.openDefaultWorld();
    
    Robot karel = new Robot();
    
    Arena.setPace(Pacing.STEP);
    
    for (int i = 0; i <= 3; i++) {
      karel.move();
      karel.turnLeft();
    }
    
    Arena.addNorthWall(5,5);
    Arena.addSouthWall(5,5);
    Arena.addEastWall(5,5);
    Arena.addWestWall(5,5);
    Arena.addBeepers(5,5,5);
      
      
    Robot rodney = new Robot(1, 2, Direction.EAST, BeeperStack.INFINITY);

    rodney.putBeeper();
    rodney.move();
    rodney.setColor(Color.BLUE);
    rodney.putBeeper();
    rodney.move();
    rodney.setColor(Color.GREEN);
    rodney.putBeeper();
    rodney.move();
    rodney.setColor(Color.RED);
    rodney.putBeeper();
    rodney.move();
  } 
}