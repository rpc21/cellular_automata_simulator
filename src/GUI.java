import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;


public class GUI {
    private Stage myStage;
    private Simulation mySimulation;
    private Scene myScene;
    private Timeline myAnimation;
    private KeyFrame myFrame;
    private Group myNode;

    private GUISimulationFactory myGUISimulationFactory;
    private SimulationFactory mySimFact = new SimulationFactory();
    private XMLParser myNewSimFact;
    private GUIGrid myGUIGrid;
    private GUIDefaultPanel myGUIDefaultPanel;
    private GUISimulationPanel myGUISimulationPanel;
    GUIGridStep myStepFunction = new GUIGridStep() {
        @Override
        public void guiGridStep() {
            step();
        }
    };
    GUIReset myResetFunction = new GUIReset() {
        @Override
        public void guiReset() {
            resetWithParams();
        }
    };
    private Credentials myCredentials;

    public static final int STAGE_SIZE = 1000;
    private static final String STAGE_TITLE = "Cellular Automata Simulation";
    private static final Color BACKGROUND_COLOR = Color.LIGHTSKYBLUE;
    private static final int FRAMES_PER_SECOND = 60;
    private static final double MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;

    public GUI(Stage s, Simulation sim){
        myStage = s;
        mySimulation = sim;
        myNode = new Group();
        myGUISimulationFactory = new GUISimulationFactory();
        render();
        makeGUIParts();
    }
    public void render(){
        myFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        myScene = new Scene(myNode,STAGE_SIZE,STAGE_SIZE,BACKGROUND_COLOR);
        myStage.setScene(myScene);
        myStage.setTitle(STAGE_TITLE);
        myStage.show();
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(myFrame);
    }

    public void step(){
            mySimulation.updateGrid();
            myGUIGrid.makeGUIGrid(mySimulation.getMyGrid().getCells());
        }

    public void resetWithParams(){
        if (myGUISimulationPanel.getName().equals(myGUIDefaultPanel.getSimName()))
            mySimulation = mySimFact.generateSimulation(myGUIDefaultPanel.getMyBasicParams(), myGUISimulationPanel.getMyParams());
        else {
            File file;
            if (!myGUIDefaultPanel.getSimName().equals(GOLSimulation.GOL_SIMULATION_NAME))
                file = new File("tests/" + myGUIDefaultPanel.getSimName().replaceAll(" ","") + "Test.xml");
            else
                file = new File("tests/GOLTest.xml");
            var p = new XMLParser(Simulation.DATA_TYPE).getSimulation(file);
            try {
                mySimulation = p;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        myNode = new Group();
        makeGUIParts();
        render();
    }

//    public void resetWithoutParams(){
//        File file = new File(myGUIDefaultPanel.getSimName());
//        var p = new XMLParser(Simulation.DATA_TYPE).getSimulation(file);
//        try{
//            mySimulation = p;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        myNode = new Group();
//        makeGUIParts();
//        render();
//    }

    private void makeGUIParts(){
        myGUIGrid = new GUIGrid(mySimulation.getMyGrid().getNumRows(),mySimulation.getMyGrid().getNumCols());
        myGUIGrid.makeGUIGrid(mySimulation.getMyGrid().getCells());
        myGUISimulationPanel = myGUISimulationFactory.makeSimulationPanel(mySimulation.getMyName(), mySimulation);
        myGUIDefaultPanel = new GUIDefaultPanel(myStepFunction, myResetFunction,myAnimation,myFrame,mySimulation.getMyName(),mySimulation.getMyGrid().getNumRows(),mySimulation.getMyGrid().getNumCols());
        myCredentials = new Credentials(mySimulation.getMyName(),"Dima, Ryan, and Anna");
        myNode.getChildren().addAll(myGUIGrid.getGUIGrid(),myGUIDefaultPanel.getGUIDefaultPanel(),myCredentials.getMyCredentials(),myGUISimulationPanel.getGUISimulationPanel());
    }



}
