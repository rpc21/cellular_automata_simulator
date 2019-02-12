import javafx.scene.paint.Color;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.*;


/**
 * This class handles parsing XML files and returning a new simulation.
 *
 * Based on code by:
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 *
 * Author:
 * @author Dima Fayyad
 *
 * Note: Defaults set for the Game of Life simulation
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
    public static final String EDGE_TYPE_TAG_NAME="edges";
    public static final int defaultColumns = 10;
    public static final int defaultRows = 10;
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
    public Simulation setSimulation(File dataFile){
        var root = getRootElement(dataFile);
        if (!isValidFile(root, Simulation.DATA_TYPE)) { throw new XMLException(ERROR_MESSAGE, Simulation.DATA_TYPE); }
        HashMap<String, String> simulationParams = getBasicSimulationParams(root);
        String simulationType = simulationParams.get(SIMULATION_TYPE_TAG_NAME);
        validateRowsAndColumns(simulationParams);
        int rows = Integer.parseInt(simulationParams.get(ROW_TAG_NAME));
        int cols = Integer.parseInt(simulationParams.get(COLUMN_TAG_NAME));
        String[][] specifiedStates = parseGrid(root, rows, cols, simulationType);
        HashMap<String, Double> additionalParams = parseAdditionalParams(root, simulationType);
        HashMap<String, String> theCredentials = getCredentials(root);
        String howToSetInitialStates = getTextValue(root, GEN_STATES_TAG_NAME);

        return initializeSimulation(howToSetInitialStates, simulationParams, additionalParams, specifiedStates, theCredentials);
    }

    private void validateRowsAndColumns(HashMap<String, String> simulationParams){
        int rows,cols;
        try{
            if(Integer.parseInt(simulationParams.get(COLUMN_TAG_NAME))<defaultColumns){
                cols = defaultColumns;
                simulationParams.put(COLUMN_TAG_NAME, String.valueOf(cols));
            }
        }catch(NumberFormatException e){
            cols = defaultColumns;
            simulationParams.put(COLUMN_TAG_NAME, String.valueOf(cols));
        }
        try{
            if(Integer.parseInt(simulationParams.get(ROW_TAG_NAME))<defaultRows){
                rows = defaultRows;
                simulationParams.put(ROW_TAG_NAME, String.valueOf(rows));
            }
        }catch(NumberFormatException e){
            rows = defaultRows;
            simulationParams.put(ROW_TAG_NAME, String.valueOf(rows));
        }
    }

    private Simulation initializeSimulation(String howToSetInitialStates, HashMap<String, String> simulationParams, HashMap<String, Double> additionalParams, String[][] specifiedStates, HashMap<String, String> theCredentials){
        try {
            SimulationFactory mySimulationFactory = new SimulationFactory();
            Simulation mySim;
            if (howToSetInitialStates.equals(RANDOM_STRING)) {
                mySim = mySimulationFactory.generateSimulation(simulationParams, additionalParams);
            } else if (howToSetInitialStates.equals(COMPLETELY_RANDOM_STRING)) {
                mySim = mySimulationFactory.generateSimulation(simulationParams, additionalParams, COMPLETELY_RANDOM_STRING);
            } else if (isValidStatesArray(specifiedStates, simulationParams)){
                mySim = mySimulationFactory.generateSimulation(simulationParams, additionalParams, specifiedStates);
            } else {
                System.out.println("Invalid state information provided. Initial states randomly set");
                mySim = mySimulationFactory.generateSimulation(simulationParams, additionalParams, COMPLETELY_RANDOM_STRING);
            }
            mySim.setCredentials(theCredentials);
            return mySim;
        }catch(NullPointerException e){
            throw new NullPointerException("Simulation could not be initialized from the given parameters");
        }
    }

    private boolean isValidStatesArray(String[][] specifiedStates, HashMap<String, String> simulationParams){
        for(String[] row:specifiedStates){
            for(String state:row){ if(!isValidState(state, simulationParams.get("simulationType"))){
                return false; } }
        }
        try {
            return (Integer.parseInt(simulationParams.get("rows")) == specifiedStates.length &&
                    Integer.parseInt(simulationParams.get("rows")) == specifiedStates[0].length);
        }catch(ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    private HashMap<String, String> getCredentials(Element root) {
        var myCredentials = new HashMap<String, String>();
        try{
            for (var field : Simulation.DATA_CREDENTIALS) {
                myCredentials.put(field, getTextValue(root, field));
            }
            return myCredentials;
        } catch(NullPointerException e){
            throw new NullPointerException("Invalid author or name");
        }
    }

    private HashMap<String, String> getBasicSimulationParams(Element root){
        var simulationParams = new HashMap<String, String>();
        for (var field : Simulation.DATA_FIELDS) {
            simulationParams.put(field, getTextValue(root, field));
        }
        simulationParams.put(EDGE_TYPE_TAG_NAME, readInEdges(root));
        return simulationParams;
    }

    private HashMap<String, Double> parseAdditionalParams(Element root, String simulationType){
        try{
            HashMap<String, Double> parameters = new HashMap<String, Double>();
            List<String> simulationFields = getSimulationDataFields(simulationType);
            for (var field : simulationFields) {
                try{
                    parameters.put(field, Double.parseDouble(getTextValue(root, field)));
                }catch(NumberFormatException e){
                    parameters.put(field, 0.0D);
                }
            }
            return parameters;
        } catch(NullPointerException e){
            throw new NullPointerException("Field is missing");
        } catch(NumberFormatException e){
            throw new NumberFormatException("Double required for simulation parameters");
        }

    }

    private List<String> getSimulationDataFields(String simulationType) {
        switch (simulationType) {
            case Simulation.GOL_SIMULATION_NAME:
                return GOLSimulation.GOL_DATA_FIELDS;
            case Simulation.SPREADING_FIRE_SIMULATION_NAME:
                return SpreadingFireSimulation.SPREADING_FIRE_DATA_FIELDS;
            case Simulation.PERCOLATION_SIMULATION_NAME:
                return PercolationSimulation.PERCOLATION_DATA_FIELDS;
            case Simulation.SEGREGATION_SIMULATION_NAME:
                return SegregationSimulation.SEGREGATION_DATA_FIELDS;
            case Simulation.WATOR_SIMULATION_NAME:
                return WatorSimulation.WATOR_DATA_FIELDS;
            case Simulation.SUGAR_SIMULATION_NAME:
                return SugarSimulation.SUGAR_DATA_FIELDS;
            case Simulation.FORAGE_SIMULATION_NAME:
                return ForageSimulation.FORAGE_DATA_FIELDS;
        }
        return GOLSimulation.GOL_DATA_FIELDS;
    }

    private List<String> getSimulationStates(String simulationType){
        switch (simulationType) {
            case Simulation.GOL_SIMULATION_NAME:
                return GOLState.DEAD.getPossibleValues();
            case Simulation.SPREADING_FIRE_SIMULATION_NAME:
                return SpreadingFireState.FIRE.getPossibleValues();
            case Simulation.PERCOLATION_SIMULATION_NAME:
                return PercolationState.OPEN.getPossibleValues();
            case Simulation.SEGREGATION_SIMULATION_NAME:
                return SegregationState.RED.getPossibleValues();
            case Simulation.WATOR_SIMULATION_NAME:
                return WatorState.EMPTY.getPossibleValues();
            case Simulation.SUGAR_SIMULATION_NAME:
                return SugarState.LIGHT_PATCH.getPossibleValues();
            case Simulation.FORAGE_SIMULATION_NAME:
                return ForageState.NEST.getPossibleValues();
        }
        return GOLState.DEAD.getPossibleValues();
    }

    //expect states to be Strings
    private String[][] parseGrid(Element root, int rows, int cols, String simulationType){
        String[][] specifiedStates = new String[rows][cols];
        try {
            specifiedStates = parseRows(root, rows, cols);
        } catch(NullPointerException e){
            //throw new NullPointerException("No initial states specified");
            return specifiedStates; //Not inputting states is valid
        }
        return specifiedStates;
    }

    private boolean isValidState(String state, String simulationType){
        List<String> states = getSimulationStates(simulationType);
        return(states.contains(state));
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
                int increment = addCellToInitialStatesArray(state, specifiedStates, i, colCount);
                colCount = colCount+increment;
            }
        }
    }

    private int addCellToInitialStatesArray(Node state, String[][] specifiedStates, int i, int colCount){
        if (CELL_COLUMNS_TAG_NAME.equals(state.getNodeName())) {
            try{
                specifiedStates[i][colCount] = state.getTextContent();
                return 1;
            }catch(IndexOutOfBoundsException e){
                System.out.println("Rows and Columns set do not match initial states");
                return 0;
                //throw new IndexOutOfBoundsException("THIS IS OUT OF BOUNDS");
            }
        }
        return 0;
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

    private String readInEdges(Element root){
        try {
            return getTextValue(root, EDGE_TYPE_TAG_NAME);
        }catch(NullPointerException e){
            throw new NullPointerException("No Edge Type specified");
        }
    }

}
