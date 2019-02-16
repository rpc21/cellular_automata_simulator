
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.File;
import java.util.Map;

public class GUISimulationFactory {
    private static final String STYLER_NAME = "media";
    /**
     * Initializes XMLFile name for setup purposes. This method is public for GUIManager to access if it needs ot add a new simulation
     * @param newSim name of the simulation to be loaded in
     */

    public String makeXMLFileName(String newSim){
        String testCase;
        switch(newSim){
            case Simulation.GOL_SIMULATION_NAME:
                testCase = "demo/GameOfLifeDemo.xml";
                break;
            case Simulation.SPREADING_FIRE_SIMULATION_NAME:
                testCase = "demo/SpreadingFireDemo.xml";
                break;
            case Simulation.PERCOLATION_SIMULATION_NAME:
                testCase = "demo/PercolationDemo.xml";
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
                testCase = "demo/SugarDemo.xml";
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
    /**
     * Initializes GUIGridSimulationPanel for display purposes. This method is public because GUIManager needs to access it if it needs
     * to add an additional simulation
     * @param newSim the SimulationPanel keeps track of the name of the current simulation to compare against user input
     * @param mySim This is the Simulation instance that will specify the initial properties of the GUISimulationPanel
     * @see GUISimulationPanel
     */
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
    /**
     * Initializes GUIGrid for display purposes. This method is public because GUIManager needs to access it if it needs
     * to add an additional simulation
     * @param mySim This is the Simulation instance that will specify the initial properties of the GUIGrid's options
     * @param stage This is needed to handle adding another window to the scene for GUIGrid's options window
     * @see GUIGrid
     */

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
    /**
     * Initializes GUIGridPolygon for display purposes. This method is public because if the user switches shapes, there needs
     * to be a way for the GUIGridOptions to access this method in order to know which shape to switch to
     * @param rows This is the number of rows in the simulation
     * @param cols This is the number of columns in the simulation
     * @param shape This is the string used to determine which subclass of GUIGridPolygon to instantiate
     */

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
