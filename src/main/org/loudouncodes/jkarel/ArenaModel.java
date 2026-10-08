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
 * walls, beepers, and robots are stored here.  It also notifies ArenaListeners when actions occur.</p>
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

  private List<ArenaListener> listeners = new java.util.concurrent.CopyOnWriteArrayList<ArenaListener>();


  private int width = 10;
  private int height = 10;

  private Wall xAxisWall = null, yAxisWall = null;

  public ArenaModel(String mapName) {

    beepers = Collections.synchronizedMap(new HashMap<Location,BeeperStack> ());
    robots = Collections.synchronizedList(new ArrayList<Robot>());
    walls = Collections.synchronizedList(new ArrayList<Wall>());
    userItems = Collections.synchronizedList(new ArrayList<Item>());

    //add border walls here
    walls.add(xAxisWall = new Wall(1, 0, Arena.HORIZONTAL));
    walls.add(yAxisWall = new Wall(0, 1, Arena.VERTICAL));
  }

  public ArenaModel() {
    this(null);
  }

  public void addListener(ArenaListener l) {
    listeners.add(Objects.requireNonNull(l, "listener"));
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
      if (!robots.remove(r)) return;
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
    
  /** Add or remove beepers, preserving the color of a remaining stack. */
  public void putBeepers(Location l, int num) {
    putBeepersInternal(l, num, null);
  }

  private void putBeepersInternal(Location l, int num, Color color) {
    BeeperStack stack;
    synchronized (beepers) {
      BeeperStack old = beepers.get(l);
      int count = old == null ? 0 : old.getBeepers();
      if (num == BeeperStack.INFINITY || count == BeeperStack.INFINITY)
        count = BeeperStack.INFINITY;
      else
        count = Math.addExact(count, num);
      if (count != BeeperStack.INFINITY && count < 1) {
        beepers.remove(l);
        return;
      }
      stack = new BeeperStack(l.getX(), l.getY(), count);
      stack.setColor(color != null ? color : old != null ? old.getColor() : Color.YELLOW);
      // Robot locations move. A map key must keep its original coordinates.
      beepers.put(new Location(l.getX(), l.getY()), stack);
    }
    if (num > 0 || num == BeeperStack.INFINITY)
      for (ArenaListener listener : listeners) listener.beeperAdded(stack);
  }

  protected void notifyPutBeeper(Robot r) {
    for (ArenaListener l : listeners) l.beeperDropped(r);
  }

  protected void notifyPickedBeeper(Robot r) {
    for (ArenaListener l : listeners) l.beeperPickedUp(r);
  }

  void notifyWallCollision(Wall wall, Robot robot) {
    for (ArenaListener l : listeners) l.wallCollision(wall, robot);
  }

  /** Add beepers and color the entire stack; the latest colored drop wins. */
  public void putBeepers(Location l, int num, Color c) {
    putBeepersInternal(l, num, Objects.requireNonNull(c, "color"));
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
      if (!walls.remove(w)) return;
    }
    for (ArenaListener l:listeners) { l.wallRemoved(w); }    
  }


  public List<Wall> getWalls() {
    synchronized (walls) {
      return Collections.unmodifiableList(new ArrayList<Wall>(walls));
    }
  }

  boolean checkWall(int x, int y, int orientation) {
    return findWall(x, y, orientation) != null;
  }

  Wall findWall(int x, int y, int orientation) {
    synchronized (walls) {
      for (Wall wall : walls)
        if (wall.getOrientation() == orientation && wall.getX() == x && wall.getY() == y)
          return wall;
    }
    return null;
  }

  // accessors for dealing with generic items    
    
  public void addUserItem(Item i) {
    synchronized (userItems) {
      userItems.add(i);
    }
    for (ArenaListener listener : listeners) listener.userItemAdded(i);
    Arena.step();
  }
    
  public List<Item> getUserItems() {
    synchronized (userItems) {
      return Collections.unmodifiableList(new ArrayList<Item>(userItems));
    }
  }
    
  public void removeUserItem(Item i) {
    synchronized (userItems) {
      if (!userItems.remove(i)) return;
    }
    for (ArenaListener listener : listeners) listener.userItemDropped(i);
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

  // Map walls are expanded into the same unit segments used by the public API.
  public void addObject_wall(Attributes a) {
    int x = Integer.parseInt(a.get("x"));
    int y = Integer.parseInt(a.get("y"));
    int length = Integer.parseInt(a.get("length"));
    int style = a.get("style").equalsIgnoreCase("horizontal") ?
                Arena.HORIZONTAL : Arena.VERTICAL;

    for (int segment = 0; segment < length; segment++)
      addWall(new Wall(x + (style == Arena.HORIZONTAL ? segment : 0),
                       y + (style == Arena.VERTICAL ? segment : 0), style));
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

  protected void clearMap() {
    beepers = Collections.synchronizedMap(new HashMap<Location,BeeperStack> ());
    walls = Collections.synchronizedList(new ArrayList<Wall>());
    userItems = Collections.synchronizedList(new ArrayList<Item>());
  }
  
  
  protected void parseMap(String mapName) {
    try (InputStream source = getInputStreamForMap(mapName)) {
      Element e = new XMLParser().parse(source);
      if (e == null) throw new IllegalArgumentException("Invalid map: " + mapName);
      MapParser.initiateMap(this, e);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read map: " + mapName, e);
    }
    for (ArenaListener listener : listeners) listener.mapLoaded(mapName);
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
        throw new IllegalStateException("Default map file not found", g);
      }
    }

    return mapSource;
  }
}
