package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;

public class Wall extends Item {

  // not used yet, but needed instead of magic ints defined in arena
  public enum Orientation {
    HORIZONTAL, VERTICAL;
  }
  
  private static ImageIcon wall;
  
	private final int WALL_WIDTH = 7;

	private int orientation;

  {
    wall = new ImageIcon(ArenaPanel.class.getResource("/icons/arena_wall.png"));
  }
  
	public Wall(int x, int y) {
		this(x, y, Arena.VERTICAL);
	}

	public Wall(int x, int y, int orientation) {
		super(x, y);

		if (orientation == Arena.VERTICAL ||
                orientation == Arena.HORIZONTAL)
			this.orientation = orientation;
		else {
			Arena.logger.warning("Invalid wall orientation: " + orientation + "...  Using VERTICAL.");
			this.orientation = Arena.VERTICAL;
		}
	}


	public int getOrientation() {
		return orientation;
	}

	public void render(Graphics g, int x, int y) {    
		g.setColor(Color.black);

		double width = ArenaPanel.getCurrent().getXBlockLength();
		double height = ArenaPanel.getCurrent().getYBlockLength();

		switch (orientation) {
			case Arena.VERTICAL:
        g.fillRect((int)(x + width / 2 - (WALL_WIDTH - 1) / 2),
                   (int)(y - height + height / 2),
                   WALL_WIDTH, (int)(height + 1));
				break;
        
        
			case Arena.HORIZONTAL:
				g.fillRect((int)(x - width / 2),
				           (int)(y - height / 2 - (WALL_WIDTH - 1) / 2),
				           (int)(width + 1), WALL_WIDTH);
				break;
		}
	}

	public String toString() {
		return "Wall { x: " + getX() + ", y: " + getY() +
		       ", orientation: " + orientation + " }";
	}

}
