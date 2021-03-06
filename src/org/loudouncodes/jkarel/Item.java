package org.loudouncodes.jkarel;

import java.awt.Graphics;

/**
 * Item is an 'abstract' concept in this library, representing
 * anything that can be placed in an Arena.
 *
 * Robots, Walls, and Beepers are all Items in an Arena.
 *
 * Future versions of this library might allow meaningful
 * subclasses of Item to be created by users.
 */
public abstract class Item {

  protected Location myLocation;

  public Item(int x, int y) {
    myLocation = new Location(x, y);
  }

  public Location getLocation() {
    return myLocation;
  }
 
  public int getX() {
    return myLocation.getX();
  }

  public int getY() {
  	return myLocation.getY();
  }
  
  /**
   * Since Items are in an arena, we know they will need to draw themselves.
   * But since 'Item' is an abstract idea, we don't know what it will look
   * like yet.  We represent this in object-oriented programming with this
   * keyword 'abstract'.  That tells the compiler, "I'mma be asking you you
   * to render a bunch of items later, but you need to ask them what they
   * look like."
   * 
   * It is unlikely you'll ever call this method directly, Java will call
   * this when it is time to draw. But if you ever make a subclass of item,
   * You'll be responsible for how it draws itself.
      
   * @param g - the Graphics context the item needs to draw itself into.
   * @param x - the x location it will draw itself.
   * @param y - the y location it will draw itself.
   */
  public abstract void render(Graphics g, int x, int y);
  
}
