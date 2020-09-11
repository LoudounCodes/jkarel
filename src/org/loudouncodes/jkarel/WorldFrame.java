package org.loudouncodes.jkarel;

import javax.swing.JFrame;

public class WorldFrame extends JFrame {

	private static WorldFrame current = null;

	public WorldFrame(WorldBackend wb) {
		super();

		current = this;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Arena.FRAME_WIDTH, Arena.FRAME_HEIGHT);
		setLocation(250, 250);
		setContentPane(new WorldPanel(wb));
		setVisible(true);
	}

	void close() {
		WorldBackend.getCurrent().close();
		WorldPanel.getCurrent().close();
		current = null;
		dispose();
	}

	public static WorldFrame getCurrent() {
		return current;
	}
}
