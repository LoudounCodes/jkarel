package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;

import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.List;
import java.util.ArrayList;

/**
 * The place where the action happens.
 *
 * The Arena is a Singleton Class... that means there is only
 * ever one of these in your program, you cannot say 'new Arena()',
 * and you will always call methods on this class starting with 
 * the classname, like Arena.getPace();.
 *
 * The Arena is one of the primary classes you'll work with in
 * this library.	It opens Arena files, displays them on screen,
 * brings 'life' to the robots as the move around the screen,
 * and pretty much everything else.
 *
 * Wow! That sounds like it does a lot of stuff! But if you ever
 * look at the code for an arena, you'll see that for the most
 * part, it relies on other classes to do most of that work.
 * This is a design pattern called a 'facade'.	Its a way we can
 * take a bunch of complexity and 'hide' it from the users of
 * this framework.
 */
public class Arena {

	/** The name and location of the default map.	 This is built-into
		* the jar file.
		*/
	public static final String DEFAULT_MAP = "/default.map";

	/** An instance of the java.util.logging class used in this
		* library.
		*/
	public static Logger logger = Logger.getLogger("Karel Logger");

	static {
		logger.setLevel(Level.WARNING);
	}
	
	/** the default ArenaFrame width. */
	public static int FRAME_WIDTH = 550;
	
	/** the default ArenaFrame width. */
	public static int FRAME_HEIGHT = 550;
	
	// will be private after an enumeration refactoring	 
	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 2;

	private static Pacing pace = Pacing.FAST;
	private static ArenaFrame theArenaFrame;
	
	/** no longer a singleton, we need to hold onto a reference. */
	protected static ArenaModel model;
	

	public static ArenaModel getModel() {
			return model;
	}
	
	/**
		* Opens a world with a name and location you provide.
		*
		* If the name is within the maps folder in the jar file, it will open
		* that file.
		*
		* @param mapName the name of the map to open.
		*/
	public static void openWorld(String mapName) {
		closeWorld();
		model = new ArenaModel(mapName);
		theArenaFrame = new ArenaFrame(model);
	}

	/**
		* Opens the default world, 10x10 with no walls or beepers.
		*/
	public static void openDefaultWorld() {
		closeWorld();
		model = new ArenaModel();
		theArenaFrame = new ArenaFrame(model);
	}

  public static void loadMap(String mapName) {
    model.parseMap(mapName);
  }
  
  
	/**
		* The Arena can be animated at different Paces; you can get
		* the current pace setting with this method.
		*
		* @see org.loudouncodes.jkarel.Pacing
		*
		* @return an enumeration defined in Pacing
		*/
	public static Pacing getPace() {
		return pace;
	}
	
	/**
		* The Arena can be animated at different Paces; you can set
		* the current pace setting with this method.
		*
		* @see org.loudouncodes.jkarel.Pacing
		* @param aPace the pace enumeration we are setting this Arena to.
		*/
	public static void setPace(Pacing aPace) {
		pace = aPace;
	}
		
	/**
		* Unless you are trying to do something advanced, you won't need
		* to use this method.
		*
		* The Arena wraps a bunch of different things and displays them
		* through this one interface.	 If for some reason you are learning
		* about Java's Swing UI library, you can get the instance of JFrame
		* that contains the Arena's UI.
		*
		* @return an instance of ArenaFrame animating the current UI.
		*/
	public static ArenaFrame getArenaFrame() {
		return theArenaFrame;
	}
	
	
	private static void closeWorld() {
		if (ArenaFrame.getCurrent() != null) {
			ArenaFrame.getCurrent().close();
		}
	}

	/**
		* Changes the size of the current world to the x and y values
		* you pass to this method.
		*
		* Note that this doesn't change the size of the Arena's Window
		* on your computer, this is the number of 'lanes' and 'avenues'
		* your robots will be able to roam around on.
		*
		* @param x the number of avenues in this world.
		* @param y the number of lanes in this world.
		*/
	public static void setSize(int x, int y) {
		if (getModel() == null)
			die("You must open a world before setting the size.");
	
		getModel().setSize(x, y);
	}
	
	/**
		* When called, this animates the Arena one frame forward.
		*/
	static void step() {
	
		ArenaPanel.getCurrent().repaint();
		pace.tick();
	}
	
	/**
		* If, for some reason, you need the program to end, call this
		* method.	 It will be marked as dead, all animations will stop,
		* and the reason you provided will be sent to the logger.
		*
		* @param reason the reason you killed the program.
		*/
	static void die(String reason) {
		Arena.logger.severe(reason);
		System.exit(0);
	}
	
	/**
		* adds a wall into the Arena north of the location specified by [x,y]
		*
		* @param x the x coordinate of the location where we are adding the wall.
		* @param y the y coordinate of the location where we are adding the wall,
		*/
	public static void addNorthWall(int x, int y) {
		Wall aWall = new Wall(x, y, Arena.HORIZONTAL);
		getModel().addWall(aWall);
		ArenaPanel.getCurrent().repaint();
    Arena.step();
	}
	
	/**
		* adds a wall into the Arena south of the location specified by [x,y]
		*
		* @param x the x coordinate of the location where we are adding the wall.
		* @param y the y coordinate of the location where we are adding the wall,
		*/
	public static void addSouthWall(int x, int y) {
		Wall aWall = new Wall(x, y-1, Arena.HORIZONTAL);
		getModel().addWall(aWall);
		ArenaPanel.getCurrent().repaint();
    Arena.step();
	}
	
	/**
		* adds a wall into the Arena east of the location specified by [x,y]
		*
		* @param x the x coordinate of the location where we are adding the wall.
		* @param y the y coordinate of the location where we are adding the wall,
		*/
	public static void addEastWall(int x, int y) {
		Wall aWall = new Wall(x, y, Arena.VERTICAL);
		getModel().addWall(aWall);
		ArenaPanel.getCurrent().repaint();
    Arena.step();
	}
	
	/**
		* adds a wall into the Arena west of the location specified by [x,y]
		*
		* @param x the x coordinate of the location where we are adding the wall.
		* @param y the y coordinate of the location where we are adding the wall,
		*/
	public static void addWestWall(int x, int y) {
		Wall aWall = new Wall(x-1, y, Arena.VERTICAL);
		getModel().addWall(aWall);
		ArenaPanel.getCurrent().repaint();
    Arena.step();
	}

	/**
		* adds beepers to the arena at the location specified by [x,y]
		*
		* @param x the x coordinate of the location where we are adding beepers.
		* @param y the y coordinate of the location where we are adding beepers.
		* @param beeperCount the number of beepers we are adding.
		*/
	public static void addBeepers(int x, int y, int beeperCount) {
		Location l = new Location(x, y);
		getModel().putBeepers(l, beeperCount);
		ArenaPanel.getCurrent().repaint();
    Arena.step();
	}
	
	/**
		* adds a single beeper to the arena at the location specified by [x,y]
		*
		* @param x the x coordinate of the location where we are adding beepers.
		* @param y the y coordinate of the location where we are adding beepers.
		*/
	public static void addBeeper(int x, int y) {
		addBeepers(x, y, 1);
	}
	
	
	
}
