import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    public static final int STAGE_SIZE = 600;
    public static final double SCALE = 0.60;
    private static final String STAGE_TITLE = "Cellular Automata Simulation";
    private static final Color BACKGROUND_COLOR = Color.LIGHTSKYBLUE;
    private static final int FRAMES_PER_SECOND = 60;
    private static final double MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;

    public GUI(Stage stage, Simulation sim){
        myStage = stage;
        mySimulation = sim;
        myNode = new Group();
        myManager.addSimulation(sim,myNode,stage);
        render();
        makeDefaultPanel(sim);
        makeGUIParts(sim);

    /**
     * sets up the default window for the GUI and initializes variables necessary to manipulate the timeline. Scale
     * parameters were added to make it easier to adapt the window to fit different screen sizes
     * @see Stage
     * @see Timeline
     */
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

        Scale scale = new Scale(SCALE, SCALE);
        scale.setPivotX(0);
        scale.setPivotY(0);
        myScene.getRoot().getTransforms().setAll(scale);
    }


    /**
     * Updates every GUI part currently being handled by the manager and displays the changes in the graph
     * @see GUIManager
     * @see GUIGraph
     * @see Simulation
     */
    private void step(){
        myManager.updateGUIParts();
        myGUIGraph.updateChart(mySimulation.getMyGrid().getCells());
    }

    private void addSimulation(){
        myManager.addSimulation(mySimulation,myNode,myStage);
    }

    private void removeSimulation(){
        myManager.removeSimulation(myNode);
    }

    /**
     * Clears the current root of the scene in preparation for a simulation with new parameters. The manager handles whether
     * this involves a new simulation or simply changes backend parameters. Once the new GUI pieces are ready, they are added
     * back into the root of the scene
     * @see GUIManager
     * @see GUIGraph
     * @see GUIDefaultPanel
     */
    private void resetWithParams() {

        myNode.getChildren().clear();
        myManager.resetSimulations(myGUIDefaultPanel,mySimulation.getCredentials());
        mySimulation = myManager.getPrimarySimulation();
        makeGUIParts(mySimulation);
        for (GUIGrid grid : myManager.getGrids()) {
            myNode.getChildren().add(grid.getGUIGrid());
            myNode.getChildren().add(grid.getGUIStyle());
        }
        for (GUISimulationPanel simPanel : myManager.getPanels())
            myNode.getChildren().add(simPanel.getGUISimulationPanel());
    }

    /**
     * Makes the GUI parts that are not part of a larger structure (the graph and credentials), and adds in the default panel here
     * so the default panel remains constant throughout most other changes the user may input into the GUI
     * @see Credentials
     * @see GUIGraph
     * @see GUIDefaultPanel
     */
    private void makeGUIParts(Simulation currSim){
        Credentials myCredentials = new Credentials(mySimulation.getCredentials().get(Simulation.TITLE_CREDENTIAL),
                mySimulation.getCredentials().get(Simulation.AUTHOR_CREDENTIAL));
        myGUIGraph = new GUIGraph(currSim);
        myNode.getChildren().addAll(myGUIDefaultPanel.getGUIDefaultPanel(), myCredentials.getMyCredentials(),myGUIGraph.getMyChart());
    }

    /**
     * Initializes the default panel with references to functions it may need from GUI, such as adding and removing a simulation,
     * access to time-sensitive variables, and the current simulation's state. Sets up the layout of the default panel with
     * respect to other parts of the GUI
     * @see GUIManager
     * @see GUIDefaultPanel
     */
    private void makeDefaultPanel(Simulation currSim){
        myGUIDefaultPanel = new GUIDefaultPanel(myStepFunction, myResetFunction, myAddSimFunction,myRemoveSimFunction, myAnimation,myFrame,
                currSim.getMyName(),currSim.getMyGrid().getNumRows(),currSim.getMyGrid().getNumCols());
        myGUIDefaultPanel.getGUIDefaultPanel().setLayoutX(myManager.getGrids().get(0).getGUIGrid().getLayoutX() +
                myManager.getGrids().get(0).getGUIGrid().getTranslateX() + GUIGrid.GUI_GRID_SIZE + 40);
        myGUIDefaultPanel.getGUIDefaultPanel().setLayoutY(myManager.getGrids().get(0).getGUIGrid().getLayoutY());
        myGUIDefaultPanel.getGUIDefaultPanel().setTranslateY(myManager.getGrids().get(0).getGUIGrid().getTranslateY());
    }



}
