package org.loudouncodes.jkarel;

import javax.swing.JFrame;

/**
 * WorldFrame houses the WorldPanel and deals with its creation using the
 * WorldBackend passed to the constructor.
 * @author Andy Street, alstreet@vt.edu, 2007
 */
public class WorldFrame extends JFrame {

	private static WorldFrame current = null;

	/**
	 * Constructs a WorldFrame associated with the specified WorldBackend.
	 * A WorldPanel is created with the WorldBackend and set as the content
	 * pane.
	 */
	public WorldFrame(WorldBackend wb) {
		super();

		current = this;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Arena.FRAME_WIDTH, Arena.FRAME_HEIGHT);
		setLocation(250, 250);
		setContentPane(new WorldPanel(wb));
		setVisible(true);
	}

	/**
	 * Closes the WorldBackend and WorldPanel, then disposes the Frame.
	 */
	void close() {
		WorldBackend.getCurrent().close();
		WorldPanel.getCurrent().close();
		current = null;
		dispose();
	}

	/**
	 * Returns the current (most recently created) WorldFrame.
	 */
	public static WorldFrame getCurrent() {
		return current;
	}
}
