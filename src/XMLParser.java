import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


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
    public Simulation getSimulation(File dataFile) {
        var root = getRootElement(dataFile);
//        if (! isValidFile(root, Simulation.DATA_TYPE)) {
//            throw new XMLException(ERROR_MESSAGE, Simulation.DATA_TYPE);
//        }
        HashMap<String, String> simulationParams = getBasicSimulationParams(root);
        String simulationType = simulationParams.get("simulationType");
        int rows = Integer.parseInt(simulationParams.get("rows"));
        int cols = Integer.parseInt(simulationParams.get("columns"));

        String[][] specifiedStates = parseGrid(root, rows, cols, simulationType);
        HashMap<String, Double> additionalParams = parseAdditionalParams(root, simulationType);
        HashMap<String, String> theCredentials = getCredentials(root);
        String howToSetInitialStates = getTextValue(root, "GenerateStatesBy");

        SimulationFactory mySimulationFactory = new SimulationFactory();
        Simulation mySim;
        if(howToSetInitialStates.equals("random")){
            mySim = mySimulationFactory.generateSimulation(simulationParams, additionalParams);
        }else{
            mySim = mySimulationFactory.generateSimulation(simulationParams, additionalParams, specifiedStates);
        }
        mySim.setCredentials(theCredentials);

        return mySim;
    }

    private HashMap<String, String> getCredentials(Element root) {
        var myCredentials = new HashMap<String, String>();
        for (var field : Simulation.DATA_CREDENTIALS) {
            myCredentials.put(field, getTextValue(root, field));
        }
        return myCredentials;
    }

    private HashMap<String, String> getBasicSimulationParams(Element root){
        var simulationParams = new HashMap<String, String>();
        for (var field : Simulation.DATA_FIELDS) {
            simulationParams.put(field, getTextValue(root, field));
        }
        return simulationParams;
    }

    private HashMap<String, Double> parseAdditionalParams(Element root, String simulationType){
        HashMap<String, Double> parameters = new HashMap<String, Double>();
        List<String> simulationFields = getSimulationDataFields(simulationType);
        for (var field : simulationFields) {
            parameters.put(field, Double.parseDouble(getTextValue(root, field)));
        }
        return parameters;
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

    //expects states to be ints
//    private int[][] parseGrid(Element root, int rows, int cols, String simulationType){
//        NodeList cellRows = root.getElementsByTagName("CellRows");
//        int[][] specifiedStates = new int[rows][cols];
//        if ((cellRows != null))
//            for (int i = 0; i < cellRows.getLength(); i++) {
//                NodeList columnsList = cellRows.item(i).getChildNodes();
//                if ((columnsList != null)){
//                    int colCount=0;
//                    for (int j = 0; j < columnsList.getLength(); j++) {
//                        var state = columnsList.item(j);
//                        if ("CellColumns".equals(state.getNodeName())) {
//                            System.out.println("(" + i + "," + colCount + ") " + state.getTextContent());
//                            specifiedStates[i][colCount] = Integer.parseInt(state.getTextContent());//+
//                            // calcStateOffset(simulationType);
//                            colCount++;
//                        }
//                    }
//                }
//            }
//        return specifiedStates;
//    }

    //expect states to be Strings
    private String[][] parseGrid(Element root, int rows, int cols, String simulationType){
        NodeList cellRows = root.getElementsByTagName("CellRows");
        String[][] specifiedStates = new String[rows][cols];
        if ((cellRows != null))
            for (int i = 0; i < cellRows.getLength(); i++) {
                NodeList columnsList = cellRows.item(i).getChildNodes();
                if ((columnsList != null)){
                    int colCount=0;
                    for (int j = 0; j < columnsList.getLength(); j++) {
                        var state = columnsList.item(j);
                        if ("CellColumns".equals(state.getNodeName())) {
                            System.out.println("(" + i + "," + colCount + ") " + state.getTextContent());
//                            try{
//                                //specifiedStates[i][colCount]=Integer.parseInt(state.getTextContent());
//                            } catch(NumberFormatException e){
//                                specifiedStates[i][colCount] = state.getTextContent();
//                            }
                            specifiedStates[i][colCount] = state.getTextContent();
                            colCount++;
                        }
                    }
                }
            }
        return specifiedStates;
    }

//    private Simulation generateSimulation(HashMap<String, String> simulationParams){
//        String simulationType = simulationParams.get("simulationType");
//        int rows = Integer.parseInt(simulationParams.get("rows"));
//        int cols = Integer.parseInt(simulationParams.get("columns"));
//        if (simulationType.equals("Game of Life")){
//            return new GOLSimulation(rows, cols);
//        }
//        else if (simulationType.equals("Spreading Fire")){
//            return new SpreadingFireSimulation(rows, cols);
//        }
//        else if (simulationType.equals("Percolation")){
//            return new PercolationSimulation(rows, cols);
//        }
//        else if (simulationType.equals("Segregation")){ return new SegregationSimulation(rows, cols); }
//        return new GOLSimulation(rows, cols);
//    }

    private int calcStateOffset(String simulationType){
        HashMap<String, Integer> simulationToOffsetMap = new HashMap<String, Integer>();
        simulationToOffsetMap.put("GOLSimulation", 1200);
        simulationToOffsetMap.put("Spreading Fire", 140002);
        simulationToOffsetMap.put("Percolation", 150002);
        simulationToOffsetMap.put("Segregation", 13700);
        return simulationToOffsetMap.get(simulationType);
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

    // Boilerplate code needed to make a documentBuilder
    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
