import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GUI {
    private Stage myStage;
    private Simulation mySimulation;
    private Scene myScene;
    private Timeline myAnimation;
    private KeyFrame myFrame;
    private Group myNode;
    private XMLParser myParser = new XMLParser(Simulation.DATA_TYPE);

    private GUISimulationFactory myGUISimulationFactory;
    private GUIGrid myGUIGrid;
    private GUIDefaultPanel myGUIDefaultPanel;
    private GUISimulationPanel myGUISimulationPanel;
    GUIGridStep myStepFunction = new GUIGridStep() {
        @Override
        public void guiGridStep() {
            step();
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
        myGUISimulationPanel = new GUIGameOfLifePanel(sim);
        myFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        myScene = new Scene(myNode,STAGE_SIZE,STAGE_SIZE,BACKGROUND_COLOR);
        myStage.setScene(myScene);
        myStage.setTitle(STAGE_TITLE);
        myStage.show();
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(myFrame);
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
        if (myGUIDefaultPanel.needsToReset())
            resetSimulation();
        else {
            mySimulation.updateGrid();
            myGUIGrid.makeGUIGrid(mySimulation.getMyGrid().getCells());
        }
    }

    private void makeGUIParts(){
        myGUIGrid = new GUIGrid(mySimulation.getMyGrid().getNumRows(),mySimulation.getMyGrid().getNumCols());
        myGUIGrid.makeGUIGrid(mySimulation.getMyGrid().getCells());
        myGUIDefaultPanel = new GUIDefaultPanel(myStepFunction, myAnimation,myFrame,mySimulation.getMyName(),mySimulation.getMyGrid().getNumRows(),mySimulation.getMyGrid().getNumCols());
        myCredentials = new Credentials(mySimulation.getCredentials().get("title"),mySimulation.getCredentials().get("author"));
        myNode.getChildren().addAll(myGUIGrid.getGUIGrid(),myGUIDefaultPanel.getGUIDefaultPanel(),myCredentials.getMyCredentials());
    }
    private void resetSimulation(){
        String newSim = myGUIDefaultPanel.getSimName();
        File file = new File(myGUISimulationFactory.makeXMLFileName(newSim));
        var sim = myParser.getSimulation(file);
        try{
            mySimulation = sim;
        }catch (Exception e){
            e.printStackTrace();
        }
        myGUISimulationPanel = myGUISimulationFactory.makeSimulationPanel(newSim,mySimulation);
        myNode = new Group();
        myNode.getChildren().addAll(myGUISimulationPanel.getGUISimulationPanel());
        makeGUIParts();
        render();
    }



}
