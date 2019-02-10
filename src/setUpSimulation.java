import java.io.File;

public class setUpSimulation {

    public static final String FILETYPE = "media";
    public setUpSimulation(){
    }
    public Simulation setSimulation(File xmlParserFile, File styleFile){
        XMLParser xmlParser = new XMLParser(FILETYPE);
        XMLStyler xmlStyler = new XMLStyler(FILETYPE);

        Simulation mySimulation = xmlParser.setSimulation(xmlParserFile);
        xmlStyler.setSimulationStyle(styleFile, mySimulation);
        return mySimulation;
    }

}
