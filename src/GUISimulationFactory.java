import javafx.stage.Stage;

public class GUISimulationFactory {
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
            default:
                testCase = "tests/GameOfLifeTest.xml";
                break;
        }
        return testCase;
    }
    public GUISimulationPanel makeSimulationPanel(String newSim, Simulation mySim){
        GUISimulationPanel mySimPanel;
        switch(newSim){
            case Simulation.GOL_SIMULATION_NAME:
                mySimPanel = new GUIGameOfLifePanel(mySim);
                break;
            case Simulation.SPREADING_FIRE_SIMULATION_NAME:
                mySimPanel = new GUISpreadingFirePanel(mySim);
                break;
            case Simulation.PERCOLATION_SIMULATION_NAME:
                mySimPanel = new GUIPercolationPanel(mySim);
                break;
            case Simulation.SEGREGATION_SIMULATION_NAME:
                mySimPanel = new GUISegregationPanel(mySim);
                break;
            case Simulation.WATOR_SIMULATION_NAME:
                mySimPanel = new GUIWatorPanel(mySim);
                break;
            default:
                mySimPanel = new GUIGameOfLifePanel(mySim);
                break;
        }
        return mySimPanel;
    }

    public GUIGrid makeGUIGrid(String newShape, Simulation mySim, Stage s){
        GUIGrid myGrid;
        GUIGridPolygon myPolygon = makeGUIPolygon(newShape,mySim);
        myGrid = new GUIGrid(mySim.getMyGrid().getNumRows(), mySim.getMyGrid().getNumCols(),mySim, s,myPolygon);
        return myGrid;

    }
    public GUIGridPolygon makeGUIPolygon(String newShape, Simulation mySim){
        GUIGridPolygon myPolygon;
        switch(newShape) {
            case "Rectangle":
                myPolygon = new GUIGridRectangle(mySim.getMyGrid().getNumRows(), mySim.getMyGrid().getNumCols());
                break;
            case "Triangle":
                myPolygon = new GUIGridTriangle(mySim.getMyGrid().getNumRows(), mySim.getMyGrid().getNumCols());
                break;
            case "Hexagon":
                myPolygon = new GUIGridHexagon(mySim.getMyGrid().getNumRows(), mySim.getMyGrid().getNumCols());
                break;
            default:
                myPolygon = new GUIGridRectangle(mySim.getMyGrid().getNumRows(), mySim.getMyGrid().getNumCols());
                break;
        }
        return myPolygon;

    }
}
