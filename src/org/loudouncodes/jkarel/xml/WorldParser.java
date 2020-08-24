package org.loudouncodes.jkarel.xml;

import org.loudouncodes.jkarel.*;

import java.lang.reflect.*;

/**
 * @author Andy Street, alstreet@vt.edu, 2007
 * This class is responsible for taking the XML DOM tree created by the parser and using reflections
 * to populate a WorldBackend representing the map.
 */

public class WorldParser {

	private static String propPrepend = "loadProperties_";
	private static String objPrepend = "addObject_";

	/**
         * Takes in an Element representing the root of the XML DOM tree for a map, then uses
         * reflections to populate a WorldBackend.
         * @param root the root of the XML DOM tree for the map
         */
        
        public static void initiateMap(Element root)
	{
		WorldBackend wb = WorldBackend.getCurrent();

		Element properties = root.get("properties");
		Element objects = root.get("objects");

		if(properties != null)
			for(Element e : properties.getElements())
			{
				try {
					Method m = Class.forName("org.loudouncodes.jkarel.WorldBackend").getMethod(propPrepend + e.getName() , new Class[] { Class.forName("org.loudouncodes.jkarel.xml.Attributes") });
					m.invoke(wb, new Object[] { e.getAttributes() });
				} catch (SecurityException e1) {
					e1.printStackTrace();
					throw e1;
				} catch (NoSuchMethodException e1) {
					XMLParser.logger.severe("Could not find method " + propPrepend + e.getName() + " for property " + e.getName() + "!  Ignoring...");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
					System.exit(2);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
					System.exit(3);
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
					System.exit(4);
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
					System.exit(5);
				} 
			}

		if(objects != null)
			for(Element e : objects.getElements())
			{
				try {
					Method m = Class.forName("org.loudouncodes.jkarel.WorldBackend").getMethod(objPrepend + e.getName() , new Class[] { Class.forName("org.loudouncodes.jkarel.xml.Attributes") });
					m.invoke(wb, new Object[] { e.getAttributes() });
				} catch (SecurityException e1) {
					e1.printStackTrace();
					throw e1;
				} catch (NoSuchMethodException e1) {
					XMLParser.logger.severe("Could not find method " + objPrepend + e.getName() + " for object " + e.getName() + "!  Ignoring...");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
					System.exit(2);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
					System.exit(3);
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
					System.exit(4);
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
					System.exit(5);
				} 
			}
	}

}
