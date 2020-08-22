package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;
import java.util.List;

/**
 * A Robot is a basic movable object in the world. Students interct primarily
 * with Robot objects in order to solve the problems in the labs. Robots tend
 * to update their status whenever asked and then request a arena update with
 * Arena.step(). This class is intended to be subclassed to add simple
 * behaviors.
 * @author Andy Street, alstreet@vt.edu, 2007
 */

public class Robot extends Item {

	private int beepers;
	private Direction direction;

	/**
	 * Contructs a Robot at the default location of (1,1) facing east with
	 * no beepers.
	 */
	public Robot() {
		this(1, 1, Direction.EAST, 0);
	}

	/**Contructs a Robot at the specified location, direction, and number
	 * of beepers.
	 * @param x the x location of the new Robot's location
	 * @param y the y location of the new Robot's location
	 * @param dir the number representing the direction of the robot, using
	 * the constants from Arena
	 * @param beepers the number of beepers the new Robot will start with
	 */
	public Robot(int x, int y, Direction dir, int beepers) {
		super(x, y);
		init(x, y, dir, beepers, false);
	}

	/**Contructs a Robot at the specified location, direction, and number of beepers
	 * and adds it to the WorldBackend.
	 * @param x the x location of the new Robot's location
	 * @param y the y location of the new Robot's location
	 * @param dir the number representing the direction of the robot, using
	 * the constants from Arena
	 * @param beepers the number of beepers the new Robot will start with
	 * @param internal a boolean specifiying whether the robot construction
	 * to cause the arena to update or not.(Internal indicates no display
	 * update
	 */

	public Robot(int x, int y, Direction dir, int beepers, boolean internal) {
		super(x, y);
		init(x, y, dir, beepers, internal);
	}
	/**Common code called by both constructors.
	 * @param x the x location of the new Robot's location
	 * @param y the y location of the new Robot's location
	 * @param dir the number representing the direction of the robot, using
	 * the constants from Arena
	 * @param beepers the number of beepers the new Robot will start with
	 * @param internal a boolean specifiying whether the robot construction
	 * to cause the arena to update or not.(Internal indicates no arena
	 * update
	 */
	private void init(int x, int y, Direction dir, int beepers, boolean internal) {

		if (WorldBackend.getCurrent() == null) {
			Arena.openDefaultWorld();
		}

		this.x = x;
		this.y = y;

		if (beepers < 0 && beepers != Arena.INFINITY) {
			Arena.logger.warning("Invalid amount of beepers: "
			                   + beepers + "...  Setting to 0...");
			beepers = 0;
		}

		direction = dir;
		this.beepers = beepers;
		if (internal)
			WorldBackend.getCurrent().addRobotInternal(this);
		else
			WorldBackend.getCurrent().addRobot(this);

	}

	/**
	 * Returns an integer representing the direction of the robot.
	 * Compare to the constants in Arena.
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Returns the number of beepers the Robot has.
	 */
	public int getBeepers() {
		return beepers;
	}

	/**
	 * Moves the Robot forward one square in the direction it is facing.
	 * If the Robot tries to move through a wall, an the simulation will
	 * exit with an error. Calls Arena.step() to update the screen.
	 */
	public synchronized void move() {
		if (Arena.isDead())
			return;

		boolean clear = frontIsClear();

		if (!clear) {
			Location c = getWallLocation(direction);

			switch (direction) {
				case NORTH:
				case SOUTH:
					Arena.die("Tried to walk through a horizontal wall at (" + c.x + ", " + c.y + ")!");
					break;
				case EAST:
				case WEST:
				default:
					Arena.die("Tried to walk through a vertical wall at (" + c.x + ", " + c.y + ")!");
			}

			return;
		}

		switch (direction) {
			case NORTH:
				y++;
				break;
			case EAST:
				x++;
				break;
			case SOUTH:
				y--;
				break;
			case WEST:
				x--;
				break;
		}

		Arena.step();
	}

	/**
	 * Turns the robot counterclockwise 90 degrees.
	 * Calls Arena.step() to update the screen.
	 */
	public void turnLeft() {
		if (Arena.isDead())
			return;

		direction = direction.left();

		Arena.step();
	}

	/**
	 * Turns the robot clockwise 90 degrees.
	 * This method is private as students are meant to write their own out
	 * of the other methods later. Calls Arena.step() to update the screen.
	 */
	private void turnRight() {
		if (Arena.isDead())
			return;

		direction = direction.right();

		Arena.step();
	}

