package org.loudouncodes.jkarel;

import java.util.*;

public interface MapDataSource {

  public int getWidth();
  public int getHeight();
	public Map<Location, BeeperStack> getBeepers();
	public List<Wall> getWalls();
}