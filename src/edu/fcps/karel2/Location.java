package edu.fcps.karel2;

/**
 * @author Andy Street, alstreet@vt.edu, 2007
 */

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
 * Location is a basic container for two ints representing the x and y locations of some object.
 * It overrides Object's equals() and hashCode() methods so that two different Location objects
 * with the same x and y locations are identified as being the same (useful for rertrieving data from
 * HashMaps)
 */

public class Location {
	
	public int x, y;
	
	public Location(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
        /**
         * Generates a predictable hash code for the given x and y of the Location.
         */
	public int hashCode()
	{
		return x * 1000 + y; //Possible problems on HUGE maps?
	}
	
        /**
         * Checks if the x and y of the passed Location are the same as this object's x and y.
         */
	public boolean equals(Object o)
	{
		if(!(o instanceof Location))
			return false;
		
		Location c = (Location)o;
		
		if(c.x == x && c.y == y)
			return true;
		
		return false;
	}
}
