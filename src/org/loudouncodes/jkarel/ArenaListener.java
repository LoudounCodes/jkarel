package org.loudouncodes.jkarel;

/**
 * In a future version of this library, you will be able to
 * implement this interface and register as a Listener on an
 * arena, and get callbacks as things happen.
 */
public interface ArenaListener {

  // be careful with this robot reference. The constructor
  // has not yet returned when you are called.
  public void robotAdded(Robot r);
  
  public void robotRemoved(Robot r);
  public void robotMoved(Robot r);
  
  public void wallCollision(Wall w, Robot r);
  
  public void wallAdded(Wall w);
  public void wallRemoved(Wall w);
  
  public void beeperAdded(BeeperStack bs);
  public void beeperPickedUp(BeeperStack bs);
  public void beeperDropped(BeeperStack bs);
  
  public void userItemAdded(Item i);
  public void userItemDropped(Item i);
}