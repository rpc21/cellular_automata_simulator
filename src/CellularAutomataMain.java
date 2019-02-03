import javafx.application.Application;
import javafx.stage.Stage;
import java.io.File;

public class CellularAutomataMain extends Application {
    public static final int WINDOW_SIZE = 800;
    private Simulation myCurrentSimulation;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //String testCase = "tests/GOLTest.xml";
        String testCase = "tests/SegregationTest.xml";
        File file = new File(testCase);
        var p = new XMLParser(Simulation.DATA_TYPE).getSimulation(file);
        try{
            myCurrentSimulation = p;
        }catch (Exception e){
            e.printStackTrace();
        }
        GUI myGUI = new GUI(stage,myCurrentSimulation);
        myGUI.render();
    }
}


