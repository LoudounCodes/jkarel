package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;
import java.util.Map;
import java.util.List;

public class WorldPanel extends JPanel {

	private static WorldPanel current = null;

	private WorldBackend wb = null;

	private final int X_BUFFER = 40, Y_BUFFER = 40;
	private final Color BACKGROUND = Color.white;

	private int blockWidth, blockHeight;

	public WorldPanel(WorldBackend wb) {
		super();

		this.wb = wb;
		current = this;

		setBackground(BACKGROUND);
	}

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

	public boolean isVisible(Location c) {
		if (c.x > getSize().width || c.y > getSize().height || c.x < 0 || c.y < 0)
			return false;

		return true;
	}

	protected double getXBlockLength() {
		return ((getSize().getWidth() - 2 * X_BUFFER)
		        * (1.0 / wb.getSize().x));
	}

	protected double getYBlockLength() {
		return ((getSize().getHeight() - 2 * Y_BUFFER)
		        * (1.0 / wb.getSize().y));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		renderScene(g);
	}

	void close() {
		current = null;
	}

	public static WorldPanel getCurrent() {
		return current;
	}

}
