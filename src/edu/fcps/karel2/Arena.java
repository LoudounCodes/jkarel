package edu.fcps.karel2;

// my goal is to rename this 'arena'

import java.awt.*;
import javax.swing.*;

import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

/*
 * Copyright (C) Andy Street 2007
 *
 * This software is licensed under the GNU Public License v3.
 * See attached file LICENSE.TXT or contact the author for more information.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of Version 3 of the GNU General Public License as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
	 * The internal number used to identify an infinite number of beepers.
	 */
	public static final int INFINITY = -2;

	/**
	 * The font to write numbers on the beepers.
	 */
	private static Font beeperFont = null;
	/**
	 * The name of the font to write numbers on the beepers.
	 */
	private static String beeperFontName = "monospaced";
	/**
	 * The font size to write numbers on the beepers.
	 */
	private static int beeperFontSize = 10;

	/**
	 * Location of the image of karel facing north.
	 */
	private static final String nkarelLocation = "/icons/kareln.gif";
	/**
	 * Location of the image of karel facing east.
	 */
	private static final String ekarelLocation = "/icons/karele.gif";
	/**
	 * Location of the image of karel facing south.
	 */
	private static final String skarelLocation = "/icons/karels.gif";
	/**
	 * Location of the image of karel facing west.
	 */
	private static final String wkarelLocation = "/icons/karelw.gif";

	/**
	 * Image icon where the north-facing karel is loaed.
	 */
	private static ImageIcon nkarel = null;
	/**
	 * Image icon where the east-facing karel is loaded.
	 */
	private static ImageIcon ekarel = null;
	/**
	 * Image icon where the south-facing karel is loaded.
	 */
	private static ImageIcon skarel = null;
	/**
	 * Image icon where the west-facing karel is loaded.
	 */
	private static ImageIcon wkarel = null;

	/**
	 * Tracks whether or not the program has crashed.
	 */
	private static boolean isDead = false;

  private static Pacing pace = Pacing.FAST;
  
	/**
	 * Closes the current world if there is one, then creates a new WorldFrame
	 * with the specified map file.
	 * @param mapName the path to the map file to be loaded
	 */
	public static void openWorld(String mapName) {
		closeWorld();
		new WorldFrame(new WorldBackend(mapName));
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
  
	/**
	 * Placea beeper at some location x,y
	 * @param x the x location of the desired placement
	 * @param y the y location of the desired placement 
	 */
	
	public static void placeBeeper(int x, int y)
	{
		if (WorldBackend.getCurrent() == null) {
			Arena.openDefaultWorld();
		}

		if (isDead())
			return;
		WorldBackend.getCurrent().putBeepers(x, y, 1);
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
	 * Returns the font with which to render beepers.
	 */
	static Font getBeeperFont() {
		if (beeperFont == null)
			beeperFont = new Font(beeperFontName, Font.PLAIN, beeperFontSize);

		return beeperFont;
	}


	/**
	 * Returns the Karel ImageIcon associated with the specified direction
	 */
	static ImageIcon getKarelImage(Direction dir) {
		switch (dir) {
			case NORTH: {
					if (nkarel == null)
						nkarel = new ImageIcon(Arena.class.getResource(nkarelLocation));

					return nkarel;
				}
			case EAST: {
					if (ekarel == null)
						ekarel = new ImageIcon(Arena.class.getResource(ekarelLocation));

					return ekarel;
				}
			case SOUTH: {
					if (skarel == null)
						skarel = new ImageIcon(Arena.class.getResource(skarelLocation));

					return skarel;
				}
			case WEST: {
					if (wkarel == null)
						wkarel = new ImageIcon(Arena.class.getResource(wkarelLocation));

					return wkarel;
				}
			default:
				Arena.logger.severe("Karel image for direction " + dir + " not found!  Aborting...");
				System.exit(7);
				return null;
		}
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
