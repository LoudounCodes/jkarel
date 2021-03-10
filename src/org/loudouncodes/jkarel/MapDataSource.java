package org.loudouncodes.jkarel;

import java.util.*;

/**
  * The abstract concept of a 'source' for map data.
  *
  * When this interface was first extracted, only the xml parser for map
  * files implemented it.  There are plans to have implementations of
  * maze generating algorithms from the book "mazes for programmers"
  * implement this interface, and who knows what other sources of map data
  * there could be in the future... That's the whole point of an interface,
  * we are leaving the door open for future developers to plug in ideas
  * we haven't even thought of yet.
  */
public interface MapDataSource {

  /**
    * @return the width of this map in columns that robots can
    *         walk north and south on.
    */
  public int getWidth();
  
  /**
    * @return the hright of this map in rows that robots can
    *        walk east and west on.
    */
  public int getHeight();
  
  /**
    * @return a map of locations and stacks of beepers that are
    *         present on this map.
    */
  public Map<Location, BeeperStack> getBeepers();
  
  /**
    * @return a list of walls that are present on this map.
    */
  public List<Wall> getWalls();
}