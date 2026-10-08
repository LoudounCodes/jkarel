package org.loudouncodes.jkarel;

/**
 * Observe arena actions by overriding only the callbacks you need, then calling
 * Arena.addListener(listener). Callbacks run synchronously on the action's thread,
 * after its state changes and before animation pacing. Keep callbacks short;
 * do not move robots or run game loops inside them. Read state and update counters.
 * robotAdded runs inside the Robot constructor: subclass fields are not ready yet.
 * beeperAdded reports additions to a stack (including robot drops); beeperDropped
 * and beeperPickedUp identify the robot. A drop therefore sends both addition and
 * drop callbacks. wallCollision occurs before a blocked action throws an exception.
 * userItemDropped is the historical name for removal of a user item.
 * No callback is sent for turning or changing color.
 */
public interface ArenaListener {

  // be careful with this robot reference. The constructor
  // has not yet returned when you are called.
  default void robotAdded(Robot r){};
  
  default void robotRemoved(Robot r){};
  default void robotMoved(Robot r){};
  default void beeperPickedUp(Robot r){};
  default void beeperDropped(Robot r){};

  
  default void wallCollision(Wall w, Robot r){};
  
  default void wallAdded(Wall w){};
  default void wallRemoved(Wall w){};
  
  default void beeperAdded(BeeperStack bs){};

  
  default void userItemAdded(Item i){};
  default void userItemDropped(Item i){};
  
  default void mapLoaded(String mapName) {};
}
