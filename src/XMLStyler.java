import javafx.scene.control.Alert;
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
import java.util.*;

/**
 * This class parses and validates a style .xml file and updates the simulation with the styles set in the file
 * Used to set the shape of cells in the simulation, type of neighbors used in the simulation, enable or disable grid
 * lines, set the type of edges used in the simulation, and the colors used in the simulation
 *
 * This class assumes that the tag names for the fields or options to set are correctly specified
 * If any field is missing, then the defaults are used for that simulation.
 * If any invalid data or selection is entered, then the defaults are used
 *
 * This class depends on XMLParserGeneral which implements methods used for parsing any xml file
 * To use the class, the user must have pass in an initialized simulation with which to associate these style options
 * The call XMLStyler.setSimulationStyle() is used to set the style options from an xml file to the desire simulation
 *
 * @author Dima Fayyad
 */

public class XMLStyler extends XMLParserGeneral{
    public static final String SHAPE_TYPE_TAG_NAME="shape";
    public static final String NEIGHBORS_TYPE_TAG_NAME="neighborsType";
    public static final String OUTLINE_TAG_NAME="outline";
    public static final String STATE_COLORS_TAG_NAME = "stateColors";
    public static final String defaultShape = "rectangle";
    public static final String defaultNeighbors = "ADJACENT";
    public static final String GRID_LINE_ON = "yes";
    public static final String GRID_LINE_OFF = "no";
    public static final String defaultGridLineSetting = GRID_LINE_ON;
    public static final String COLOR = "color";
    public static final String ID = "id";
    private Queue<Paint> DEFAULT_COLORS= new LinkedList<>(List.of(Color.ALICEBLUE, Color.BISQUE, Color.BLANCHEDALMOND, Color.HONEYDEW, Color.LINEN));

    private final String TYPE_ATTRIBUTE;
    private final DocumentBuilder DOCUMENT_BUILDER;

    /**
     * Class constructor
     * @param type the type of expected
     */
    public XMLStyler (String type) {
        super(type);
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }


    /**
     *setSimulationStyle is used to set style options read in from an xml file for a simulation
     * @param dataFile the xml file containing the style settings
     * @param simulation the simulation for to which the style settings will be applied
     * @return the simulation after being updated with the styles settings read in from the xml file
     */
    public Simulation setSimulationStyle(File dataFile, Simulation simulation){
        var root = getRootElement(dataFile);
        simulation.setColors(readStateColors(root, simulation));
        Map<String, String> map = makeStylePropertiesMap(root);
        simulation.setMyStyleProperties(map);
        simulation.updateNeighbors(map);
        return simulation;
    }

    /**
     * getColorMap is used when the map from state to colors is needed
     * The method validates that the map associates each state in the simulation with a color, and fills in any missing
     * states with default colors
     * @param dataFile the xml file containing the style settings
     * @param mySim the simulation for to which the style settings will be applied
     * @return the map of states to colors set in the xml file
     */
    public Map<String, Paint> getColorMap(File dataFile, Simulation mySim) {
        Map<String, Paint> colorMap = new HashMap<String, Paint>();
        var root = getRootElement(dataFile);
        NodeList colors = root.getElementsByTagName(COLOR);
        for (int i = 0; i < colors.getLength(); i++) {
            Element element = (Element) colors.item(i);
            String colorval = element.getTextContent();
            Paint p = Paint.valueOf(colorval);
            colorMap.put(element.getAttributes().getNamedItem(ID).getNodeValue(), p);
        }
        validateColorMap(colorMap, mySim);
        return colorMap;
    }

    /**
     * getStyleProperties map is used to immediately extract the map of style options to their set values from a given file
     * Used when the information in the xml is desired independent of the simulation for which it will be used
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
                System.out.print("Not a valid neighbors option. Default used.");
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

    private boolean isValidNeighbors(String possibleNeighbors){
        for(NeighborsDefinitions neighborType: NeighborsDefinitions.values()){
            if(possibleNeighbors.toUpperCase().equals(neighborType.toString().toUpperCase())){
                return possibleNeighbors.equals(neighborType.toString());
            }
        }
        return false;
    }

    private boolean isValidShape(String possibleShape){
        for(simulationShapes s: simulationShapes.values()){
            if(possibleShape.equals(s.toString())){
                return possibleShape.equals(s.toString());
            }
        }
        return false;
    }

    private HashMap<String, String> readStateColors(Element root, Simulation simulation){
        Map<String, String> colorMap = new HashMap<String, String>();
        NodeList colors = root.getElementsByTagName(COLOR);
        for (int i = 0; i < colors.getLength(); i++) {
            Element element = (Element) colors.item(i);
            String colorval = element.getTextContent();
            colorMap.put(element.getAttributes().getNamedItem(ID).getNodeValue(), colorval);
        }
        return (HashMap)colorMap;
    }

    private void validateColorMap(Map<String, Paint> colorMap, Simulation simulation){
        try{
            List<String> states = simulation.getMyGrid().getCells().get(0).getCurrentCellState().getPossibleValues();
            for(String state: states){
                if (!colorMap.containsKey(state)){
                    Paint color = DEFAULT_COLORS.poll();
                    colorMap.put(state, color);
                    DEFAULT_COLORS.add(color);
                }
            }
        }catch(NullPointerException e){
            throw new NullPointerException("Unable to validate color map");
        }
    }

    private String readInEdges(Element root){
        return getTextValue(root, XMLParser.EDGE_TYPE_TAG_NAME);
    }
}
