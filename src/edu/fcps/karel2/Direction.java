package edu.fcps.karel2;


public enum Direction {
  NORTH, EAST, SOUTH, WEST;
  
  public Direction left() {
    return values()[(ordinal() - 1  + values().length) % values().length];
  }
  
  public Direction right() {
    return values()[(ordinal() + 1) % values().length];
  }
}
     