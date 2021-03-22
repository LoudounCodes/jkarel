package org.loudouncodes.jkarel;

/**
 * In a future version of this library, you will be able to
 * implement this interface and register as a Listener on an
 * arena, and get callbacks as things happen.
 */
public interface ArenaListener {

  public boolean robotAdded(Robot r);
  public boolean robotRemoved(Robot r);
  public boolean robotMoved(Robot r);
  
  public boolean wallCollision(Wall w, Robot r);
  
  public boolean wallAdded(Wall w);
  public boolean wallRemoved(Wall w);
  
  public boolean beeperAdded(BeeperStack bs);
  public boolean beeperPickedUp(BeeperStack bs);
  public boolean beeperDropped(BeeperStack bs);
  
  public boolean userItemAdded(Item i);
  public boolean userItemDropped(Item i);
}