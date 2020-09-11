package org.loudouncodes.jkarel;

import org.loudouncodes.jkarel.xml.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class WorldBackend {

	private static WorldBackend current = null;

	private Map<Location, BeeperStack> beepers;
	private List<Robot> robots;
	private List<Wall> walls;

	private int width = 10;
	private int height = 10;

	private Wall xAxisWall = null, yAxisWall = null;

	public WorldBackend(String mapName) {
		current = this;

		beepers = Collections.synchronizedMap(new HashMap < Location,
		                                      BeeperStack > ());
		robots = Collections.synchronizedList(new ArrayList<Robot>());
		walls = Collections.synchronizedList(new ArrayList<Wall>());

		walls.add(xAxisWall = new Wall(1, 0, width, Arena.HORIZONTAL));
		walls.add(yAxisWall = new Wall(0, 1, height, Arena.VERTICAL));

		parseMap(mapName);
	}

	public WorldBackend() {
		this(null);
	}

	void addRobot(Robot r) {
		synchronized (robots) {
			robots.add(r);
		}
		Arena.step();
	}

	void addRobotInternal(Robot r) {
		synchronized (robots) {
			robots.add(r);
		}
	}

	void removeRobot(Robot r) {
		synchronized (robots) {
			robots.remove(r);
		}
		Arena.step();
	}

	public void putBeepers(Location l, int num) {
		if (num == BeeperStack.INFINITY) {
			synchronized (beepers) {
				beepers.put(l, new BeeperStack(l.getX(), l.getY(), num));
			}
			return;
		}
		synchronized (beepers) {
			int oldBeepers = 0;

			BeeperStack b;
			if ((b = beepers.get(l)) != null)
				oldBeepers = b.getBeepers();

			if (oldBeepers == BeeperStack.INFINITY)
				return;

			if (oldBeepers + num < 1)
				beepers.remove(l);
			else
				beepers.put(l, new BeeperStack(l.getX(), l.getY(), num + oldBeepers));
		}
	}

	public void addWall(Wall w) {
		synchronized (walls) {
			walls.add(w);
		}
	}

	public void addObject_beeper(Attributes a) {
		int x = Integer.parseInt(a.get("x"));
		int y = Integer.parseInt(a.get("y"));
		String num = a.get("num");

		if (num.equalsIgnoreCase("infinite"))
			putBeepers(new Location(x, y), BeeperStack.INFINITY);
		else
			putBeepers(new Location(x, y), Integer.parseInt(num));
	}

	public void addObject_wall(Attributes a) {
		int x = Integer.parseInt(a.get("x"));
		int y = Integer.parseInt(a.get("y"));
		int length = Integer.parseInt(a.get("length"));
		int style = a.get("style").equalsIgnoreCase("horizontal") ?
		            Arena.HORIZONTAL : Arena.VERTICAL;

		addWall(new Wall(x, y, length, style));
	}

	public void addObject_robot(Attributes a) {
		int x = Integer.parseInt(a.get("x"));
		int y = Integer.parseInt(a.get("y"));
		int directionVal = Integer.parseInt(a.get("direction"));
    Direction direction = Direction.values()[directionVal];
		int beepers = Integer.parseInt(a.get("beepers"));

		new Robot(x, y, direction, beepers, true);
	}

	public void loadProperties_defaultSize(Attributes a) {
		int w = Integer.parseInt(a.get("width"));
		int h = Integer.parseInt(a.get("height"));

		Arena.setSize(w, h);
	}

	void parseMap(String mapName) {
		Element e = new XMLParser().parse(getInputStreamForMap(mapName));
		WorldParser.initiateMap(e);
	}

	private InputStream getInputStreamForMap(String fileName) {
		
    InputStream mapSource = null;

		try {
			if (fileName == null)
				throw new FileNotFoundException();
      
			mapSource = getClass().getResourceAsStream(fileName);
      if (mapSource == null) {
			  mapSource = new FileInputStream(new File(fileName));
      }
		}
		catch (FileNotFoundException e) {
			if (fileName != null)
				Arena.logger.warning("Map " + fileName + " not found, using default map file...");

			try {
				mapSource = getClass().getResourceAsStream(Arena.DEFAULT_MAP);

				if (mapSource == null)
					throw new FileNotFoundException();
			}
			catch (Exception g) {
				Arena.logger.severe("Default map file not found!  Aborting...");
				System.exit(1);
			}
		}

		return mapSource;
	}

	Map<Location, BeeperStack> getBeepers() {
		return beepers;
	}

	List<Wall> getWalls() {
		return walls;
	}

	List<Robot> getRobots() {
		return robots;
	}

	boolean checkWall(int x, int y, int style) {
		synchronized (walls) {
			switch (style) {
				case Arena.HORIZONTAL:
					for (Wall w : walls)
						if (w.getStyle() == style)
							if (w.getY() == y &&
							                x >= w.getX() &&
							                x < w.getX() + w.getLength())
								return true;

					break;
				case Arena.VERTICAL:
				default:
					for (Wall w : walls)
						if (w.getStyle() == style)
							if (w.getX() == x &&
							                y >= w.getY() &&
							                y < w.getY() + w.getLength())
								return true;

					break;
			}
		}
		return false;
	}

	boolean checkBeepers(Location l) {
		return beepers.get(l) != null;
	}

	boolean isNextToARobot(Robot r, Location l) {
		synchronized (robots) {
			for (Robot robot : robots)
				if (robot != r && robot.getX() == l.getX() && robot.getY() == l.getY())
					return true;

			return false;
		}
	}

	void setSize(int width, int height) {
		if (this.width != width) {
			this.width = width;
			walls.remove(xAxisWall);
			walls.add(xAxisWall = new Wall(1, 0, width, Arena.HORIZONTAL));
		}

		if (this.height != height) {
			this.height = height;
			walls.remove(yAxisWall);
			walls.add(yAxisWall = new Wall(0, 1, height, Arena.VERTICAL));
		}
	}

	public Location getSize() {
		return new Location(width, height);
	}

	void close() {
		current = null;
	}

	/**
	 * Returns the currently running instance of WorldBackend.
   * Ew. singleton pattern.  This won't do in the new world order...
	 */
	public static WorldBackend getCurrent() {
		return current;
	}
}
