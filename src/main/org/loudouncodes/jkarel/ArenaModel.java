package org.loudouncodes.jkarel;

// breaks an important design rule with knowledge of a sub-package.
// need to fix that.
import org.loudouncodes.jkarel.xml.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.Color;

/**
 * <p>Represents a back-end 'model' of the area; the locations of all the
 * walls, beepers, and robots are stored here.  In the future, it will
 * also be responsible for notifying ArenaListeners of ArenaEvents.</p>
 *
 * <p>As I have been refactoring the FCPS version of this code, a lot of
 * complexity has been pushed into this class.  It will eventually
 * be given the same treatment as the rest of the complexity and removed.</p>
 *
 */
public class ArenaModel {

	private Map<Location, BeeperStack> beepers;
	private List<Robot> robots;
	private List<Wall> walls;
    
    // The FCPS design here is horrible. I want to add some generic
    // user-created subclasses of items and have them 'do the right thing',
    // but it makes the design awkward because we have other arrays for
    // specific kinds of items (above).  We sould fix this someday with
    // better design.
	private List<Item> userItems;
    
    private List<ArenaListener> listeners = new ArrayList<ArenaListener>();
    
    
    
	private int width = 10;
	private int height = 10;

	private Wall xAxisWall = null, yAxisWall = null;

	public ArenaModel(String mapName) {

		beepers = Collections.synchronizedMap(new HashMap < Location,
		                                      BeeperStack > ());
		robots = Collections.synchronizedList(new ArrayList<Robot>());
		walls = Collections.synchronizedList(new ArrayList<Wall>());
		userItems = Collections.synchronizedList(new ArrayList<Item>());

        //add border walls here
		walls.add(xAxisWall = new Wall(1, 0, Arena.HORIZONTAL));
		walls.add(yAxisWall = new Wall(0, 1, Arena.VERTICAL));

        // arenamodel needs to be a true model object. parsing xml
        // is not the responsibility of a model, especially in a constructor.
		parseMap(mapName);
	}

	public ArenaModel() {
		this(null);
	}

    public void addListener(ArenaListener l) {
        listeners.add(l);
    }

    public void removeListener(ArenaListener l) {
        listeners.remove(l);
    }
    
	public Location getSize() {
		return new Location(width, height);
	}
    
// accessors for dealing with robots    
	List<Robot> getRobots() {
		return robots;
	}
    
	void addRobot(Robot r) {
		synchronized (robots) {
			robots.add(r);
		}
        
        for (ArenaListener l:listeners) { l.robotAdded(r); }
        
		Arena.step();
	}

	void removeRobot(Robot r) {
		synchronized (robots) {
			robots.remove(r);
		}
        
        for (ArenaListener l:listeners) { l.robotRemoved(r); }
        
		Arena.step();
	}

    void notifyMoved(Robot r) {
        for (ArenaListener l:listeners) { l.robotMoved(r); }
    }
// accessors for dealing with beepers
	Map<Location, BeeperStack> getBeepers() {
		return beepers;
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

  // lets set a color for the dropped beeper...
	public void putBeepers(Location l, int num, Color c) {
    putBeepers(l, num);
    beepers.get(l).setColor(c);
  }
  
	boolean checkBeepers(Location l) {
		return beepers.get(l) != null;
	}
    
    
    
    
// accessors for dealing with walls    
	public void addWall(Wall w) {
		synchronized (walls) {
			walls.add(w);
		}
        for (ArenaListener l:listeners) { l.wallAdded(w); }
        
	}

	public void removeWall(Wall w) {
		synchronized (walls) {
			walls.remove(w);
		}
        for (ArenaListener l:listeners) { l.wallRemoved(w); }
        
	}


	public List<Wall> getWalls() {
		return Collections.unmodifiableList(walls);
	}

    // this needs some deobfuscation.
	boolean checkWall(int x, int y, int orientation) {
		synchronized (walls) {
			switch (orientation) {
				case Arena.HORIZONTAL:
					for (Wall w : walls)
						if (w.getOrientation() == orientation)
							if (w.getY() == y &&
							                x >= w.getX() &&
							                x < w.getX() + 1)
								return true;
					break;
				case Arena.VERTICAL:
				default:
					for (Wall w : walls)
						if (w.getOrientation() == orientation)
							if (w.getX() == x &&
							                y >= w.getY() &&
							                y < w.getY() + 1)
								return true;

					break;
			}
		}
		return false;
	}
    
    
    
// accessors for dealing with generic items    
    
    public void addUserItem(Item i) {
		synchronized (userItems) {
			userItems.add(i);
		}
		Arena.step();
    }
    
    public List<Item> getUserItems() {
        return Collections.unmodifiableList(userItems);
    }
    
    public void removeUserItem(Item i) {
		synchronized (userItems) {
			userItems.remove(i);
		}
		Arena.step();
    }




	boolean isNextToARobot(Robot r, Location l) {
		synchronized (robots) {
			for (Robot robot : robots)
				if (robot != r && robot.getX() == l.getX() && robot.getY() == l.getY())
					return true;

			return false;
		}
	}

    // need to add border walls back
	void setSize(int width, int height) {
		if (this.width != width) {
			this.width = width;
			walls.remove(xAxisWall);
			walls.add(xAxisWall = new Wall(1, 0, Arena.HORIZONTAL));
		}

		if (this.height != height) {
			this.height = height;
			walls.remove(yAxisWall);
			walls.add(yAxisWall = new Wall(0, 1, Arena.VERTICAL));
		}
	}





    // stuff having to do with xml parsing

	public void addObject_beeper(Attributes a) {
		int x = Integer.parseInt(a.get("x"));
		int y = Integer.parseInt(a.get("y"));
		String num = a.get("num");

		if (num.equalsIgnoreCase("infinite"))
			putBeepers(new Location(x, y), BeeperStack.INFINITY);
		else
			putBeepers(new Location(x, y), Integer.parseInt(num));
	}

    // ignoring length in the xml
	public void addObject_wall(Attributes a) {
		int x = Integer.parseInt(a.get("x"));
		int y = Integer.parseInt(a.get("y"));
		int length = Integer.parseInt(a.get("length"));
		int style = a.get("style").equalsIgnoreCase("horizontal") ?
		            Arena.HORIZONTAL : Arena.VERTICAL;

		addWall(new Wall(x, y, style));
	}

	public void addObject_robot(Attributes a) {
		int x = Integer.parseInt(a.get("x"));
		int y = Integer.parseInt(a.get("y"));
		int directionVal = Integer.parseInt(a.get("direction"));
    Direction direction = Direction.values()[directionVal];
		int beepers = Integer.parseInt(a.get("beepers"));

		new Robot(x, y, direction, beepers);
	}

    public void loadProperties_defaultSize(Attributes a) {
        width = Integer.parseInt(a.get("width"));
        height = Integer.parseInt(a.get("height"));
    }

	void parseMap(String mapName) {
		Element e = new XMLParser().parse(getInputStreamForMap(mapName));
		WorldParser.initiateMap(this, e);
	}

	private InputStream getInputStreamForMap(String fileName) {	
      InputStream mapSource = null;
      System.out.println(fileName);
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
}
