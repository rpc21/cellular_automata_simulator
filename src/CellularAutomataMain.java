import javafx.application.Application;
import javafx.stage.Stage;
import java.io.File;

public class CellularAutomataMain extends Application {
    public static final int WINDOW_SIZE = 1000;
    private Simulation myCurrentSimulation;

    private static final String DEFAULT_SIMULATION = "tests/SpreadingFireTest.xml";
    private static final String DEFAULT_STYLE = "tests/StyleTest1.XML";
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        File testFile = new File(DEFAULT_SIMULATION);
        File styleFile = new File(DEFAULT_STYLE);
        var simulation = new setUpSimulation().setSimulation(testFile, styleFile);
        try{
            myCurrentSimulation = simulation;
        }catch (Exception e){
            System.out.println("Invalid Simulation or Style File");
        }

        GUI myGUI = new GUI(stage,myCurrentSimulation);

    }
}



