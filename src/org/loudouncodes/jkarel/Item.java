package org.loudouncodes.jkarel;

import java.awt.Graphics;


/**
 * The Item class keeps track of an x and y location and provides an abstract
 * render method.
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

	public abstract void render(Graphics g, int x, int y);
  
}
