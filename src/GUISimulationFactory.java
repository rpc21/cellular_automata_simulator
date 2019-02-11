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
    public String makeXMLStyleName(String newSim){
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
            case Simulation.FORAGE_SIMULATION_NAME:
                mySimPanel = new GUIBlankPanel(mySim);
                break;
            case Simulation.SUGAR_SIMULATION_NAME:
                mySimPanel = new GUIBlankPanel(mySim);
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
        System.out.println( "@@@"  + mySim.getMyName());
        Map<String, String> initProps = myStyler.getStylePropertiesMap(new File(makeXMLStyleName(mySim.getMyName())));

        System.out.println("%%%%" + initProps.keySet().contains(XMLStyler.NEIGHBORS_TYPE_TAG_NAME));
        System.out.println(initProps.get(XMLStyler.NEIGHBORS_TYPE_TAG_NAME));

        Map<String,Paint> myColors = myStyler.getColorMap(new File(makeXMLStyleName(mySim.getMyName())));
        GUIGridPolygon myPolygon = makeGUIPolygon(mySim.getMyGrid().getNumRows(), mySim.getMyGrid().getNumCols(),newShape);
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
