import javafx.application.Application;
import javafx.stage.Stage;
import java.io.File;

public class CellularAutomataMain extends Application {
    public static final int WINDOW_SIZE = 1000;
    private Simulation myCurrentSimulation;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //String testCase = "tests/GOLTest.xml";
        //String testCase = "tests/SegregationTest.xml";
        String testCase = "tests/PercolationTest.xml";
//        String testCase = "tests/SpreadingFireTest.xml";
        //String testCase = "tests/WatorTest.xml";
        File Testfile = new File(testCase);

        String styleTestCase = "tests/StyleTest1.XML";
        File styleFile = new File(styleTestCase);

        var simulation = new setUpSimulation().setSimulation(Testfile, styleFile);
        //var p = new XMLParser(Simulation.DATA_TYPE).getSimulation(file);
        try{
            myCurrentSimulation = simulation;
        }catch (Exception e){
            e.printStackTrace();
        }

        GUI myGUI = new GUI(stage,myCurrentSimulation);
        //myGUI.render();
    }
}


