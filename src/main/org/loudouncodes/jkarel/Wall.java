package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;

/**
  * Represents one wall segment within the arena. If a robot hits
  * a wall, that's bad m'kay?
  *
  * You won't have to deal with walls unless you are doing something
  * kinda advanced with this library.  Walls are loaded out of map
  * files that pre-exist for the lab you'll be working on.
  *
  * if you want to programatically create walls,
  * @see org.loudouncodes.jkarel.Arena
  */
public class Wall extends Item {

  /** not used yet, but needed instead of magic ints defined in arena */
  public enum Orientation {
    /** not used yet */
    HORIZONTAL,
    /** not used yet */
    VERTICAL;
  }
  
  private static ImageIcon wall;
  
  private final int WALL_WIDTH = 7;

  private int orientation;

  {
    wall = new ImageIcon(ArenaPanel.class.getResource("/icons/arena_wall.png"));
  }

  /**
    * creates a wall at location x, y in the arena with
    * the specified orientation.
    *
    * Should this use a Location object instead?
    *
    * @param x the x location in the arena.
    * @param y the y location within the arena.
    * @param orientation the orientation of the wall.
    */
  public Wall(int x, int y, int orientation) {
    super(x, y);

    if (orientation == Arena.VERTICAL ||
                orientation == Arena.HORIZONTAL)
      this.orientation = orientation;
    else {
      Arena.logger.warning("Invalid wall orientation: " + orientation + "...  Using VERTICAL.");
      this.orientation = Arena.VERTICAL;
    }
  }


  /**
    * @return a magic int indicating horizontal or vertical.
    */
  public int getOrientation() {
    return orientation;
  }

  /**
    * You will never need to call this method unless you are 
    * working inside the jkarel library itself.  The library
    * calls this method when the wall needs to draw itself
    * on the screen.
    *
    * In advanced uses of this library, you can subclass Wall
    * and override this method, making a wall that can draw
    * something other than the default image.
    *
    * @param g the Graphics context to draw into
    * @param x the x location where the wall should draw itself
    * @param y the y location where the wall should draw itself 
    */
  public void render(Graphics g, int x, int y) {    
    g.setColor(Color.black);

    double width = ArenaPanel.getCurrent().getXBlockLength();
    double height = ArenaPanel.getCurrent().getYBlockLength();

    switch (orientation) {
      case Arena.VERTICAL:
        g.fillRect((int)(x + width / 2 - (WALL_WIDTH - 1) / 2),
                   (int)(y - height + height / 2),
                   WALL_WIDTH, (int)(height + 1));
        break;
        
        
      case Arena.HORIZONTAL:
        g.fillRect((int)(x - width / 2),
                   (int)(y - height / 2 - (WALL_WIDTH - 1) / 2),
                   (int)(width + 1), WALL_WIDTH);
        break;
    }
  }

  /**
    * We implement this because this class is a nice Java citizen.
    * All classes have an option to implement toString().  If you
    * try to System.out.println a wall, it actually prints something
    * useful to a programmer trying to debug a problem.
    *
    * @return a String representation of this wall.
    */
  public String toString() {
    return "Wall { x: " + getX() + ", y: " + getY() +
           ", orientation: " + orientation + " }";
  }

}
