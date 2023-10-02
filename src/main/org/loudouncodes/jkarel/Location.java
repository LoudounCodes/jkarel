package org.loudouncodes.jkarel;

/**
 * Represents an x,y coordinate within an arena.
 *
 * This is simply a wrapper around two ints named x and y,
 * along with a little behavior so that the location can
 * 'move itself' around an Arena.
 *
 * It also contains some of the appropriate Java hooks to make sure
 * that it works as a proper 'value object' in Java containers.
 **/
public class Location {

  private int x;
      
  private int y;

  /**
   * The only constructor we need.
   * @param x the x value of this location in the arena.
   * @param y the y value of this location in the arena.
   */
  public Location(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the x coordinate for this location.
   *
   * @return the x value of this location.
   */
  public int getX() {
    return x;
  }

  /**
   * @return the y value of this location.
   */
  public int getY() {
    return y;
  }

  /**
   * Given an instance of a Direction, the values of this location
   * are updated to reflect movement in that direction.
   *
   * @param d an instance of a Direction
   */
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
  
  /**
   * Don't worry about this method until you learn some
   * more advanced Java and algorithms stuff.
   *
   * Its basically used inside algorithms to determine if
   * two things are "probably not equal".  Its a strange
   * concept, but it lets computers be faster with some
   * kinds of decision-making.
   *
   * @return a hashcode of this instance of Location.
   */
  public int hashCode() {
      return x * 1009 + y; //1009 is the first prime larger than 1000
  }

  /**
   * returns a String in the format of "[x, y]"
   *
   * @return a String in the format of "[x, y]"
   */
  public String toString() {
    return "[" + x +", " + y +"]";
  }
  
  
  /**
   * compares two locations, and if their x and y values
   * are the same, then the locations are considered equal.
   *
   * @return true if both locations have the same x and y values.
   */
  public boolean equals(Object o) {
    if(!(o instanceof Location))
      return false;
    Location that = (Location)o;
    return (that.x == this.x && that.y == this.y);
  }
}
