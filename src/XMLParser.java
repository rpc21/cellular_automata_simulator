import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


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

        int[][] specifiedStates = parseGrid(root, rows, cols, simulationType);
        HashMap<String, Double> additionalParams = parseAdditionalParams(root);

        Simulation sim = generateSimulation(simulationParams);
        sim.setInitialStates(specifiedStates, simulationType, additionalParams);
        return sim;
    }

    private HashMap<String, String> getBasicSimulationParams(Element root){
        var simulationParams = new HashMap<String, String>();
        for (var field : Simulation.DATA_FIELDS) {
            simulationParams.put(field, getTextValue(root, field));
        }
        return simulationParams;
    }


    private HashMap<String, Double> parseAdditionalParams(Element root){
        HashMap<String, Double> parameters = new HashMap<String, Double>();
        NodeList params = root.getElementsByTagName("parameters");
        if(params!=null){
            for(int i = 0; i < params.getLength(); i++){
                var p = params.item(i);
                if ("parameters".equals(p.getNodeName())&& p.getTextContent().split(" ").length>1) {
                    parameters.put(p.getTextContent().split(" ")[0], Double.parseDouble(p.getTextContent().split(" ")[1]));
                }
            }
        }
        return parameters;
    }

    private int[][] parseGrid(Element root, int rows, int cols, String simulationType){
        NodeList cellRows = root.getElementsByTagName("CellRows");
        int[][] specifiedStates = new int[rows][cols];
        if ((cellRows != null))
            for (int i = 0; i < cellRows.getLength(); i++) {
                NodeList columnsList = cellRows.item(i).getChildNodes();
                if ((columnsList != null)){
                    int colCount=0;
                    for (int j = 0; j < columnsList.getLength(); j++) {
                        var state = columnsList.item(j);
                        if ("CellColumns".equals(state.getNodeName())) {
                            System.out.println("(" + i + "," + colCount + ") " + state.getTextContent());
                            specifiedStates[i][colCount] = Integer.parseInt(state.getTextContent());//+
                            // calcStateOffset(simulationType);
                            colCount++;
                        }
                    }
                }
            }
        return specifiedStates;
    }

    private static Simulation generateSimulation(HashMap<String, String> simulationParams){
        String simulationType = simulationParams.get("simulationType");
        int rows = Integer.parseInt(simulationParams.get("rows"));
        int cols = Integer.parseInt(simulationParams.get("columns"));
        if (simulationType.equals("Game of Life")){
            return new GOLSimulation(rows, cols);
        }
        else if (simulationType.equals("Spreading Fire")){
            return new SpreadingFireSimulation(rows, cols);
        }
        else if (simulationType.equals("Percolation")){
            return new PercolationSimulation(rows, cols);
        }
        else if (simulationType.equals("Segregation")){ return new SegregationSimulation(rows, cols); }
        return new GOLSimulation(rows, cols);
    }

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
