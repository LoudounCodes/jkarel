package org.loudouncodes.jkarel.xml;

import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.Locator;
import org.xml.sax.ext.Attributes2Impl;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import org.xml.sax.helpers.DefaultHandler;

import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;



/**
 * The XMLParser implements ContentHandler to use the SAXParser contained in Apache's J-Xerces2 implementation.  The parser uses
 * callbacks to construct an XMLElement representing the base of the
 * content XML tree.  See http://netchat.tjhsst.edu/trac/netchat/wiki/NCP for more information.
 * @author Andy Street, alstreet@vt.edu, 2007
 */

public class XMLParser extends DefaultHandler
{
	private XMLReader parser;
	private ArrayList<Element> elements = null;
	private Element rootElement = null;
	
  public static Logger logger = Logger.getLogger("XML Logger");
  
  static {
    logger.setLevel(Level.SEVERE);
  }
  
	/**
	 * The default constructor.
	 */
	public XMLParser()
	{
		logger.info("Initializing base parser objects...");
		
    try {
		  logger.info("Creating parser...");
		  parser = XMLReaderFactory.createXMLReader();
		
  		logger.info("Setting Handler...");
	  	parser.setContentHandler(this);
    } catch (SAXException e) {
        e.printStackTrace();
    }
	}
	
	/**
	 * Initiates message parsing, reading the XML document from the supplied Reader.
	 * @param r the reader that buffers the message
	 */
	public Element parse(Reader r)
	{
		return parse(new InputSource(r));
	}
	
	/**
	 * Initiates message parsing, reading the XML document from the supplied InputStream.
	 * @param is the InputStream that buffers the message
	 */
	public Element parse(InputStream is)
	{
		return parse(new InputSource(is));
	}

	/**
	 * Initiates message parsing, reading the XML document from the supplied InputSource.
	 * @param is the InputSource that buffers the message
	 */
	public Element parse(InputSource is)
	{
		try{
			parser.parse(is);
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
		
		return rootElement;
	}
	
	public void startDocument()
	{
		logger.fine("Initializing parser objects...");
		elements = new ArrayList<Element>();
		
		logger.fine("Incoming XML message, starting parser...");
	}
	public void endDocument()
	{
		
	}
	public void processingInstruction(String target, String data)
	{
	}
	public void startPrefixMapping(String prefix, String uri)
	{
	}
	public void endPrefixMapping(String prefix)
	{
	}
	public void startElement(String namespaceURI, String localName, String rawName, Attributes as)
	{	

			logger.fine("Start of element received:");
			logger.fine("  Namespace URI: " + namespaceURI);
			logger.fine("  Local Name: " + localName);
			logger.fine("  Raw Name: " + rawName);
			logger.fine("  Attributes:");
			for(int i = 0; i < as.getLength(); i++)
			{
				logger.fine("    Namespace URI: " + as.getURI(i));
				logger.fine("    Type: " + as.getType(i));
				logger.fine("    Qualified (prefixed) Name: " + as.getQName(i));
				logger.fine("    Local Name: " + as.getLocalName(i));
				logger.fine("    Value: " + as.getValue(i));
			}
		
		
		org.loudouncodes.jkarel.xml.Attributes a = new org.loudouncodes.jkarel.xml.Attributes();
		a.setAttributes(as);

		Element e = new Element(localName, a);

		if(rootElement == null)
			rootElement = e;
		else
			elements.get(elements.size() - 1).addElement(e);
		
		elements.add(e);
		
		logger.fine("End of start of element.");
	}
	public void endElement(String namespaceURI, String localName, String rawName)
	{

			logger.fine("End of element received:");
			logger.fine("  Namespace URI: " + namespaceURI);
			logger.fine("  Local Name: " + localName);
			logger.fine("  Raw Name: " + rawName);
			logger.fine("End of end of element.");
	
		
		int elemLen = elements.size();
		
		if(elemLen > 0)
			elements.remove(elemLen - 1);
	}
	public void characters(char[] ch, int s, int len)
	{
		String content = new String(ch, s, len);
		
		if(content.length() != 0)
		{
	
				logger.fine("Characters received:");
				logger.fine("  Characters: " + content);
				logger.fine("End of characters.");
				logger.fine("Adding content...");
			
			elements.get(elements.size() - 1).addText(content);
		}
	}
	
	public void error(SAXParseException e) //We use the default error and fatal error handlers
	{
	}
	public void fatalError(SAXParseException e)
	{
	}
	
	public void setDocumentLocator(Locator loc)
	{
		
	}
	public void ignorableWhitespace(char[] ch, int s, int len)
	{

			logger.fine("Ignorable whitespace:");
			logger.fine("  Characters: " + new String(ch, s, len));
			logger.fine("End of ignorable whitespace.");

	}
	public void skippedEntity(String entName)
	{
		logger.info("Skipped entity: " + entName);
	}
}

