package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;
import java.util.Map;
import java.util.List;

/**
  * implementation of a JPanel that holds the Arena.
  *
  * In an intro class, you will not worry about this class; this exists
  * solely to create a user interface with the computer. In a more advanced
  * class, you may learn about user interfaces, Swing, JFrames and JPanels,
  * but even then, you'll never need to worry about this class directly.
  */
public class ArenaPanel extends JPanel {

  /** variable for implementing the Singleton pattern. */
	private static ArenaPanel current = null;

  /** the model passed in at construction. */
	private ArenaModel model = null;

	private final int X_BUFFER = 40;
  private final int Y_BUFFER = 40;
	private int blockWidth;
  private int blockHeight;
  
  
  /** the background color when drawing the panel. */
	private final Color BACKGROUND = Color.white;



  /** the ImageIcon used to draw the floor of the Arena. */
  private ImageIcon floor;
  {
    floor = new ImageIcon(ArenaPanel.class.getResource("/icons/arena_floor.png"));
  }
  
  
  
  /**
    * constructor. This is protected because only code inside the library
    * should be creating these.
    *
    * @param model the ArenaModel that holds the data this class is
    *              responsible for drawing.
    */
	protected ArenaPanel(ArenaModel model) {
		super();

		this.model = model;
		current = this;

		setBackground(BACKGROUND);
	}

  /**
    * Called when the JFrame is closed, this is our chance
    * to clean up our singleton reference.
    */
	void close() {
		current = null;
	}

  /**
    * As this implements the Singleton pattern (a design pattern) you
    * typically won't learn in high school) this is the method that
    * returns the current singleton instance.
    *
    * @return the current ArenaPanel singleton.
    */
	public static ArenaPanel getCurrent() {
		return current;
	}


	public int convertToXPixel(int xLocation) {
		Dimension panelSize = getSize(); //In pixels
		Location worldSize = model.getSize(); //In Cartesian locations
		int width = panelSize.width;

		int x = (int)((width - 2 * X_BUFFER) *
		               (xLocation * 1.0 / worldSize.getX())) +
		         X_BUFFER - blockWidth / 2;
		return x;
	}
  
	public int convertToYPixel(int yLocation) {
		Dimension panelSize = getSize(); //In pixels
		Location worldSize = model.getSize(); //In Cartesian locations
		int height = panelSize.height;

		int y = (int)((height - 2 * Y_BUFFER) *
		               ((worldSize.getY() - yLocation) * 1.0 / worldSize.getY())) +
		         Y_BUFFER + blockHeight / 2;
		return y;
	}

	public boolean isVisible(Location c) {
		return !(c.getX() > getSize().width || c.getY() > getSize().height || c.getY() < 0 || c.getY() < 0);
	}

	protected double getXBlockLength() {
		return ((getSize().getWidth() - 2 * X_BUFFER)
		        * (1.0 / model.getSize().getX()));
	}

	protected double getYBlockLength() {
		return ((getSize().getHeight() - 2 * Y_BUFFER)
		        * (1.0 / model.getSize().getY()));
	}





  /**
    * As this class is a JPanel, it will be responsible for drawing itself.
    *
    * This method, and all the methods below it in this class, are responsible
    * for drawing the arena.  This is where the magic happens.
    *
    * @param g the graphics context we need to draw into.
    */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		renderScene(g);
	}
  
  
  private void renderFloor(Graphics g) {
		Location worldSize = model.getSize();

    for (int i = 0; i <= worldSize.getX(); i++) {
      for (int j = 0; j <= worldSize.getY(); j++) {
        int x1 = convertToXPixel(i);
        int y1 = convertToYPixel(j);
        int x2 = convertToXPixel(i+1);
        int y2 = convertToYPixel(j+1);
        g.drawImage(floor.getImage(),
                    x1, y1, x2, y2,
                    0, 0, floor.getIconWidth(), floor.getIconHeight(),
                    Color.WHITE, null);
      }
    }
  }


	private void renderBeepers(Graphics g) {
		Map<Location, BeeperStack> beepers = model.getBeepers();
		synchronized (beepers) {
			for (BeeperStack b : beepers.values()) {
				b.render(g,
                 convertToXPixel(b.getLocation().getX()),
                 convertToYPixel(b.getLocation().getY()));
			}
		}
	}

	private void renderWalls(Graphics g) {
		List<Wall> walls = model.getWalls();
		synchronized (walls) {
			for (Wall w : walls) {
				w.render(g,
                 convertToXPixel(w.getLocation().getX()),
                 convertToYPixel(w.getLocation().getY()));
			}
		}
	}

	private void renderRobots(Graphics g) {
		List<Robot> robots = model.getRobots();
		synchronized (robots) {
			for (Robot r : robots) {
				r.render(g,
                 convertToXPixel(r.getLocation().getX()),
                 convertToYPixel(r.getLocation().getY()));
			}
		}
	}

	private void renderScene(Graphics g) {
		blockWidth = (int)getXBlockLength();
		blockHeight = (int)getYBlockLength();

    renderFloor(g);
		renderBeepers(g);
		renderRobots(g);
		renderWalls(g);
	}




}
