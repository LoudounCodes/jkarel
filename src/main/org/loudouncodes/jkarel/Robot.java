package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.HashMap;
import java.awt.image.BufferedImage;


/**
 * One of the main concepts implemented by this Library.
 *
 * In the curriculum that uses this library, we will create robots
 * and move them around the arena.  We'll also 'subclass' robots,
 * teaching them how to do new things.
 *
 * As defined by the original Karel the Robot curriculum, robots
 * can only:
 * <ul>
 *   <li>Know what direction they are facing</li>
 *   <li>Know if they are next to a wall (and where it is)</li>
 *   <li>Move Forward</li>
 *   <li>Turn Left</li>
 *   <li>Drop 'Beepers'</li>
 *   <li>Pick up 'Beepers'</li>
 *   <li>Know if they are currently 'on top of' a beeper</li>
 * </ul>
 * Anything other than that, you will teach it.
 */
public class Robot extends Item {

  /** The number of beepers we have in our inventory. */
  private int beepers;
  
  /** The current direction the robot is facing. This is protected
    * so that in a future OO design class, students can learn a
    * better implementation of turnRight().
    */
  protected Direction direction;
  
  /** The icons we use to draw the robot, organized by Direction. */
  private HashMap<Direction, BufferedImage> icons;

  private ArenaModel containingModel;
  
  private Color myColor = Color.YELLOW;
    /**
     * Constructs a robot at [1, 1], facing east, with 0 beepers.
     */
  public Robot() {
    this(1, 1, Direction.EAST, 0);
  }

    /**
     * Constructs a robot at a location you specify, facing the direction
     * you specify, with the number of beepers you specify.
     * 
     * Design note - this constructor does too much.
     *
     * @param x the x location where this robot is constructed.
     * @param y the y location where this robot is constructed.
     * @param dir the Direction this robot is facing when constructed.
     * @param beepers. The number of beepers this robot has when constructed.
     * 
     */
  public Robot(int x, int y, Direction dir, int beepers) {
      super(x, y);
    direction = dir;
      
    if (beepers < 0 && beepers != BeeperStack.INFINITY) {
      Arena.logger.warning("Invalid amount of beepers: "
                         + beepers + "...  Setting to 0...");
      this.beepers = 0;
    } else {
      this.beepers = beepers;
    }

      initializeIcons();
      
    Arena.getModel().addRobot(this);
  }

    /**
     * Subclasses that know what they are doing can override this method
     * in order to change the way robots draw themselves in the arena.
     *
     * return a hashmap with Directions as the key and icons as the value.
     */
    protected void initializeIcons() {
        HashMap<Direction, BufferedImage> images = new HashMap<Direction, BufferedImage>();
        
        ImageIcon base =  new ImageIcon(Robot.class.getResource("/icons/karel.png"));

        icons = ImageUtils.setupImages(base, myColor);
    }
    
    
    
    /**
      * Returns the direction this robot is facing.
      * @return an enum indicating the direction this robot is facing.
      */
  public Direction getDirection() {
    return direction;
  }

  /**
    * Returns the number of beepers this Robot is currently holding.
    * @return the number of beepers this Robot is currently holding.
    */
  public int getBeepers() {
    return beepers;
  }

  /**
    * Moves the robot one step forward (in the direction it is facing)
    */
  public synchronized void move() {      
    if (!frontIsClear()) {
      Arena.die("Tried to walk " + direction + " through a wall at " + myLocation);
      return;
    }
      
    myLocation.move(direction);
      
      Arena.getModel().notifyMoved(this);
      
    Arena.step();
  }

  /**
    * Moves the robot one step backward (from the direction it is facing)
    */
  protected synchronized void backUp() {      
    if (!backIsClear()) {
      Arena.die("Tried to walk " + direction + " through a wall at " + myLocation);
      return;
    }
      
    myLocation.move(direction.behind());
      
      Arena.getModel().notifyMoved(this);
      
    Arena.step();
  }
  
  
  /**
    * Turns the Robot 90 degrees to the left.
    */
  public void turnLeft() {      
    direction = direction.left();
      
    Arena.step();
  }

  /**
    * Turnd the robot 90 degrees to the right.  Note that this
    * method is private because part of the original Karel
    * curriculum is about creating a turnRight method by turning
    * left 3 times.
    */
  private void turnRight() {
    direction = direction.right();
      
    Arena.step();
  }

  /**
    * Drops a single beeper at the current location.
    * The number of beepers this robot is holding is
    * decreased by 1.
    *
    * If there are no beepers, this method will log
    * an error and stop the program.
    */
  public void putBeeper() {
    if (beepers < 1 && beepers != BeeperStack.INFINITY) {
      Arena.die("Trying to put non-existent beepers!");
      return;
    }
      
    if (beepers != BeeperStack.INFINITY)
      beepers--;
      
    Arena.getModel().putBeepers(myLocation, 1, myColor);
      
    Arena.step();
  }
  
