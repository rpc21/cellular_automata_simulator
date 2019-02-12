import javafx.scene.paint.Paint;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class setUpSimulation {

    public static final String FILETYPE = "media";
    public setUpSimulation(){
    }
    public Simulation setSimulation(File xmlParserFile, File styleFile){
        XMLParser xmlParser = new XMLParser(FILETYPE);
        XMLStyler xmlStyler = new XMLStyler(FILETYPE);

        Simulation mySimulation = xmlParser.setSimulation(xmlParserFile);
        xmlStyler.setSimulationStyle(styleFile, mySimulation);
        //Map<String, Paint> s = xmlStyler.getColorMap(new File("tests/COLORTEST.xml"));
        System.out.println(xmlStyler.getStylePropertiesMap(new File("tests/StyleTest1.xml")));
        return mySimulation;
    }

}
