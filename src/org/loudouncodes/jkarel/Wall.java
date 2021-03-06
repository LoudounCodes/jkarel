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
	private int length;

  {
    wall = new ImageIcon(ArenaPanel.class.getResource("/icons/arena_wall.png"));
  }
  
	public Wall(int x, int y) {
		this(x, y, Arena.VERTICAL);
	}

	public Wall(int x, int y, int orientation) {
		this(x, y, 1, orientation);
	}

	public Wall(int x, int y, int length, int orientation) {
		super(x, y);

		this.length = length;

		if (orientation == Arena.VERTICAL || orientation == Arena.HORIZONTAL)
			this.orientation = orientation;
		else {
			Arena.logger.warning("Invalid wall orientation: " + orientation + "...  Using VERTICAL.");
			this.orientation = Arena.VERTICAL;
		}
	}

	public int getLength() {
		return length;
	}

	public int getStyle() {
		return orientation;
	}

	public void render(Graphics g, int x, int y) {    
		g.setColor(Color.black);

		double width = ArenaPanel.getCurrent().getXBlockLength();
		double height = ArenaPanel.getCurrent().getYBlockLength();

		switch (orientation) {
			case Arena.VERTICAL:
        g.fillRect((int)(x + width / 2 - (WALL_WIDTH - 1) / 2),
                   (int)(y - height * length + height / 2),
                   WALL_WIDTH, (int)(height * length + 1));
        
        // int x1 = (int)(x + width / 2 - (WALL_WIDTH - 1) / 2);
        // int y1 = (int)(y - height * length + height / 2);
        // int x2 = x1+12;
        // int y2 = y1+ (int) height;
        // g.drawImage(wall.getImage(),
        //             x1, y1, x2, y2,
        //             0, 0, wall.getIconWidth(), wall.getIconHeight(),
        //             Color.WHITE, null);
        
				break;
        
        
			case Arena.HORIZONTAL:
				g.fillRect((int)(x - width / 2),
				           (int)(y - height / 2 - (WALL_WIDTH - 1) / 2),
				           (int)(width * length + 1), WALL_WIDTH);
				break;
		}
	}

	public String toString() {
		return "Wall { x : " + getX() + ", y: " + getY() +
		       " , length: " + length + ", orientation: " + orientation + " }";
	}

}
