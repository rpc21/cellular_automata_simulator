import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;

import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser.ExtensionFilter;


public class CellularAutomataMain extends Application {
    public static final int WINDOW_SIZE = 750;
    private Simulation myCurrentSimulation;

    private static final String DEFAULT_SIMULATION = "tests/GameOfLifeTest.xml";
    private static final String DEFAULT_STYLE = "tests/StyleTest1.XML";
    public static void main(String[] args){
        launch(args);
    }
    private static final String SELECT_STYLE_FILE = "Select the Simulation Style File";
    private static final String SELECT_TEST_FILE = "Select the Simulation Test File";
    private static final String ERROR_FILE = "Not a valid file!";
    private static final String START_SIMULATION = "Starting new ";
    private static final String SIMULATION = " Simulation";
//
//    @Override
//    public void start(Stage stage) {
//        //String testCase = "tests/GameOfLifeTest.xml";
//        //String testCase = "tests/SegregationTest.xml";
//        //String testCase = "tests/GOLTest.xml";
//        //String testCase = "tests/GameOfLifeTest.xml";
//        //String testCase = "tests/PercolationTest.xml";
//        String testCase = "SpreadingFireTest.xml";
//        //String testCase = "tests/SugarTest.xml";
//        //String testCase = "tests/WatorTest.xml";
//        //String testCase = "tests/ForageTest.xml";
//        //String testCase = "tests/PercolationNeighborsTest";
//        //String testCase = "tests/SmallGridTestSpreadingFire.xml";
//        File testFile = new File(testCase);
////
//        //String styleTestCase = "tests/ForageStyle.XML";
//       //String styleTestCase = "tests/SegregationStyle.XML";
//        //String styleTestCase = "tests/GameOfLifeStyle.XML";
//        //String styleTestCase = "tests/PercolationStyle.XML";
//        String styleTestCase = "tests/SpreadingFireStyle.XML";
//        //String styleTestCase = "tests/SugarStyle.XML";
//        //String styleTestCase = "tests/GameOfLifeStyle.XML";
//        File styleFile = new File(styleTestCase);
////
//        //var p = new XMLParser(Simulation.DATA_TYPE).getSimulation(file);
////        File testFile = new File(DEFAULT_SIMULATION);
//        //File styleFile = new File(DEFAULT_STYLE);
//        var simulation = new setUpSimulation().setSimulation(testFile, styleFile);
//        try{
//            myCurrentSimulation = simulation;
//        }catch (Exception e){
//            System.out.println("Invalid Simulation or Style File");
//        }
//        System.out.println("Ready to Display");
//
//        GUI myGUI = new GUI(stage,myCurrentSimulation);
//
//    }

    // kind of data files to look for
    public static final String DATA_FILE_EXTENSION = "*.xml";

    // NOTE: generally accepted behavior that the chooser remembers where user left it last
    private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
    
    @Override
    public void start (Stage primaryStage) throws Exception {
        new Alert(AlertType.INFORMATION, SELECT_TEST_FILE).showAndWait();
        var dataFile = myChooser.showOpenDialog(primaryStage);
        new Alert(AlertType.INFORMATION, SELECT_STYLE_FILE).showAndWait();
        var styleFile = myChooser.showOpenDialog(primaryStage);
        if (dataFile != null&&styleFile!=null) {
            try {
                var simulation = new setUpSimulation().setSimulation(dataFile, styleFile);
                StringBuilder startMessage = new StringBuilder(START_SIMULATION+simulation.getMyName()+SIMULATION);
                new Alert(AlertType.INFORMATION, startMessage.toString()).showAndWait();
                try{
                    myCurrentSimulation = simulation;
                    GUI myGUI = new GUI(primaryStage,myCurrentSimulation);
                }catch (Exception e){
                    new Alert(AlertType.INFORMATION, ERROR_FILE).showAndWait();
                }
            }
            catch (XMLException e) {
                new Alert(AlertType.ERROR, e.getMessage()).showAndWait();
            }
        }
    }

    // set some sensible defaults when the FileChooser is created
    private FileChooser makeChooser (String extensionAccepted) {
        var result = new FileChooser();
        result.setTitle("Open Data File");
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Text Files", extensionAccepted));
        return result;
    }


}
