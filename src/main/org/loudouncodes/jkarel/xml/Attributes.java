package org.loudouncodes.jkarel.xml;

import org.xml.sax.ext.Attributes2Impl;

/**
 * @author Andy Street, alstreet@vt.edu, 2007

 * A simple extension of the sax Attributes2Impl that shortens the name and attributes to be added and
 * removed in a HashMap-like manner.
 */

public class Attributes extends Attributes2Impl {
  public Attributes()
  {
    super();
  }
  public Attributes(org.xml.sax.Attributes a)
  {
    super(a);
  }
  
  /**
         * Adds an attribute pair.
         * @param key the attribute key
         * @param value the attribute value
         */
        public void put(String key, String value)
  {
    addAttribute("", key, key, "CDATA", value);
  }

        /**
         * A synonym for getValue(String).
         * @param key the key for the value you wish to retrieve
         */
  public String get(String key)
  {
    return getValue(key);
  }
}
