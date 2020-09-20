package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;

import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.List;
import java.util.ArrayList;

public class Arena {

	public static final String DEFAULT_MAP = "/default.map";

  public static Logger logger = Logger.getLogger("Karel Logger");

  static {
    logger.setLevel(Level.WARNING);
  }
  

	public static int FRAME_WIDTH = 550;

	public static int FRAME_HEIGHT = 550;

	public static final int VERTICAL = 1;

	public static final int HORIZONTAL = 2;

	private static boolean isDead = false;

  private static Pacing pace = Pacing.FAST;
  
  private static ArenaFrame theArenaFrame;

  private static List<ArenaListener> listeners = new ArrayList<ArenaListener>();
  
  
	public static void openWorld(String mapName) {
		closeWorld();
		theArenaFrame = new ArenaFrame(new ArenaModel(mapName));
	}

  public static ArenaFrame getArenaFrame() {
    return theArenaFrame;
  }

	public static void openDefaultWorld() {
		closeWorld();
		new ArenaFrame(new ArenaModel());
	}

  public static Pacing getPace() {
    return pace;
  }

  public static void setPace(Pacing aPace) {
    pace = aPace;
  }
  
	public static void placeBeeper(Location l)
	{
		if (ArenaModel.getCurrent() == null) {
			Arena.openDefaultWorld();
		}

		if (isDead())
			return;
		ArenaModel.getCurrent().putBeepers(l, 1);
		ArenaPanel.getCurrent().repaint();
	}
	
	private static void closeWorld() {
		if (ArenaFrame.getCurrent() != null)
			ArenaFrame.getCurrent().close();
	}

	public static void setSize(int x, int y) {
		if (ArenaModel.getCurrent() == null)
			openDefaultWorld();

		ArenaModel.getCurrent().setSize(x, y);
	}

	static void step() {

		ArenaPanel.getCurrent().repaint();
		pace.tick();
	}

	static void die(String reason) {
		isDead = true;
		Arena.logger.severe(reason);
		System.exit(0);
	}

	public static boolean isDead() {
		return isDead;
	}
  
  public static void addNorthWall(int x, int y) {
    Wall aWall = new Wall(x, y, 1, Arena.HORIZONTAL);
    ArenaModel.getCurrent().addWall(aWall);
		ArenaPanel.getCurrent().repaint();
  }
  
  public static void addSouthWall(int x, int y) {
    Wall aWall = new Wall(x, y-1, 1, Arena.HORIZONTAL);
    ArenaModel.getCurrent().addWall(aWall);
		ArenaPanel.getCurrent().repaint();
  }
  
  public static void addEastWall(int x, int y) {
    Wall aWall = new Wall(x, y, 1, Arena.VERTICAL);
    ArenaModel.getCurrent().addWall(aWall);
		ArenaPanel.getCurrent().repaint();
  }
  
  public static void addWestWall(int x, int y) {
    Wall aWall = new Wall(x-1, y, 1, Arena.VERTICAL);
    ArenaModel.getCurrent().addWall(aWall);
		ArenaPanel.getCurrent().repaint();
  }

  public static void addBeepers(int x, int y, int beeperCount) {
    Location l = new Location(x, y);
    ArenaModel.getCurrent().putBeepers(l, beeperCount);
		ArenaPanel.getCurrent().repaint();
  }
  
}
