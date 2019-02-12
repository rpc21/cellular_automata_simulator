
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GUISimulationFactory {
    private static final String STYLER_NAME = "media";
    public String makeXMLFileName(String newSim){
        String testCase;
        switch(newSim){
            case Simulation.GOL_SIMULATION_NAME:
                testCase = "tests/GameOfLifeTest.xml";
                break;
            case Simulation.SPREADING_FIRE_SIMULATION_NAME:
                testCase = "tests/SpreadingFireTest.xml";
                break;
            case Simulation.PERCOLATION_SIMULATION_NAME:
                testCase = "tests/PercolationTest.xml";
                break;
            case Simulation.SEGREGATION_SIMULATION_NAME:
                testCase = "tests/SegregationTest.xml";
                break;
            case Simulation.WATOR_SIMULATION_NAME:
                testCase = "tests/WatorTest.xml";
                break;
            case Simulation.FORAGE_SIMULATION_NAME:
                testCase = "tests/ForageTest.xml";
                break;
            case Simulation.SUGAR_SIMULATION_NAME:
                testCase = "tests/SugarTest.xml";
                break;
            default:
                testCase = "tests/GameOfLifeTest.xml";
                break;
        }
        return testCase;
    }
    private String makeXMLStyleName(String newSim){
        String testCase;
        switch(newSim){
            case Simulation.GOL_SIMULATION_NAME:
                testCase = "tests/GameOfLifeStyle.xml";
                break;
            case Simulation.SPREADING_FIRE_SIMULATION_NAME:
                testCase = "tests/SpreadingFireStyle.xml";
                break;
            case Simulation.PERCOLATION_SIMULATION_NAME:
                testCase = "tests/PercolationStyle.xml";
                break;
            case Simulation.SEGREGATION_SIMULATION_NAME:
                testCase = "tests/SegregationStyle.xml";
                break;
            case Simulation.WATOR_SIMULATION_NAME:
                testCase = "tests/WatorStyle.xml";
                break;
            case Simulation.FORAGE_SIMULATION_NAME:
                testCase = "tests/ForageStyle.xml";
                break;
            case Simulation.SUGAR_SIMULATION_NAME:
                testCase = "tests/SugarStyle.xml";
                break;
            default:
                testCase = "tests/GameOfLifeStyle.xml";
                break;
        }
        return testCase;
    }
    public GUISimulationPanel makeSimulationPanel(String newSim, Simulation mySim){
        GUISimulationPanel mySimPanel;
        switch(newSim){
            case Simulation.GOL_SIMULATION_NAME:
                mySimPanel = new GUIGameOfLifePanel(newSim,mySim.getInitialParams());
                break;
            case Simulation.SPREADING_FIRE_SIMULATION_NAME:
                mySimPanel = new GUISpreadingFirePanel(newSim,mySim.getInitialParams());
                break;
            case Simulation.PERCOLATION_SIMULATION_NAME:
                mySimPanel = new GUIPercolationPanel(newSim,mySim.getInitialParams());
                break;
            case Simulation.SEGREGATION_SIMULATION_NAME:
                mySimPanel = new GUISegregationPanel(newSim,mySim.getInitialParams());
                break;
            case Simulation.WATOR_SIMULATION_NAME:
                mySimPanel = new GUIWatorPanel(newSim,mySim.getInitialParams());
                break;
            case Simulation.FORAGE_SIMULATION_NAME:
                mySimPanel = new GUIBlankPanel(newSim,mySim.getInitialParams());
                break;
            case Simulation.SUGAR_SIMULATION_NAME:
                mySimPanel = new GUIBlankPanel(newSim,mySim.getInitialParams());
                break;
            default:
                mySimPanel = new GUIGameOfLifePanel(newSim,mySim.getInitialParams());
                break;
        }
        return mySimPanel;
    }

    public GUIGrid makeGUIGrid(Simulation mySim, Stage stage){
        GUIGrid myGrid;
        XMLStyler myStyler = new XMLStyler(STYLER_NAME);

        Map<String, String> initProps = myStyler.getStylePropertiesMap(new File(makeXMLStyleName(mySim.getMyName())));
        Map<String,Paint> myColors = myStyler.getColorMap(new File(makeXMLStyleName(mySim.getMyName())), mySim);

        GUIGridPolygon myPolygon = makeGUIPolygon(mySim.getMyGrid().getNumRows(), mySim.getMyGrid().getNumCols(),initProps.get(XMLStyler.NEIGHBORS_TYPE_TAG_NAME));
        GUIGridCell myCell = new GUIGridCell(myColors, mySim, myPolygon);
        myGrid = new GUIGrid(mySim.getMyGrid().getNumRows(), mySim.getMyGrid().getNumCols(),stage,myCell,initProps);
        return myGrid;

    }
    public GUIGridPolygon makeGUIPolygon(int rows, int cols, String shape){
        GUIGridPolygon myPolygon;
        switch(shape) {
            case "rectangle":
                myPolygon = new GUIGridRectangle(rows,cols);
                break;
            case "triangle":
                myPolygon =  new GUIGridTriangle(rows,cols);
                break;
            case "hexagon":
                myPolygon = new GUIGridHexagon(rows,cols);
                break;
            default:
                myPolygon = new GUIGridRectangle(rows,cols);
                break;
        }
        return myPolygon;

    }
}
