import javafx.scene.paint.Paint;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Sets up a simulation using an XML file with the simulation information and another XML file with the style preferences for the simulation
 * Depends on the XMLParser and XMLStyler classes
 *
 * If the XML files passed are not found or are not valid, then the default simulation, Game of Life, is initialized
 *
 * @author Dima Fayyad
 */
public class setUpSimulation {

    public static final String FILETYPE = "media";
//    public setUpSimulation(){}

    /**
     * Uses XMLParser and XMLStyler to setup a new simulation of the desired type and properties
     * @param xmlParserFile the XML file containing simulation information
     * @param styleFile file with the style preferences for the simulation
     * @return mySimulation, a new initialized simulation that is ready to be displayed
     */
    public Simulation setSimulation(File xmlParserFile, File styleFile){
        XMLParser xmlParser = new XMLParser(FILETYPE);
        XMLStyler xmlStyler = new XMLStyler(FILETYPE);

        Simulation mySimulation = xmlParser.setSimulation(xmlParserFile);
        xmlStyler.setSimulationStyle(styleFile, mySimulation);
        return mySimulation;
    }

}
