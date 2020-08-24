package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;

import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

/**
 * Arena houses most of the static constants used in object creation and
 * rendering, Karel file locations and images, and the speed at which the
 * WorldPanel updates.  The Arena.step() method is responsible for the
 * animation of the Panel.
 * @author Andy Street, alstreet@vt.edu, 2007
 * @author Craig Saperstein, cmsaperstein@gmail.com, 2010
 */

public class Arena {

	/**
	 * Map loaded if no other map is specified.
	 */
	public static final String DEFAULT_MAP = "/default.map";

  public static Logger logger = Logger.getLogger("Karel Logger");

  static {
    logger.setLevel(Level.WARNING);
  }
  
  
  // a lot of stuff here might really belong in robot.
  

	/**
	 * Default width of the arena window.
	 */
	public static int FRAME_WIDTH = 550;
	/**
	 * Default height of the arena window
	 */
	public static int FRAME_HEIGHT = 550;

	//Walls
	/**
	 * Define for a vertical wall.
	 */
	public static final int VERTICAL = 1;
	/**
	 * Define for a horizontal wall.
	 */
	public static final int HORIZONTAL = 2;



	/**
	 * Tracks whether or not the program has crashed.
	 */
	private static boolean isDead = false;

  private static Pacing pace = Pacing.FAST;
  
  
  private static WorldFrame theWorldFrame;
  
	/**
	 * Closes the current world if there is one, then creates a new WorldFrame
	 * with the specified map file.
	 * @param mapName the path to the map file to be loaded
	 */
	public static void openWorld(String mapName) {
		closeWorld();
		theWorldFrame = new WorldFrame(new WorldBackend(mapName));
	}

  public static WorldFrame getWorldFrame() {
    return theWorldFrame;
  }
  
	/**
	 * Closes the current world if there is one, then creates a new WorldFrame
	 * with the default map.
	 */
	public static void openDefaultWorld() {
		closeWorld();
		new WorldFrame(new WorldBackend());
	}

  public static Pacing getPace() {
    return pace;
  }

  public static void setPace(Pacing aPace) {
    pace = aPace;
  }
  
	
	public static void placeBeeper(Location l)
	{
		if (WorldBackend.getCurrent() == null) {
			Arena.openDefaultWorld();
		}

		if (isDead())
			return;
		WorldBackend.getCurrent().putBeepers(l, 1);
		WorldPanel.getCurrent().repaint();
	}
	
	/**
	 * If a WorldFrame has been previously created, its close method is called,
	 * closing the associated WorldFrame and WorldBackend before disposing of
	 * the current WorldFrame.
	 */
	private static void closeWorld() {
		if (WorldFrame.getCurrent() != null)
			WorldFrame.getCurrent().close();
	}

	/**
	 * The same as calling WorldBackend.setSize(x, y)
	 * @param x
	 * @param y
	 */
	public static void setSize(int x, int y) {
		if (WorldBackend.getCurrent() == null)
			openDefaultWorld();

		WorldBackend.getCurrent().setSize(x, y);
	}

	/**
	 * Repaints the WorldPanel, then pauses the Thread for a period of time
	 * based on the current Arena speed.
	 */
	static void step() {

		WorldPanel.getCurrent().repaint();
		pace.tick();
	}


	/**
	 * Outputs the reason why the Arena cannot continue to update, calls
	 * hang(), then exits when hang() returns.
	 */
	static void die(String reason) {
		isDead = true;
		Arena.logger.severe(reason);
		hang();
		System.exit(0);
	}

	/**
	 * Blocks the Thread until input is given to System.in.
	 */
	private static void hang() { //A bit hacky, but it works
		try {
			System.in.read();
		}
		catch (Exception e) { }
	}

	/**
	 * Returns whether or not the Arena is currently dead
	 * (no longer able to update).
	 */
	public static boolean isDead() {
		return isDead;
	}
}
