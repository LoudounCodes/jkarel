package org.loudouncodes.jkarel;

import java.awt.*;

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
 *
 * @author Andy Street, alstreet@vt.edu, 2007
 */

public class Wall extends Item {

  // not used yet, but needed instead of magic ints defined in arena
  public enum Orientation {
    HORIZONTAL, VERTICAL;
  }
  
  
	private final int WALL_WIDTH = 7;

	private int style;
	private int length;

	/**
	 * Constructs a vertical Wall of length 1
	 * @param x the x location of the Wall
	 * @param y the y location of the Wall
	 */
	public Wall(int x, int y) {
		this(x, y, Arena.VERTICAL);
	}

	/**
	 * Constructs a Wall of length 1 with the specified style
	 * @param x the x location of the Wall
	 * @param y the y location of the Wall
	 * @param style the orientation of the wall (Arena.VERTICAL or
	 * Arena.HORIZONTAL)
	 */
	public Wall(int x, int y, int style) {
		this(x, y, 1, style);
	}

	/**
	 * Constructs a Wall of the specified length with the specified style
	 * @param x the x location of the Wall
	 * @param y the y location of the Wall
	 * @param length the length of the Wall
	 * @param style the orientation of the wall (Arena.VERTICAL or
	 * Arena.HORIZONTAL)
	 */
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

	/**
	 * Returns the length of the Wall
	 * @return the length of the Wall
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns the style of the Wall
	 * @return the orientation of the wall (Arena.VERTICAL or
	 * Arena.HORIZONTAL)
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * Renders the Wall at the Location specified using the passed
	 * Graphics object.
	 * @param g the Graphics with which to render the Wall
	 */
	public void render(Graphics g, int x, int y) {    
		g.setColor(Color.black);

		double width = WorldPanel.getCurrent().getXBlockLength();
		double height = WorldPanel.getCurrent().getYBlockLength();

		switch (style) {
			case Arena.VERTICAL:
				g.fillRect((int)(x + width / 2 - (WALL_WIDTH - 1) / 2),
				           (int)(y - height * length + height / 2),
				           WALL_WIDTH, (int)(height * length + 1));
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
