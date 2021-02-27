package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;


/**
 * A Wall is a barrier that is rendered on the WorldPanel.  A Wall appears
 * between two lanes on the map and causes the program to end when a Robot walks
 * into it.
 *
 * For vertical walls, the x location corresponds to the vertical lane in
 * front of which the Wall will render, and the y location corresponds to the
 * horizontal lane on which the first segment of the Wall will center on.
 *
 * For horizontal walls, the x location corresponds to the vertical lane on
 * which the first segment of the wall will center on, and the y location
 * corresponds to the horizontal lane in front of which the Wall will render.
 *
 * If the Wall is of length greater than 1, the Wall will extend outwards
 * in the direction of positive x-infinity or y-infinity, depending on its
 * orientation.
 */

public class Wall extends Item {

  // not used yet, but needed instead of magic ints defined in arena
  public enum Orientation {
    HORIZONTAL, VERTICAL;
  }
  
  private static ImageIcon wall;
  
	private final int WALL_WIDTH = 7;

	private int style;
	private int length;

  {
    wall = new ImageIcon(ArenaPanel.class.getResource("/icons/arena_wall.png"));
  }
  
	public Wall(int x, int y) {
		this(x, y, Arena.VERTICAL);
	}

	public Wall(int x, int y, int style) {
		this(x, y, 1, style);
	}

	public Wall(int x, int y, int length, int style) {
		super(x, y);

		this.length = length;

		if (style == Arena.VERTICAL || style == Arena.HORIZONTAL)
			this.style = style;
		else {
			Arena.logger.warning("Invalid wall style: " + style + "...  Using VERTICAL.");
			this.style = Arena.VERTICAL;
		}
	}

	public int getLength() {
		return length;
	}

	public int getStyle() {
		return style;
	}

	public void render(Graphics g, int x, int y) {    
		g.setColor(Color.black);

		double width = ArenaPanel.getCurrent().getXBlockLength();
		double height = ArenaPanel.getCurrent().getYBlockLength();

		switch (style) {
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
		       " , length: " + length + ", style: " + style + " }";
	}

}
