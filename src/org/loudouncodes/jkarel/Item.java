package org.loudouncodes.jkarel;

import java.awt.Graphics;


/**
 * The Item class keeps track of an x and y location and provides an abstract
 * render method.
 */

public abstract class Item {

	protected int x, y;

	/**
	 * Constructs an item with the specified x and y locations.
	 */
	public Item(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x location.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y location.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Renders the Item.
	 */
	public abstract void render(Graphics g, Location c);

}
