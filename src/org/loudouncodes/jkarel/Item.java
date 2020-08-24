package org.loudouncodes.jkarel;

import java.awt.Graphics;

/*
 * Copyright (C) Andy Street 2007
 *
 * This software is licensed under the GNU Public License v3.
 * See attached file LICENSE.TXT or contact the author for more information.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of Version 3 of the GNU General Public License as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
