package org.loudouncodes.jkarel;

/**
 * In a future version of this library, you will be able to
 * implement this interface and register as a Listener on an
 * arena, and get callbacks as things happen.
 */
public interface ArenaListener {

  // be careful with this robot reference. The constructor
  // has not yet returned when you are called.
  default void robotAdded(Robot r){};
  
  default void robotRemoved(Robot r){};
  default void robotMoved(Robot r){};
  
  default void wallCollision(Wall w, Robot r){};
  
  default void wallAdded(Wall w){};
  default void wallRemoved(Wall w){};
  
  default void beeperAdded(BeeperStack bs){};
  default void beeperPickedUp(BeeperStack bs){};
  default void beeperDropped(BeeperStack bs){};
  
  default void userItemAdded(Item i){};
  default void userItemDropped(Item i){};
}