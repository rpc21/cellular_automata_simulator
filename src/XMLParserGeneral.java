import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class XMLParserGeneral {
    // name of root attribute that notes the type of file expecting to parse
    protected final String TYPE_ATTRIBUTE;
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    protected final DocumentBuilder DOCUMENT_BUILDER;
    protected final File DEFAULT_XML_FILE = new File("tests/GameOfLifeTest.xml");

    public XMLParserGeneral (String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }

    // Get root element of an XML file
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
                throw new XMLException(e);
            }
        }
    }

    // Returns if this is a valid XML file for the specified object type
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