  public void setColor(Color c) {
    myColor = c;
    initializeIcons();
    Arena.step();
  }
  
  public Color getColor() {
    return myColor;
  }
  
  /**
    * Picks a beeper up from the current location and
    * adds it to the Robot's beeper inventory.
    *
    * If there are no beepers present, this method will
    * log an error and stop the program.
    */
  public void pickBeeper() {
    if (!Arena.getModel().checkBeepers(myLocation)) {
      Arena.die("Trying to pick non-existent beepers!");
      return;
    }
      
    if (beepers != BeeperStack.INFINITY)
      beepers++;
      
    Arena.getModel().putBeepers(myLocation, -1);
      
    Arena.step();
  }

  /**
    * returns true if this robot has beepers in its inventory.
    *
    * @return true if this robot has beepers in its inventory,
    *         false otherwise.
    */
  public boolean hasBeepers() {
    return beepers > 0 || beepers == BeeperStack.INFINITY;
  }

  /**
    * @return boolean is the front clear of any walls?
    */
  public boolean frontIsClear() {
    return isClear(direction);
  }

  /**
    * @return boolean is the right clear of any walls?
    */
  public boolean rightIsClear() {
    return isClear(direction.right());
  }

  /**
    * @return boolean is the left clear of any walls?
    */
  public boolean leftIsClear() {
    return isClear(direction.left());
  }

  /**
    * @return boolean is the back clear of any walls?
    */
  public boolean backIsClear() {
    return isClear(direction.right().right());
  }

  /**
    * @return boolean am I next to (on top of) any beepers?
    */
  public boolean nextToABeeper() {
    return Arena.getModel().checkBeepers(myLocation);
  }

  /**
    * @return boolean am I next to (on top of) any robots?
    */
  public boolean nextToARobot() {
    return Arena.getModel().isNextToARobot(this, myLocation);
  }

  /**
    * @return boolean am I facing North?
    */
  public boolean facingNorth() {
    return direction == Direction.NORTH;
  }

  /**
    * @return boolean am I facing South?
    */
  public boolean facingSouth() {
    return direction == Direction.SOUTH;
  }

  /**
    * @return boolean am I facing East?
    */
  public boolean facingEast() {
    return direction == Direction.EAST;
  }

  /**
    * @return boolean am I facing West?
    */
  public boolean facingWest() {
    return direction == Direction.WEST;
  }

  /**
    * used internally by the xIsClear methods.
    */
  private boolean isClear(Direction dir) {
    Location c = getWallLocation(direction);
      
    switch (dir) {
      case NORTH:
      case SOUTH:
        return !Arena.getModel()
               .checkWall(c.getX(), c.getY(), Arena.HORIZONTAL);
      case EAST:
      case WEST:
      default:
        return !Arena.getModel()
               .checkWall(c.getX(), c.getY(), Arena.VERTICAL);
    }
  }

  /**
    * Removes this robot from the current arena.
    *
    * not as cool as it sounds. It needs an explosion or something.
    */
  public void explode() {
    Arena.getModel().removeRobot(this);
  }


  /**
    * I'm unsure about this method, because the name sucks.
    * what wall is this? The one we walked through? The one
    * in front of us?  There can be multiple walls around the robot...
    * which one is this and why?
    */
  private Location getWallLocation(Direction dir) {
    int xc = myLocation.getX();
    int yc = myLocation.getY();

    switch (dir) {
      case NORTH: //Check in front, not behind
      case EAST: //Check in front, not behind
        break;
      case SOUTH: //Checking behind current location
        yc--;
        break;
      case WEST: //Checking behind current location
        xc--;
        break;
    }
      
    return new Location(xc, yc);
  }

  /**
    * You will never need to call this method unless you are 
    * working inside the jkarel library itself.  The library
    * calls this method when the robot needs to draw itself
    * on the screen.
    *
    * In advanced uses of this library, you can subclass Robot
    * and override this method, making a robot that can draw
    * something other than the default image.
    *
    * @param g the Graphics context to draw into
    * @param x the x location where the robot should draw itself
    * @param y the y location where the robot should draw itself 
    */
  public void render(Graphics g, int x, int y) {
    BufferedImage i = icons.get(direction);
    g.drawImage(i,
                x - i.getWidth() / 2,
                y - i.getHeight() / 2, null);
  }

  /**
    * Returns true if we are next to a robot, false otherwise.
    * Note that 'next to' really means 'on top of' because of
    * the way Karel graphics look and work.  This is like two
    * robots standing next to each other on a streetcorner.
    *
    * @param other the other robot we are testing to see if we are next to.
    * @return true if we are next to a robot, false otherwise.
    */
  public boolean nextToRobot(Robot other) {
    return (myLocation.equals(other.getLocation()));
  }
}
