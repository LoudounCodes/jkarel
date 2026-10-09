// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
/**
 * A runnable lettering example that builds a larger behavior from robot actions.
 *
 * <p>{@link org.loudouncodes.jkarel.demo.Hello Hello} loads the bundled
 * {@code hello.map}, adjusts the window, sets the animation pace, and asks an
 * {@link org.loudouncodes.jkarel.demo.AlphaBot AlphaBot} to write
 * {@code Hello World!} in beepers. It is the main class of the standard demo jar:
 *
 * <pre>{@code
 * java -jar out/jkarel-1.0.0.jar
 * }</pre>
 *
 * <p>The demo opens a graphical window. The supplied map provides room for the
 * message. When adapting it, allow space for each character and use characters
 * in the robot's 128-entry basic character table.
 *
 * <h2>From commands to a reusable behavior</h2>
 *
 * <p>{@code AlphaBot} extends {@link org.loudouncodes.jkarel.Robot Robot} and
 * adds {@link org.loudouncodes.jkarel.demo.AlphaBot#say(String) say(String)}.
 * Its implementation translates character bitmaps into rows of movement and
 * beeper placement. Read it to see how small helper methods organize a longer
 * task and how a subclass introduces a vocabulary appropriate to that task.
 *
 * <p>A useful first modification is to change the message, the robot's color,
 * or the pacing. A larger extension could render a scoreboard or label an arena.
 * The core package supplies the world and movement rules; this package shows
 * one application built on those rules.
 *
 * @see org.loudouncodes.jkarel
 */
package org.loudouncodes.jkarel.demo;
