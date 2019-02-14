import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The XMLParserGeneral class is an abstract class that implements methods used for parsing XML files of any type
 *
 * Based on code by:
 *  @author Rhondu Smithwick
 *  @author Robert C. Duvall
 *
 *
 * @author Dima Fayyad
 */
public abstract class XMLParserGeneral {
    protected final String TYPE_ATTRIBUTE;
    protected final DocumentBuilder DOCUMENT_BUILDER;
    protected final File DEFAULT_XML_FILE = new File("tests/GameOfLifeTest.xml");

    public XMLParserGeneral (String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }

    protected Element getRootElement (File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            var xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }catch (SAXException | IOException e) {
            try {
                System.out.println("File not found. Using default Game of Life File.");
                DOCUMENT_BUILDER.reset();
                var xmlDocument = DOCUMENT_BUILDER.parse(DEFAULT_XML_FILE);
                return xmlDocument.getDocumentElement();
            }catch(SAXException | IOException ee){
                throw new XMLException("Default File not found. Simulation could not be started");
            }
        }
    }

    protected boolean isValidFile (Element root, String type) {
        return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
    }

    // Get value of Element's attribute
    protected String getAttribute (Element e, String attributeName) {
        return e.getAttribute(attributeName);
    }

    // Get value of Element's text
    protected String getTextValue (Element e, String tagName) throws NullPointerException {
        try{
            var nodeList = e.getElementsByTagName(tagName);
            if (nodeList != null && nodeList.getLength() > 0) {
                return nodeList.item(0).getTextContent();
            }
        }catch(NullPointerException exc){
            throw new NullPointerException("No such field" + tagName);
        }
        return "";
    }

    protected DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
