package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;
import java.util.Map;
import java.util.List;

/**
 * The WorldPanel is responsible for actually drawing the world, according to
 * the specifications of the associated WorldBackend.
 * @author Andy Street, alstreet@vt.edu, 2007
 */
public class WorldPanel extends JPanel {

	private static WorldPanel current = null;

	private WorldBackend wb = null;

	private final int X_BUFFER = 40, Y_BUFFER = 40;
	private final Color BACKGROUND = Color.white;

	private int blockWidth, blockHeight;

	/**
	 * Constructs a WorldPanel associated with the specified WorldBackend.
	 */
	public WorldPanel(WorldBackend wb) {
		super();

		this.wb = wb;
		current = this;

		setBackground(BACKGROUND);
	}

	/**
	 * Renders the world paths (avenues and streets) using the specified
	 * Graphics object.
	 */
	private void renderGrid(Graphics g) {
		Location worldSize = wb.getSize();
		g.setColor(Color.red);

    int yMin = convertToYPixel(1) + blockHeight / 2;
    int yMax = convertToYPixel(worldSize.y);
    for (int i = 1; i <= worldSize.x; i++) {
      int x = convertToXPixel(i);
      g.drawLine(x, yMin, x, yMax);
    }
    
    int xMin = convertToXPixel(0) + blockHeight / 2;
    int xMax = convertToXPixel(worldSize.x);
    for (int i = 1; i <= worldSize.y; i++) {
      int y = convertToYPixel(i);
      g.drawLine(xMin, y , xMax, y);      
    }
	}

	/**
	 * Renders all beepers contained by the world using the specified
	 * Graphics object.
	 */
	private void renderBeepers(Graphics g) {
		Map<Location, BeeperStack> beepers = wb.getBeepers();
		synchronized (beepers) {
			for (BeeperStack b : beepers.values()) {
				b.render(g,
                 convertToXPixel(b.getLocation().getX()),
                 convertToYPixel(b.getLocation().getY()));
			}
		}
	}

	/**
	 * Renders all Walls contained by the world using the specified Graphics
	 * object.
	 */
	private void renderWalls(Graphics g) {
		List<Wall> walls = wb.getWalls();
		synchronized (walls) {
			for (Wall w : walls) {
				w.render(g,
                 convertToXPixel(w.getLocation().getX()),
                 convertToYPixel(w.getLocation().getY()));
			}
		}
	}

	/**
	 * Renders all Robots contained by the world using the specified Graphics
	 * object.
	 */
	private void renderRobots(Graphics g) {
		List<Robot> robots = wb.getRobots();
		synchronized (robots) {
			for (Robot r : robots) {
				r.render(g,
                 convertToXPixel(r.getLocation().getX()),
                 convertToYPixel(r.getLocation().getY()));
			}
		}
	}

	/**
	 * Renders the world.
	 */
	private void renderScene(Graphics g) {
		blockWidth = (int)getXBlockLength();
		blockHeight = (int)getYBlockLength();

		renderGrid(g);
		renderBeepers(g);
		renderRobots(g);
		renderWalls(g);
	}

	public int convertToXPixel(int xLocation) {
		Dimension panelSize = getSize(); //In pixels
		Location worldSize = wb.getSize(); //In Cartesian locations
		int width = panelSize.width;

		int x = (int)((width - 2 * X_BUFFER) *
		               (xLocation * 1.0 / worldSize.x)) +
		         X_BUFFER - blockWidth / 2;
		return x;
	}
  
	public int convertToYPixel(int yLocation) {
		Dimension panelSize = getSize(); //In pixels
		Location worldSize = wb.getSize(); //In Cartesian locations
		int height = panelSize.height;

		int y = (int)((height - 2 * Y_BUFFER) *
		               ((worldSize.y - yLocation) * 1.0 / worldSize.y)) +
		         Y_BUFFER + blockHeight / 2;
		return y;
	}
  
  
  
	/**
	 * Returns whether the specified Location is within the size
	 * specifications of the world.
	 */
	public boolean isVisible(Location c) {
		if (c.x > getSize().width || c.y > getSize().height || c.x < 0 || c.y < 0)
			return false;

		return true;
	}

	/**
	 * Returns the number of pixels between two consecutive vertical paths
	 * (avenues). Used for creating walls.
	 */
	protected double getXBlockLength() {
		return ((getSize().getWidth() - 2 * X_BUFFER)
		        * (1.0 / wb.getSize().x));
	}

	/**
	 * Returns the number of pixels between two consecutive horizontal paths
	 * (streets). Used for creating walls.
	 */
	protected double getYBlockLength() {
		return ((getSize().getHeight() - 2 * Y_BUFFER)
		        * (1.0 / wb.getSize().y));
	}

	/**
	 * Paints the world.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		renderScene(g);
	}

	/**
	 * Disables the WorldPanel.
	 */
	void close() {
		current = null;
	}

	/**
	 * Returns the current (most recently created) WorldPanel.
	 */
	public static WorldPanel getCurrent() {
		return current;
	}

}
