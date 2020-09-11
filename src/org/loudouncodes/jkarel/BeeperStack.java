package org.loudouncodes.jkarel;


import java.awt.*;
import java.awt.geom.Rectangle2D;

public class BeeperStack extends Item {

	public static final int INFINITY = -2;
  

	private static Font beeperFont = null;
	private static String beeperFontName = "monospaced";
	private static int beeperFontSize = 10;
  
  
	private final int RADIUS = 12;

	private int numBeepers = 1;

	public BeeperStack(int x, int y, int beepers) {
		super(x, y);

		if (beepers < 1 && beepers != INFINITY) {
			Arena.logger.warning("Invalid amount of beepers: "
			                   + beepers + "...  Setting to 1...");
			beepers = 1;
		}

		numBeepers = beepers;
	}

	public int getBeepers() {
		return numBeepers;
	}

	private Font getBeeperFont() {
		if (beeperFont == null)
			beeperFont = new Font(beeperFontName, Font.PLAIN, beeperFontSize);

		return beeperFont;
	}
  
	public void render(Graphics g, int x, int y) {
    
		g.setColor(Color.black);
		g.fillOval(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);

		String text;
		if (numBeepers == INFINITY)
			text = "\u221E "; //The infinity sign
		else
			text = "" + numBeepers;

		Font f = getBeeperFont();

		FontMetrics fm = g.getFontMetrics(f);
		Rectangle2D bounds = fm.getStringBounds(text, g);

		g.setColor(Color.white);
		g.drawString(text, (int)(x - bounds.getWidth() / 2),
		             (int)(y + bounds.getHeight() / 2));
	}
}
