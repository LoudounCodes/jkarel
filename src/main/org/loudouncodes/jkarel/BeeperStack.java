package org.loudouncodes.jkarel;


import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
  * A stack of beepers displayed within the arena. Only an advanced
  * user of this library will need to concern themselves with this
  * class.
  *
  * This is kind-of an unfortunate implementation of this concept
  * for future expandability, but it makes sense as all beepers are
  * identical... We don't need instances of a Beeper class, we only
  * need to keep track of the count at any given location.
  * That may change in the future if we decide to allow other types
  * of Items...
  */
public class BeeperStack extends Item {

	public static final int INFINITY = -2;
  

	private static Font beeperFont = null;
	private static String beeperFontName = "monospaced";
	private static int beeperFontSize = 10;
  
  
	private final int RADIUS = 12;

	private int numBeepers = 1;
  
  private Color myColor = Color.YELLOW;

	public BeeperStack(int x, int y, int beepers) {
		super(x, y);

		if (beepers < 1 && beepers != INFINITY) {
			Arena.logger.warning("Invalid amount of beepers: "
			                   + beepers + "...  Setting to 1...");
			beepers = 1;
		}

		numBeepers = beepers;
	}

    /**
      * @return the number of beepers represented by this stack.
      */
	public int getBeepers() {
		return numBeepers;
	}

	private Font getBeeperFont() {
		if (beeperFont == null)
			beeperFont = new Font(beeperFontName, Font.PLAIN, beeperFontSize);

		return beeperFont;
	}
  
  public void setColor(Color c) {
    myColor = c;
  }
  
  public Color getColor() {
    return myColor;
  }
  
	public void render(Graphics g, int x, int y) {
    
		g.setColor(myColor);
		g.fillOval(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);

		String text;
		if (numBeepers == INFINITY)
			text = "\u221E "; //The infinity sign
		else
			text = "" + numBeepers;

		Font f = getBeeperFont();

		FontMetrics fm = g.getFontMetrics(f);
		Rectangle2D bounds = fm.getStringBounds(text, g);

		g.setColor(Color.red);
		g.drawString(text, (int)(x - bounds.getWidth() / 2),
		             (int)(y + bounds.getHeight() / 2));
	}
}
