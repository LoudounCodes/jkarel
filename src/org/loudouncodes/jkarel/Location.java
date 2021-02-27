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
	
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public void move(Direction d) {
		switch (d) {
			case NORTH:
				y++;
				break;
			case EAST:
				x++;
				break;
			case SOUTH:
				y--;
				break;
			case WEST:
				x--;
				break;
		}
  }

	public int hashCode()
	{
		return x * 1000 + y; //Possible problems on HUGE maps?
	}
	
  public String toString() {
    return "[" + x +", " + y +"]";
  }

	public boolean equals(Object o)
	{
		if(!(o instanceof Location))
			return false;
		
		Location that = (Location)o;
		
		return (that.x == this.x && that.y == this.y);

	}
}
