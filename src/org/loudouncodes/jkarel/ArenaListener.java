package org.loudouncodes.jkarel;

/**
 * In a future version of this library, you will be able to
 * implement this interface and register as a Listener on an
 * arena, and get callbacks as things happen.
 */
public interface ArenaListener {

  public void robotAdded();
  public void wallAdded();
  public void wallRemoved();
  public void beeperAdded();
  public void beeperPickedUp();
  public void beeperDropped();
  public void robotMoved();
  public void wallHit();
  
}