	/**
	 * Places a beeper at the current location in the world, or adds it to
	 * the a currently existing pile.
	 * Calls Arena.step() to update the screen.
	 */
	public void putBeeper() {
		if (Arena.isDead())
			return;

		if (beepers < 1 && beepers != Arena.INFINITY) {
			Arena.die("Trying to put non-existent beepers!");
			return;
		}

		if (beepers != Arena.INFINITY)
			beepers--;

		WorldBackend.getCurrent().putBeepers(x, y, 1);

		Arena.step();
	}

	/**
	 * Picks up a beeper from the current location in the world, removing
	 * it from the currently existing pile.
	 * Calls Arena.step() to update the screen.
	 */
	public void pickBeeper() {
		if (Arena.isDead())
			return;

		if (!WorldBackend.getCurrent().checkBeepers(x, y)) {
			Arena.die("Trying to pick non-existent beepers!");
			return;
		}

		if (beepers != Arena.INFINITY)
			beepers++;

		WorldBackend.getCurrent().putBeepers(x, y, -1);

		Arena.step();
	}

	/**
	 * Picks up a beeper from the current location in the world, removing
	 * it from the currently existing pile.
	 * Calls Arena.step() to update the screen.
	 */
	public boolean hasBeepers() {
		return beepers > 0 || beepers == Arena.INFINITY;
	}

	/**
	 * Checks to see if a wall would prevent the robot from moving forward.
	 */
	public boolean frontIsClear() {
		return isClear(direction);
	}

	/**
	 * Checks to see if a wall would prevent the robot from turning right
	 * and then moving forward.
	 */
	public boolean rightIsClear() {
		return isClear(direction.right());
	}

	/**
	 * Checks to see if a wall would prevent the robot from turning left and
	 * then moving forward.
	 */
	public boolean leftIsClear() {
		return isClear(direction.left());
	}

	/**
	 * Checks to see if a wall would prevent the robot from turning around
	 * and then moving forward.
	 */
	public boolean backIsClear() {
		return isClear(direction.right().right());
	}

	/**
	 * Returns whether or not there are any beepers on the same square as
	 * this Robot.
	 */
	public boolean nextToABeeper() {
		return WorldBackend.getCurrent().checkBeepers(x, y);
	}

	/**
	 * Returns whether or not there is another Robot on the same square as
	 * this Robot.
	 */
	public boolean nextToARobot() {
		return WorldBackend.getCurrent().isNextToARobot(this, x, y);
	}

	/**
	 * Returns whether or not this Robot is facing north.
	 */
	public boolean facingNorth() {
		return direction == Direction.NORTH;
	}
	/**
	 * Returns whether or not this Robot is facing south.
	 */
	public boolean facingSouth() {
		return direction == Direction.SOUTH;
	}
	/**
	 * Returns whether or not this Robot is facing east.
	 */
	public boolean facingEast() {
		return direction == Direction.EAST;
	}
	/**
	 * Returns whether or not this Robot is facing west.
	 */
	public boolean facingWest() {
		return direction == Direction.WEST;
	}

	/**
	 * Checks to see if a wall would prevent the robot from moving in the
	 * specified direction.
	 * @param direction the direction to check. Uses values from the
	 * constants in the Arena class.
	 */
	private boolean isClear(Direction dir) {
		Location c = getWallLocation(direction);

		switch (dir) {
			case NORTH:
			case SOUTH:
				return !WorldBackend.getCurrent()
				       .checkWall(c.x, c.y, Arena.HORIZONTAL);
			case EAST:
			case WEST:
			default:
				return !WorldBackend.getCurrent()
				       .checkWall(c.x, c.y, Arena.VERTICAL);
		}
	}

	/**
	 * Removes the robot from the world, preventing it from displaying.
	 */
	public void explode() {
		WorldBackend.getCurrent().removeRobot(this);
	}

	/**
	 * Returns the location of where the wall directly in front of the
	 * robot would be.
	 */
	private Location getWallLocation(Direction dir) {
		int xc = x, yc = y;

		switch (dir) {
			case NORTH: //Check in front, not behind
			case EAST: //Check in front, not behind
				break;
			case SOUTH: //Checking behind current location
				yc--;
				break;
			case WEST: //Checking behind current location
				xc--;
				break;
		}

		return new Location(xc, yc);
	}

	/**
	 * Renders the graphical representation of the Robot to the graphics
	 * object at the specified location.
	 * @param g the graphics context to render onto
	 * @param c the locations of the position to render to (in pixels)
	 */
	public void render(Graphics g, Location c) {
		ImageIcon i = Arena.getKarelImage(direction);
		g.drawImage(i.getImage(), c.x - i.getIconWidth() / 2,
		            c.y - i.getIconHeight() / 2, null);
	}

	/**
	 * Returns whether or not the specified Robot is on the same square as
	 * this Robot.
	 * @param other the Robot to check against
	 */
	public boolean nextToRobot(Robot other) {
		return (x == other.getX() && y == other.getY());
	}
}