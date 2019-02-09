import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.File;
import java.util.HashMap;

public class CellularAutomataMain extends Application {
    public static final int WINDOW_SIZE = 1000;
    private GUI myGUI;
    private Simulation myCurrentSimulation;
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        String testCase = "tests/GameOfLifeTest.xml";
        //String testCase = "tests/SegregationTest.xml";
        //String testCase = "tests/PercolationTest.xml";
//        String testCase = "tests/SpreadingFireTest.xml";
        //String testCase = "tests/WatorTest.xml";
        File file = new File(testCase);
        var p = new XMLParser(Simulation.DATA_TYPE).getSimulation(file);
        try{
            myCurrentSimulation = p;
        }catch (Exception e){
            e.printStackTrace();
        }
        myGUI = new GUI(stage,myCurrentSimulation);
        //myGUI.render();
    }
}


