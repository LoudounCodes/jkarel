package org.loudouncodes.jkarel;

import javax.swing.JFrame;

/**
  * The JFrame that the Arena sits in.
  *
  * In an intro course you will not use this class explicitly...
  * It will be used underneath the hood by the Arena. In more advanced
  * labs, when you are learning about user interfaces, you may use this
  * class by getting it from the Arena, but you are unlikely to ever
  * create one of these yourself.
*/
public class ArenaFrame extends JFrame {

  /** internal reference for the Singleton pattern.*/
	private static ArenaFrame current = null;

  /**
    * The constructor takes the ArenaModel which contains the
    * data representing the arena.
    *
    * This constructor is protected, whch is an intermediate java concept,
    * which means only classes in the org.loudouncodes.jkarel package
    * can construct these.
    *
    * @param model an ArenaModel containing arena data.
    */
	protected ArenaFrame(ArenaModel model) {
		super();

		current = this;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Arena.FRAME_WIDTH, Arena.FRAME_HEIGHT);
		setLocation(250, 250);
		setContentPane(new ArenaPanel(model));
		setVisible(true);
	}

  /**
    * called when a user clicks the 'close' button in the window titlebar.
    */
	protected void close() {
		ArenaPanel.getCurrent().close();
		current = null;
		dispose();
	}

  /**
    * ArenaFrame is a <i>singleton</i>, which is a design pattern
    * not typically taught in a high school curriculum.
    *
    * Basic idea here is that there should only ever be one ArenaFrame
    * in existence for any application.  This is also why the constructor
    * is protected; that way, it is only created inside of this library.
    * by having a current variable that holds a reference to <i>this</i>
    * when constructed, we can return that instance here.  That means
    * anyone on the outside of this library has 'one stop shopping' to
    * always find the one and only instance of the ArenaFrame.
    *
    * @return the singleton instance of ArenaFrame.
    */
	public static ArenaFrame getCurrent() {
		return current;
	}
}
