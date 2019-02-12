import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLStyler {
    public static final String SHAPE_TYPE_TAG_NAME="shape";
    public static final String NEIGHBORS_TYPE_TAG_NAME="neighborsType";
    public static final String OUTLINE_TAG_NAME="outline";
    public static final String STATE_COLORS_TAG_NAME = "stateColors";
    public static final String defaultShape = "rectangle";
    public static final String defaultNeighbors = "ADJACENT";
    public static final String GRID_LINE_ON = "yes";
    public static final String GRID_LINE_OFF = "no";
    public static final String defaultGridLineSetting = GRID_LINE_ON;

    // name of root attribute that notes the type of file expecting to parse
    private final String TYPE_ATTRIBUTE;
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;

    public XMLStyler (String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }

    public Simulation setSimulationStyle(File dataFile, Simulation simulation){
        var root = getRootElement(dataFile);
        simulation.setColors(readStateColors(root));
        Map<String, String> map = makeStylePropertiesMap(root);
        simulation.setMyStyleProperties(map);
        simulation.updateNeighbors(map);
        return simulation;
    }

    public Map<String, Paint> getColorMap(File dataFile) {
        Map<String, Paint> colorMap = new HashMap<String, Paint>();
        var root = getRootElement(dataFile);
        NodeList colors = root.getElementsByTagName("color");
        System.out.println("Num Colors = "+colors.getLength());
        for (int i = 0; i < colors.getLength(); i++) {
            Element element = (Element) colors.item(i);
            String colorval = element.getTextContent();
            Paint p = Paint.valueOf(colorval);
            colorMap.put(element.getAttributes().getNamedItem("id").getNodeValue(), p);
        }
        return colorMap;
    }
    /**
     * public for visualization to use
     * @param dataFile
     * @return the map containing shape, neighbors, outline
     */
    public Map<String, String> getStylePropertiesMap(File dataFile){
        var root = getRootElement(dataFile);
        return makeStylePropertiesMap(root);
    }

    private HashMap<String, String> makeStylePropertiesMap(Element root){
        HashMap<String, String> styleProperties = new HashMap<String, String>();
        readInShape(root, styleProperties);
        readInNeighborsType(root, styleProperties);
        readInOutline(root, styleProperties);
        styleProperties.put(XMLParser.EDGE_TYPE_TAG_NAME, readInEdges(root));
        return styleProperties;
    }

    private void readInOutline(Element root, HashMap<String, String> styleProperties){
        try {
            String outline = getTextValue(root, OUTLINE_TAG_NAME);
            if (isValidOutlineOption(outline)) {
                styleProperties.put(OUTLINE_TAG_NAME, outline);
            } else {
                styleProperties.put(OUTLINE_TAG_NAME, defaultGridLineSetting);
            }
        }catch(NullPointerException e){
            styleProperties.put(OUTLINE_TAG_NAME, defaultGridLineSetting);
        }
    }

    private boolean isValidOutlineOption(String outline){
        return (outline.equals(GRID_LINE_ON) || outline.equals(GRID_LINE_OFF));
    }

    private void readInNeighborsType(Element root, HashMap<String, String> styleProperties) throws IllegalArgumentException{
        try {
            String neighbors = getTextValue(root, NEIGHBORS_TYPE_TAG_NAME);
            if (isValidNeighbors(neighbors)) {
                styleProperties.put(NEIGHBORS_TYPE_TAG_NAME, neighbors);
            } else {
                styleProperties.put(NEIGHBORS_TYPE_TAG_NAME, defaultNeighbors);
            }
        } catch (IllegalArgumentException e){
            try {//try all lower case
                String upperCaseName = getTextValue(root, NEIGHBORS_TYPE_TAG_NAME).toUpperCase();
                if(isValidNeighbors(upperCaseName)){
                    styleProperties.put(NEIGHBORS_TYPE_TAG_NAME, upperCaseName);
                }
            }
            catch (IllegalArgumentException ee) {
                styleProperties.put(NEIGHBORS_TYPE_TAG_NAME, defaultNeighbors);
            }
        }
    }

    private void readInShape(Element root, HashMap<String, String> styleProperties) throws IllegalArgumentException {
        try {
            String shape = getTextValue(root, SHAPE_TYPE_TAG_NAME);
            styleProperties.put(SHAPE_TYPE_TAG_NAME, shape);
        } catch(IllegalArgumentException e){
            try {//try all lower case
                String lowerCaseName = getTextValue(root, SHAPE_TYPE_TAG_NAME).toLowerCase();
                if(isValidShape(lowerCaseName)){
                    styleProperties.put(SHAPE_TYPE_TAG_NAME, lowerCaseName);
                }
            }
            catch (IllegalArgumentException ee) {
                styleProperties.put(SHAPE_TYPE_TAG_NAME, defaultShape);
                System.out.println("Not a valid shape specified. Default rectangle used.");
            }
        }
    }

    public boolean isValidNeighbors(String possibleNeighbors){
        for(NeighborsDefinitions neighborType: NeighborsDefinitions.values()){
            if(possibleNeighbors.toUpperCase().equals(neighborType.toString().toUpperCase())){
                return possibleNeighbors.equals(neighborType.toString());
            }
        }
        return false;
    }

    public boolean isValidShape(String possibleShape){
        for(simulationShapes s: simulationShapes.values()){
            if(possibleShape.equals(s.toString())){
                return possibleShape.equals(s.toString());
            }
        }
        return false;
    }

    private HashMap<String, String> readStateColors(Element root){
        Map<String, String> colorMap = new HashMap<String, String>();
        NodeList colors = root.getElementsByTagName("color");
        for (int i = 0; i < colors.getLength(); i++) {
            Element element = (Element) colors.item(i);
            String colorval = element.getTextContent();
            colorMap.put(element.getAttributes().getNamedItem("id").getNodeValue(), colorval);
        }
        return (HashMap)colorMap;
    }

    // Get root element of an XML file
    private Element getRootElement (File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            var xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    // Returns if this is a valid XML file for the specified object type
    private boolean isValidFile (Element root, String type) {
        return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
    }

    // Get value of Element's attribute
    private String getAttribute (Element e, String attributeName) {
        return e.getAttribute(attributeName);
    }

    // Get value of Element's text
    private String getTextValue (Element e, String tagName) throws NullPointerException {
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

    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }

    private String readInEdges(Element root){
        return getTextValue(root, XMLParser.EDGE_TYPE_TAG_NAME);
    }
}
