// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
package org.loudouncodes.jkarel;

import java.util.*;

/**
  * The abstract concept of a 'source' for map data.
  *
  * Implement this interface for a fixed room, a generated maze, or another
  * description of a level, then pass it to {@link Arena#loadMap(MapDataSource)}.
  * There are plans to have implementations of maze generating algorithms from
  * the book "mazes for programmers" implement this interface, and who knows
  * what other sources of map data there could be in the future... That's the
  * whole point of an interface, we are leaving the door open for future
  * developers to plug in ideas we haven't even thought of yet.
  *
  * Width and height must be positive. Collections, their entries, and beeper
  * colors must be non-null. Each beeper key must match its stack's location;
  * counts must be positive or {@link BeeperStack#INFINITY}.
  *
  * The loader copies the collections and beeper data. Walls retain their object
  * identity, so a source can supply a custom-drawn wall or a removable door.
  * The existing String overload continues to load XML files.
  */
public interface MapDataSource {

  /**
    * @return the width of this map in columns that robots can
    *         walk north and south on.
    */
  public int getWidth();
  
  /**
    * @return the height of this map in rows that robots can
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
