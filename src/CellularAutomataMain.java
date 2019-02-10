import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser.ExtensionFilter;


public class CellularAutomataMain extends Application {
    public static final int WINDOW_SIZE = 1000;
    private Simulation myCurrentSimulation;

    public static void main(String[] args){
        launch(args);
    }

//    @Override
//    public void start(Stage stage) {
//        //String testCase = "tests/GOLTest.xml";
//        //String testCase = "tests/SegregationTest.xml";
//        //String testCase = "tests/PercolationTest.xml";
//        //String testCase = "tests/SpreadingFireTest.xml";
//        String testCase = "tests/WatorTest.xml";
//        File Testfile = new File(testCase);
//
//        String styleTestCase = "tests/StyleTest1.XML";
//        File styleFile = new File(styleTestCase);
//
//        var simulation = new setUpSimulation().setSimulation(Testfile, styleFile);
//        //var p = new XMLParser(Simulation.DATA_TYPE).getSimulation(file);
//        try{
//            myCurrentSimulation = simulation;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        GUI myGUI = new GUI(stage,myCurrentSimulation);
//        //myGUI.render();
//    }

    // kind of data files to look for
    public static final String DATA_FILE_EXTENSION = "*.xml";

    // NOTE: generally accepted behavior that the chooser remembers where user left it last
    private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);


    @Override
    public void start (Stage primaryStage) throws Exception {
        var dataFile = myChooser.showOpenDialog(primaryStage);
        var styleFile = myChooser.showOpenDialog(primaryStage);
        while (dataFile != null) {
            try {
                var simulation = new setUpSimulation().setSimulation(dataFile, styleFile);
                try{
                    myCurrentSimulation = simulation;
                }catch (Exception e){
                    e.printStackTrace();
                }
                //new Alert(Alert.INFORMATION, simulation.toString()).showAndWait();
            }
            catch (XMLException e) {
                //new Alert(Alert.ERROR, e.getMessage()).showAndWait();
            }
            dataFile = myChooser.showOpenDialog(primaryStage);
        }

        GUI myGUI = new GUI(primaryStage,myCurrentSimulation);
        // nothing selected, so quit the application
        //Platform.exit();
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


