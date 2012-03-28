/* CS145
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire directory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


class MyParser {
    
    static final String columnSeparator = "<>";
    static DocumentBuilder builder;
    
    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String formatDollar(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    /* Returns the time (in YYYY-MM-DD HH:MM:SS format) denoted by a
     * time string like Dec-31-01 23:59:59. */
    static String formatTime(String time) {
    	DateFormat outputDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    	DateFormat inputDf = new SimpleDateFormat("MMM-dd-yy HH:mm:ss", Locale.US);
    	String result = "";
    	try { 
    		result = outputDf.format(inputDf.parse(time)); 
    	} catch (ParseException e) {
    		System.out.println("This method should work for all date/" +
                           "time strings you find in our data.");
    		System.exit(20);
    	}
    	return result;
    }
    
    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);
        
        /* Fill in code here (you will probably need to write auxiliary
            methods). */
        
        Element itemElems = doc.getDocumentElement();
        
        generateUserLoadFile(itemElems);
        generateItemLoadFile(itemElems);
        generateCategoryLoadFile(itemElems);
        generateBidLoadFile(itemElems);
        
        /**************************************************************/
        
    }
    
    /**
     * Generate AuctionUser Bulk-Loading file.
     * 
     * @param root
     */
    static void generateUserLoadFile(Element root) {    	
    	try {
    		FileWriter fileWriter = new FileWriter("user.dat", true);
			PrintWriter output = new PrintWriter(fileWriter);
			
			Element[] itemsElems = getElementsByTagNameNR(root, "Item");
	    	for (Element itemElem : itemsElems) {
	    		Element sellerElem = getElementByTagNameNR(itemElem, "Seller");
	    		output.print(appendSeparator(sellerElem.getAttribute("UserID")));
	    		output.print(appendSeparator(sellerElem.getAttribute("Rating")));
	    		output.print(appendSeparator(getElementTextByTagNameNR(itemElem, "Location")));
	    		output.println(getElementTextByTagNameNR(itemElem, "Country"));
	    		
	    		Element bidsElem = getElementByTagNameNR(itemElem, "Bids");
	    		Element[] bidElems = getElementsByTagNameNR(bidsElem, "Bid");
	    		for (Element bidElem : bidElems) {
	    			Element bidderElem = getElementByTagNameNR(bidElem, "Bidder");
		    		output.print(appendSeparator(bidderElem.getAttribute("UserID")));
		    		output.print(appendSeparator(bidderElem.getAttribute("Rating")));
		    		output.print(appendSeparator(getElementTextByTagNameNR(bidderElem, "Location")));
		    		output.println(getElementTextByTagNameNR(bidderElem, "Country"));
	    		}
	    		
	    	}
	    	
	    	output.close();
	    	fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
     * Generate "Item" Bulk-Loading file.
     * 
     * @param root
     */
    static void generateItemLoadFile(Element root) {
    	try {
    		FileWriter fileWriter = new FileWriter("items.dat", true);
			PrintWriter output = new PrintWriter(fileWriter);
			
			Element[] itemsElems = getElementsByTagNameNR(root, "Item");
			for (Element itemElem : itemsElems) {
	    		output.print(appendSeparator(itemElem.getAttribute("ItemID")));
	    		output.print(appendSeparator(getElementTextByTagNameNR(itemElem, "Name")));
	    		output.print(appendSeparator(getStandInNullWhenEmpty(
	    				getElementTextByTagNameNR(itemElem, "Buy_Price"))));
	    		output.print(appendSeparator(formatTime(getElementTextByTagNameNR(itemElem, "Started"))));
	    		output.print(appendSeparator(formatTime(getElementTextByTagNameNR(itemElem, "Ends"))));
	    		
	    		Element sellerElem = getElementByTagNameNR(itemElem, "Seller");
	    		output.print(appendSeparator(sellerElem.getAttribute("UserID")));
	    		output.println(getElementTextByTagNameNR(itemElem, "Description"));
	    	}
	    	
	    	output.close();
	    	fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Generate Category Bulk-Loading file.
     * 
     * @param rootElem
     */
    static void generateCategoryLoadFile(Element rootElem) {
    	try {
    		FileWriter fileWriter = new FileWriter("categories.dat", true);
			PrintWriter output = new PrintWriter(fileWriter);
			
			Element[] itemElems = getElementsByTagNameNR(rootElem, "Item");
	    	for (Element itemElem : itemElems) {
	    		Element[] categoryElems = getElementsByTagNameNR(itemElem, "Category");
	    		for (Element categoryElem : categoryElems) {
	    			output.print(appendSeparator(itemElem.getAttribute("ItemID")));
	    			output.println(getElementText(categoryElem));
	    		}
	    	}
	    	
	    	output.close();
	    	fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Generate Bid Bulk-Loading file.
     * 
     * @param rootElem
     */
    static void generateBidLoadFile(Element rootElem) {
    	try {
    		FileWriter fileWriter = new FileWriter("bid.dat", true);
			PrintWriter output = new PrintWriter(fileWriter);
			
			Element[] itemElems = getElementsByTagNameNR(rootElem, "Item");
			for (Element itemElem : itemElems) {
	    		Element bidsElem = getElementByTagNameNR(itemElem, "Bids");
	    		Element[] bidElems = getElementsByTagNameNR(bidsElem, "Bid");
	    		for (Element bidElem : bidElems) {
	    			output.print(appendSeparator(itemElem.getAttribute("ItemID")));
	    			
	    			Element bidderElem = getElementByTagNameNR(bidElem, "Bidder");
	    			output.print(appendSeparator(bidderElem.getAttribute("UserID")));
	    			
	    			output.print(appendSeparator(formatTime(getElementTextByTagNameNR(bidElem, "Time"))));
	    			output.println(formatDollar(getElementTextByTagNameNR(bidElem, "Amount")));
	    		}
	    	}
	    	
	    	output.close();
	    	fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Replace with 'NULL' string if the component value is empty string.
     * @param componentValue
     * @return
     */
    static String getStandInNullWhenEmpty(String componentValue) {
    	if (componentValue.equals("")) {
    		return "NULL";
    	} else {
    		return componentValue;
    	}
    }
    
    static String appendSeparator(String text) {
    	return text + columnSeparator;
    }
    
    

    
    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }
        
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. */
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }
    }
}
