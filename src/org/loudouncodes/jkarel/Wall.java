package org.loudouncodes.jkarel;

import java.awt.*;

public class Wall extends Item {

  // not used yet, but needed instead of magic ints defined in arena
  public enum Orientation {
    HORIZONTAL, VERTICAL;
  }
  
  
	private final int WALL_WIDTH = 7;

	private int style;
	private int length;

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
