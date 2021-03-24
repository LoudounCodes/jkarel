import org.loudouncodes.jkarel.*;

public class HelloWorld {

  public static void main(String[] args) {
    Arena.openWorld("/default.map");
    Arena.setSize(10, 10);
    Arena.setPace(Pacing.STEP);
    
    Robot karel = new Robot();
    
    karel.move();
    karel.move();
    karel.turnLeft();
    karel.move();
    karel.move();
    karel.turnLeft();
    karel.turnLeft();
  }
  
  
}
  