// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
/**
 * XML map parsing for the classroom {@code .map} file format.
 *
 * <p>Student programs normally enter through
 * {@link org.loudouncodes.jkarel.Arena#loadMap(String) Arena.loadMap(String)}.
 * Map names can identify classpath resources or local files. This package
 * handles reading the document and translating its contents into the arena's
 * model. To describe a level directly in Java, implement
 * {@link org.loudouncodes.jkarel.MapDataSource MapDataSource} and use the
 * corresponding {@code Arena.loadMap} overload.
 *
 * <h2>A small map</h2>
 *
 * <p>A {@code world} contains dimensions under {@code properties} and placed
 * objects under {@code objects}. For example, save this as {@code room.map}
 * and load it with {@code Arena.loadMap("room.map")}:
 *
 * <pre>{@code
 * <world>
 *   <properties>
 *     <defaultSize width="6" height="4"/>
 *   </properties>
 *   <objects>
 *     <beeper x="2" y="1" num="3"/>
 *     <wall x="3" y="1" length="2" style="vertical"/>
 *   </objects>
 * </world>
 * }</pre>
 *
 * <p>The beeper entry places three beepers at cell {@code (2, 1)};
 * {@code num="infinite"} supplies an inexhaustible stack. Walls run along cell
 * edges: a vertical segment at {@code (x, y)} is east of that cell; a horizontal
 * segment is north of it. Length extends a vertical wall northward or a
 * horizontal wall eastward, in unit segments.
 *
 * <h2>How a document becomes a world</h2>
 *
 * <p>{@link org.loudouncodes.jkarel.xml.XMLParser XMLParser} receives SAX parsing
 * callbacks and builds a tree of {@link org.loudouncodes.jkarel.xml.Element
 * Element} objects with {@link org.loudouncodes.jkarel.xml.Attributes Attributes}.
 * {@link org.loudouncodes.jkarel.xml.MapParser MapParser} then uses the element
 * names to locate model methods through reflection. For example,
 * {@code defaultSize} selects {@code loadProperties_defaultSize}, and
 * {@code beeper} selects {@code addObject_beeper} on
 * {@link org.loudouncodes.jkarel.ArenaModel ArenaModel}.
 *
 * <p>Read these classes when studying parsing callbacks, tree structures, or
 * reflection. The map loader and its error handling retain the classroom
 * format's historical behavior; the Java map-description interface offers an
 * alternative with explicit validation before replacing a level.
 *
 * @see org.loudouncodes.jkarel
 */
package org.loudouncodes.jkarel.xml;
