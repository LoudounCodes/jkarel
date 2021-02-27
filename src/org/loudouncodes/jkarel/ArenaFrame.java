package org.loudouncodes.jkarel;

import javax.swing.JFrame;

public class ArenaFrame extends JFrame {

	private static ArenaFrame current = null;

	public ArenaFrame(ArenaModel wb) {
		super();

		current = this;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Arena.FRAME_WIDTH, Arena.FRAME_HEIGHT);
		setLocation(250, 250);
		setContentPane(new ArenaPanel(wb));
		setVisible(true);
	}

	void close() {
		ArenaModel.getCurrent().close();
		ArenaPanel.getCurrent().close();
		current = null;
		dispose();
	}

	public static ArenaFrame getCurrent() {
		return current;
	}
}
