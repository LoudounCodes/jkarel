package org.loudouncodes.jkarel;

/**
  * Represents the concept of an Item in the Arena (like a Robot)
  * facing a specific direction.
  */
public enum Direction {
  /** enumeration indication a Direction facing North. */
  NORTH, 
    
  /** enumeration indication a Direction facing East. */
  EAST,
    
  /** enumeration indication a Direction facing South. */
  SOUTH,
    
  /** enumeration indication a Direction facing West. */
  WEST;
  
  /**
    * @return a Direction as if the current direction turned left
    */
  public Direction left() {
    return values()[(ordinal() - 1  + values().length) % values().length];
  }
  
  /**  
    * @return a Direction as if the current direction turned right
    */
  public Direction right() {
    return values()[(ordinal() + 1) % values().length];
  }
}
     