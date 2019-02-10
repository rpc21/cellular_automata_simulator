import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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

    public GUIGrid makeGUIGrid(String newShape, Simulation mySim, Stage stage){
        GUIGrid myGrid;
        XMLStyler myStyler = new XMLStyler("media");
        Map<String, String> initProps = myStyler.getStylePropertiesMap(new File("tests/StyleTest1.XML"));
        HashMap<String, Paint> wrongOrderMap = new HashMap<>();
        wrongOrderMap.put("ALIVE", GOLState.ALIVE.getMyCellColor());
        wrongOrderMap.put("DEAD", GOLState.DEAD.getMyCellColor());
        GUIGridPolygon myPolygon = makeGUIPolygon(mySim.getMyGrid().getNumRows(), mySim.getMyGrid().getNumCols(),newShape);
        GUIGridCell myCell = new GUIGridCell(wrongOrderMap, mySim, myPolygon);
        myGrid = new GUIGrid(mySim.getMyGrid().getNumRows(), mySim.getMyGrid().getNumCols(),stage,myCell,initProps);
        return myGrid;

    }
    public GUIGridPolygon makeGUIPolygon(int rows, int cols, String shape){
        GUIGridPolygon myPolygon;
        switch(shape) {
            case "Rectangle":
                myPolygon = new GUIGridRectangle(rows,cols);
                break;
            case "Triangle":
                myPolygon =  new GUIGridTriangle(rows,cols);
                break;
            case "Hexagon":
                myPolygon = new GUIGridHexagon(rows,cols);
                break;
            default:
                myPolygon = new GUIGridRectangle(rows,cols);
                break;
        }
        return myPolygon;

    }
}
