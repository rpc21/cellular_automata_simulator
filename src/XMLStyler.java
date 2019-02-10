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

public class XMLStyler {
    public static final String SHAPE_TYPE_TAG_NAME="shape";
    public static final String NEIGHBORS_TYPE_TAG_NAME="neighborsType";
    public static final String EDGE_TYPE_TAG_NAME="edges";
    public static final String OUTLINE_TAG_NAME="outline";
    public static final String STATE_COLORS_TAG_NAME = "stateColors";

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
        //simulation.setColors(readStateColors(simulation.getMyPossibleStates(), root));
        simulation.myStyleProperties =makeStylePropertiesMap(root);
        return simulation;
    }

    private HashMap<String, String> makeStylePropertiesMap(Element root){
        HashMap<String, String> styleProperties = new HashMap<String, String>();
        readInShape(root, styleProperties);
        readInNeighborsType(root, styleProperties);
        readInEdges(root, styleProperties);
        readInOutline(root, styleProperties);
        return styleProperties;
    }

    private void readInOutline(Element root, HashMap<String, String> styleProperties){
        String outline = getTextValue(root, OUTLINE_TAG_NAME);
        styleProperties.put(OUTLINE_TAG_NAME, outline);
    }

    private void readInEdges(Element root, HashMap<String, String> styleProperties){
        String edges = getTextValue(root, EDGE_TYPE_TAG_NAME);
        styleProperties.put(EDGE_TYPE_TAG_NAME, edges);
    }

    private void readInNeighborsType(Element root, HashMap<String, String> styleProperties) throws IllegalArgumentException{
        try {
            String neighbors = getTextValue(root, NEIGHBORS_TYPE_TAG_NAME);
            if (isValidNeighbors(neighbors)) {
                styleProperties.put(NEIGHBORS_TYPE_TAG_NAME, neighbors);
            } else {
                throw new IllegalArgumentException("neighbors type must be a valid string: " + NeighborsDefinitions.values());
            }
        } catch (IllegalArgumentException e){
            try {//try all lower case
                String lowerCaseName = getTextValue(root, NEIGHBORS_TYPE_TAG_NAME).toLowerCase();
                if(isValidNeighbors(lowerCaseName)){
                    styleProperties.put(NEIGHBORS_TYPE_TAG_NAME, lowerCaseName);
                }
            }
            catch (IllegalArgumentException ee) {
                throw new IllegalArgumentException("neighbors type must be a valid string: " + NeighborsDefinitions.values());
            }
        }
    }

    private void readInShape(Element root, HashMap<String, String> styleProperties) throws IllegalArgumentException {
        try {
            String shape = getTextValue(root, SHAPE_TYPE_TAG_NAME);
            if (isValidShape(shape)) {
                styleProperties.put(SHAPE_TYPE_TAG_NAME, shape);
            }
            else {
                System.out.println("not a valid shape!");
                throw new IllegalArgumentException("shape must be a valid string: " + simulationShapes.values());

            }
        } catch(IllegalArgumentException e){
            try {//try all lower case
                String lowerCaseName = getTextValue(root, SHAPE_TYPE_TAG_NAME).toLowerCase();
                if(isValidShape(lowerCaseName)){
                    styleProperties.put(SHAPE_TYPE_TAG_NAME, lowerCaseName);
                }
            }
            catch (IllegalArgumentException ee) {
                throw new IllegalArgumentException("shape must be a valid string: " + simulationShapes.values());
            }
        }
    }

    public boolean isValidNeighbors(String possibleNeighbors){
        for(NeighborsDefinitions neighborType: NeighborsDefinitions.values()){
            if(possibleNeighbors.equals(neighborType.toString())){
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

    private HashMap<String, String> readStateColors(List<String> states, Element root){
        HashMap<String, String> statesToColors = new HashMap<String, String>();
        NodeList stateColorsList = root.getElementsByTagName(STATE_COLORS_TAG_NAME);
        for(String currentState: states){
            statesToColors.put(currentState,getTextValue(root, currentState));
            System.out.println(getTextValue(root, currentState));
        }
        return statesToColors;

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
    private String getTextValue (Element e, String tagName) {
        var nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {
            // FIXME: empty string or null, is it an error to not find the text value?
            return "";
        }
    }

    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
