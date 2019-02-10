import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.HashMap;

public class GUI {
    private Stage myStage;
    private Simulation mySimulation;
    private Timeline myAnimation;
    private KeyFrame myFrame;
    private Group myNode;
    private GUIManager myManager = new GUIManager();
    private GUIDefaultPanel myGUIDefaultPanel;
    private GUIGraph myGUIGraph;

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
    GUIAddSimulation myAddSimFunction = new GUIAddSimulation() {
        @Override
        public void guiAddSim() {
            addSimulation();
        }
    };
    GUIRemoveSimulation myRemoveSimFunction = new GUIRemoveSimulation() {
        @Override
        public void guiRemoveSim() {
            removeSimulation();
        }
    };



    public static final int STAGE_SIZE = 1000;
    private static final String STAGE_TITLE = "Cellular Automata Simulation";
    private static final Color BACKGROUND_COLOR = Color.LIGHTSKYBLUE;
    private static final int FRAMES_PER_SECOND = 60;
    private static final double MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;

    public GUI(Stage s, Simulation sim){
        myStage = s;
        mySimulation = sim;
        myNode = new Group();
        myManager.addSimulation(sim,myNode,s);
        render();
        makeDefaultPanel(sim);
        makeGUIParts(sim);
    }
    private void render(){
        myFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        Scene myScene = new Scene(myNode,STAGE_SIZE,STAGE_SIZE,BACKGROUND_COLOR);
        myStage.setScene(myScene);
        myStage.setTitle(STAGE_TITLE);
        myStage.show();
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(myFrame);
    }

    public void step(){
        myManager.updateGUIParts();
        //myGUIGraph.updateChart(mySimulation.getMyGrid().getCells());
        }

    private void addSimulation(){
        myManager.addSimulation(mySimulation,myNode,myStage);
    }

    private void removeSimulation(){
        myManager.removeSimulation(myNode);
    }


    public void resetWithParams() {

        myNode.getChildren().clear();
        myManager.resetSimulations(myGUIDefaultPanel);
        mySimulation = myManager.getPrimarySimulation();
        makeGUIParts(mySimulation);
        for (GUIGrid grid : myManager.getGrids()) {
            myNode.getChildren().add(grid.getGUIGrid());
            myNode.getChildren().add(grid.getGUIStyle());
        }
        for (GUISimulationPanel simPanel : myManager.getPanels())
            myNode.getChildren().add(simPanel.getGUISimulationPanel());
    }

//    public void setParameters(){
//        //pass back changes to neighbors,shapes,gridedge etc
//        myManager.updateGUIGrid(new HashMap<String, String>());
//    }


    private void makeGUIParts(Simulation currSim){
        Credentials myCredentials = new Credentials("lol","hi");
        myGUIGraph = new GUIGraph(currSim);
        myNode.getChildren().addAll(myGUIDefaultPanel.getGUIDefaultPanel(), myCredentials.getMyCredentials(),myGUIGraph.getMyChart());
    }

    private void makeDefaultPanel(Simulation currSim){
        myGUIDefaultPanel = new GUIDefaultPanel(myStepFunction, myResetFunction, myAddSimFunction,myRemoveSimFunction, myAnimation,myFrame,
                currSim.getMyName(),currSim.getMyGrid().getNumRows(),currSim.getMyGrid().getNumCols());
        myGUIDefaultPanel.getGUIDefaultPanel().setLayoutY(myManager.getGrids().get(0).getGUIGrid().getLayoutY());
        myGUIDefaultPanel.getGUIDefaultPanel().setTranslateY(myManager.getGrids().get(0).getGUIGrid().getTranslateY());
    }



}
