// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
/**
 * Robots, maps, and events for building programs in a two-dimensional Karel world.
 * JKarel modernizes the classroom Karel library with named directions and pacing,
 * colored robots and beepers, editable walls, and interfaces for describing maps
 * and observing actions. The same world supports a first robot program, a maze
 * game, or a closer study of object-oriented design.
 *
 * <h2>Begin with an arena and a robot</h2>
 *
 * <p>An {@link org.loudouncodes.jkarel.Arena Arena} is the place where robots act.
 * Its static methods load the shared world and control its animation pace.
 * Constructing a {@link org.loudouncodes.jkarel.Robot Robot} adds it to that arena;
 * its instance methods move that particular robot, inspect its surroundings,
 * and manage the beepers it carries.
 *
 * <pre>{@code
 * import java.awt.Color;
 * import org.loudouncodes.jkarel.*;
 *
 * public class FirstRobot {
 *   public static void main(String[] args) {
 *     Arena.openDefaultMap(); // An empty 10-by-10 world.
 *     Arena.setPace(Pacing.SLOW);
 *     Robot scout = new Robot(1, 1, Direction.EAST, 3);
 *     scout.setColor(Color.BLUE);
 *     scout.putBeeper();
 *     if (scout.frontIsClear()) {
 *       scout.move();
 *     }
 *   }
 * }
 * }</pre>
 *
 * <p>Positions use one-based coordinates: {@code (1, 1)} is the lower-left cell,
 * x increases eastward, and y increases northward. Robot movement advances one
 * cell. Check {@link org.loudouncodes.jkarel.Robot#frontIsClear() frontIsClear()}
 * before moving when a wall may block the path. A blocked move throws an
 * {@link java.lang.IllegalStateException}; the robot remains in place.
 *
 * <h2>Choose the class that owns the behavior</h2>
 *
 * <table>
 * <caption>Where to start in the API</caption>
 * <tr><th scope="col">Task</th><th scope="col">Classes to read</th></tr>
 * <tr><td>Move, turn, sense, and carry beepers</td>
 *     <td>{@link org.loudouncodes.jkarel.Robot Robot},
 *         {@link org.loudouncodes.jkarel.Direction Direction}</td></tr>
 * <tr><td>Load a level and choose its animation pace</td>
 *     <td>{@link org.loudouncodes.jkarel.Arena Arena},
 *         {@link org.loudouncodes.jkarel.Pacing Pacing}</td></tr>
 * <tr><td>Build rooms, remove doors, and place colored beepers</td>
 *     <td>{@link org.loudouncodes.jkarel.ArenaModel ArenaModel},
 *         {@link org.loudouncodes.jkarel.Wall Wall},
 *         {@link org.loudouncodes.jkarel.BeeperStack BeeperStack}</td></tr>
 * <tr><td>Describe a fixed or generated level in Java</td>
 *     <td>{@link org.loudouncodes.jkarel.MapDataSource MapDataSource}</td></tr>
 * <tr><td>Observe actions and maintain a score</td>
 *     <td>{@link org.loudouncodes.jkarel.ArenaListener ArenaListener}</td></tr>
 * <tr><td>Give an object its own appearance</td>
 *     <td>{@link org.loudouncodes.jkarel.Item Item} and its subclasses</td></tr>
 * </table>
 *
 * <h2>Build a richer world</h2>
 *
 * <p>Start with {@code Arena.addNorthWall}, {@code addSouthWall},
 * {@code addEastWall}, and {@code addWestWall} to place walls relative to a cell.
 * For removable doors, retain a {@code Wall} reference and use
 * {@link org.loudouncodes.jkarel.ArenaModel#addWall(Wall) ArenaModel.addWall} and
 * {@link org.loudouncodes.jkarel.ArenaModel#removeWall(Wall) removeWall} through
 * {@link org.loudouncodes.jkarel.Arena#getModel() Arena.getModel()}.
 * Robot colors belong to individual robots; colored beeper stacks can be placed
 * with {@link org.loudouncodes.jkarel.ArenaModel#putBeepers(Location, int, java.awt.Color)
 * ArenaModel.putBeepers}. A color changes appearance; your program supplies its
 * meaning, such as team membership or a collectible's value.
 *
 * <p>Load an XML map with {@link org.loudouncodes.jkarel.Arena#loadMap(String)
 * Arena.loadMap(String)}, or implement {@code MapDataSource} and pass it to
 * {@link org.loudouncodes.jkarel.Arena#loadMap(MapDataSource)
 * Arena.loadMap(MapDataSource)}. The latter validates the description before
 * replacing walls, beepers, and custom items. Existing robots and listeners
 * continue across the level change; plan where the robots should stand in the
 * new level. Read {@code MapDataSource} for the collection and copying contract.
 *
 * <h2>React to actions</h2>
 *
 * <p>Implement {@code ArenaListener}, override the callbacks you need, and
 * register it with {@link org.loudouncodes.jkarel.Arena#addListener(ArenaListener)
 * Arena.addListener}. Callbacks run synchronously on the thread performing the
 * action. Keep them short: update scores or inspect state, and let the main
 * program control movement. A beeper drop produces both a stack-addition event
 * and a robot-drop event; choose the appropriate callback for your counter.
 * See {@code ArenaListener} for callback timing and constructor-related details.
 *
 * <h2>Read the implementation as a teaching example</h2>
 *
 * <p>{@code Arena} is a facade over the model and Swing presentation.
 * {@code ArenaModel} holds the world's state; {@link org.loudouncodes.jkarel.ArenaPanel
 * ArenaPanel} draws it inside an {@link org.loudouncodes.jkarel.ArenaFrame ArenaFrame}.
 * {@code Item} supplies the common position and drawing contract for robots,
 * walls, and beepers. Subclass it to add an object with its own rendering.
 * {@code MapDataSource} separates a level description from loading it, while
 * {@code ArenaListener} separates reactions from the actions being observed.
 * These are useful places to study abstraction, polymorphism, and collaboration
 * between objects after writing your first working game.
 *
 * @see org.loudouncodes.jkarel.demo
 * @see org.loudouncodes.jkarel.xml
 */
package org.loudouncodes.jkarel;
