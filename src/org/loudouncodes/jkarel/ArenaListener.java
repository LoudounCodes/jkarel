package org.loudouncodes.jkarel;



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