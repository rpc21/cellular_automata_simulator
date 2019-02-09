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
import java.util.MissingFormatArgumentException;
import java.util.Random;


/**
 * This class handles parsing XML files and returning a new simulation.
 *
 * Based on code by:
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 *
 * Author:
 * @author Dima Fayyad
 */
public class XMLParser {
    // Readable error message that can be displayed by the GUI

    public static final String ERROR_MESSAGE = "XML file does not represent %s";
    public static final String ROW_TAG_NAME="rows";
    public static final String RANDOM_STRING="randomFromPercentages";
    public static final String COMPLETELY_RANDOM_STRING="completelyRandom";
    public static final String COLUMN_TAG_NAME="columns";
    public static final String SIMULATION_TYPE_TAG_NAME="simulationType";
    public static final String CELL_ROWS_TAG_NAME="CellRows";
    public static final String CELL_COLUMNS_TAG_NAME="CellColumns";
    public static final String GEN_STATES_TAG_NAME="GenerateStatesBy";
    // name of root attribute that notes the type of file expecting to parse
    private final String TYPE_ATTRIBUTE;
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;

    /**
     * Create a parser for XML files of given type.
     */
    public XMLParser (String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }

    /**
     * getSimulation takes in an XML file and returns a new simulation
     * @param dataFile the xml file containing the setup for the simulation
     * @return a new simulation
     */
    public Simulation setSimulation(File dataFile) {
        var root = getRootElement(dataFile);
//        if (! isValidFile(root, Simulation.DATA_TYPE)) {
//            throw new XMLException(ERROR_MESSAGE, Simulation.DATA_TYPE);
//        }
        HashMap<String, String> simulationParams = getBasicSimulationParams(root);
        String simulationType = simulationParams.get(SIMULATION_TYPE_TAG_NAME);
        int rows = Integer.parseInt(simulationParams.get(ROW_TAG_NAME));
        int cols = Integer.parseInt(simulationParams.get(COLUMN_TAG_NAME));

        String[][] specifiedStates = parseGrid(root, rows, cols, simulationType);
        HashMap<String, Double> additionalParams = parseAdditionalParams(root, simulationType);
        HashMap<String, String> theCredentials = getCredentials(root);
        String howToSetInitialStates = getTextValue(root, GEN_STATES_TAG_NAME);

        SimulationFactory mySimulationFactory = new SimulationFactory();
        Simulation mySim;

        if(howToSetInitialStates.equals(RANDOM_STRING)){
            mySim = mySimulationFactory.generateSimulation(simulationParams, additionalParams);
        }else if(howToSetInitialStates.equals(COMPLETELY_RANDOM_STRING)){
            //TODO SET THE PERCENTAGES RANDOMLY TO ADD TO 1
            mySim = mySimulationFactory.generateSimulation(simulationParams, additionalParams, COMPLETELY_RANDOM_STRING);
        }else{
            mySim = mySimulationFactory.generateSimulation(simulationParams, additionalParams, specifiedStates);
        }
        mySim.setCredentials(theCredentials);
        return mySim;
    }

    private HashMap<String, String> getCredentials(Element root) {
        var myCredentials = new HashMap<String, String>();
        try{
            for (var field : Simulation.DATA_CREDENTIALS) {
                myCredentials.put(field, getTextValue(root, field));
            }
            return myCredentials;
        } catch(NullPointerException e){
            throw new NullPointerException("Invalid author of name");
        }
    }

    private HashMap<String, String> getBasicSimulationParams(Element root){
        var simulationParams = new HashMap<String, String>();
        for (var field : Simulation.DATA_FIELDS) {
            simulationParams.put(field, getTextValue(root, field));
        }
        return simulationParams;
    }

    private HashMap<String, Double> parseAdditionalParams(Element root, String simulationType){
        try{
            HashMap<String, Double> parameters = new HashMap<String, Double>();
            List<String> simulationFields = getSimulationDataFields(simulationType);
            for (var field : simulationFields) {
                parameters.put(field, Double.parseDouble(getTextValue(root, field)));
            }
            return parameters;
        } catch(NullPointerException e){
            throw new NullPointerException("Field is missing");
        }

    }

    private List<String> getSimulationDataFields(String simulationType){
        if (simulationType.equals(Simulation.GOL_SIMULATION_NAME)){
            return GOLSimulation.GOL_DATA_FIELDS;
        }
        else if (simulationType.equals(Simulation.SPREADING_FIRE_SIMULATION_NAME)){
            return SpreadingFireSimulation.SPREADING_FIRE_DATA_FIELDS;
        }
        else if (simulationType.equals(Simulation.PERCOLATION_SIMULATION_NAME)){
            return PercolationSimulation.PERCOLATION_DATA_FIELDS;
        }
        else if (simulationType.equals(Simulation.SEGREGATION_SIMULATION_NAME)){
            return SegregationSimulation.SEGREGATION_DATA_FIELDS;
        }
        else if (simulationType.equals(Simulation.WATOR_SIMULATION_NAME)){
            return WatorSimulation.WATOR_DATA_FIELDS;
        }
        return GOLSimulation.GOL_DATA_FIELDS;
    }

    //expect states to be Strings
    private String[][] parseGrid(Element root, int rows, int cols, String simulationType){
        String[][] specifiedStates = new String[rows][cols];
        try {
            specifiedStates = parseRows(root, rows, cols);
        } catch(NullPointerException e){
            //ADD ERROR CATCHING
        }
        return specifiedStates;
    }

    private String[][] parseRows(Element root, int rows, int cols){
        NodeList cellRows = root.getElementsByTagName(CELL_ROWS_TAG_NAME);
        String[][] specifiedStates = new String[rows][cols];
        if(cellRows!=null){
            for (int i = 0; i < cellRows.getLength(); i++) {
                parseColumns(root, specifiedStates, cellRows, i);
            }
        }
        return specifiedStates;
    }

    private void parseColumns(Element root, String[][] specifiedStates, NodeList cellRows, int i){
        NodeList columnsList = cellRows.item(i).getChildNodes();
        if(columnsList!=null){
            int colCount = 0;
            for (int j = 0; j < columnsList.getLength(); j++) {
                Node state = columnsList.item(j);
                addCellToInitialStatesArray(state, specifiedStates, i, colCount);
            }
        }
    }

    private void addCellToInitialStatesArray(Node state, String[][] specifiedStates, int i, int colCount){
        if (CELL_COLUMNS_TAG_NAME.equals(state.getNodeName())) {
            System.out.println("(" + i + "," + colCount + ") " + state.getTextContent());
            specifiedStates[i][colCount] = state.getTextContent();
            colCount++;
        }
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
        try {
            return e.getAttribute(attributeName);
        }catch(NullPointerException exc){
            throw new NullPointerException("No such attribute");
        }
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

}
