package org.loudouncodes.jkarel;


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
