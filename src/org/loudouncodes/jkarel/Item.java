package org.loudouncodes.jkarel;

import java.awt.Graphics;

/**
 * The Item class keeps track of an x and y location and provides an abstract
 * render method.
 * @author Andy Street, alstreet@vt.edu, 2007
 */

public abstract class Item {

  protected Location myLocation;
  
	/**
	 * Constructs an item with the specified x and y locations.
	 */
	public Item(int x, int y) {
    myLocation = new Location(x, y);
	}

  public Location getLocation() {
    return myLocation;
  }
  
	/**
	 * Returns the x location.
	 */
	public int getX() {
		return myLocation.getX();
	}

	/**
	 * Returns the y location.
	 */
	public int getY() {
		return myLocation.getY();
	}

	/**
	 * Renders the Item.
   * At first glance, x and y might seem duplicative of the Location concept, but these
   * are different.  Location is an object that references the arena location, but these
   * x and y coordinates represent a location on the WorldPanel... that is, locations
   * converted to pixels.
	 */
	public abstract void render(Graphics g, int x, int y);
  
}
