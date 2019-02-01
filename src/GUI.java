import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
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
    private Group myNode = new Group();
    private XMLParser myParser = new XMLParser(Simulation.DATA_TYPE);

    private GUIGrid myGUIGrid;
    private GUIDefaultPanel myGUIDefaultPanel;
    private GUISimulationPanel mySimulationPanel;
    private Button myPlayButton;
    private Button myStepButton;
    private Slider mySpeedSlider;
    private ChoiceBox<String> myChoiceBox;

    public static final int STAGE_SIZE = 800;
    private static final String STAGE_TITLE = "Cellular Automata Simulation";
    private static final Color BACKGROUND_COLOR = Color.LIGHTSKYBLUE;
    private static final int FRAMES_PER_SECOND = 60;
    private static final double MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;

    public GUI(Stage s, Simulation sim){
        myStage = s;
        mySimulation = sim;
        mySimulationPanel = new GUISimulationPanel(sim);
        myNode = new Group();
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
        myGUIGrid.updateGUIGrid(mySimulation.getMyGrid().getCells());
    }

    private void makeGUIParts(){
        myGUIGrid = new GUIGrid(mySimulation.getMyGrid().getNumRows(),mySimulation.getMyGrid().getNumCols());
        myGUIGrid.updateGUIGrid(mySimulation.getMyGrid().getCells());
        makeControls();
        myGUIDefaultPanel = new GUIDefaultPanel(myPlayButton,myStepButton,mySpeedSlider,myChoiceBox);
        myNode.getChildren().addAll(myGUIGrid.getGUIGrid(),myGUIDefaultPanel.getGUIDefaultPanel());
    }

    private void makeControls(){
        makePlayButton();
        makeStepButton();
        makeSpeedSlider();
        makeSimulationDropDownMenu();
    }

    private void makePlayButton() {
        myPlayButton = new Button("Play");
        myPlayButton.setLayoutY(1 * GUIDefaultPanel.DEFAULT_CONTROL_SPACING);
        myPlayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (myAnimation.getStatus() == Animation.Status.RUNNING)
                    myAnimation.pause();
                else {
                    myAnimation.play();
                }
            }
        });
    }
    private void makeStepButton(){
        myStepButton = new Button("Step");
        myStepButton.setLayoutY(2* GUIDefaultPanel.DEFAULT_CONTROL_SPACING);
        myStepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                step();
            }
        });
    }

    private void makeSpeedSlider(){
        mySpeedSlider = new Slider(-17000,-3000,-10000);
        mySpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                mySpeedSlider.setValue(mySpeedSlider.getValue());
                resetAnimation(mySpeedSlider.getValue(),myAnimation.getStatus() == Animation.Status.RUNNING);
            }
        });
    }

    private void resetAnimation(double duration, boolean isPlaying){
        myAnimation.stop();
        myFrame = new KeyFrame(Duration.millis(duration), e -> step());
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(myFrame);
        if (isPlaying)
            myAnimation.play();
    }

    private void makeSimulationDropDownMenu(){
        myChoiceBox = new ChoiceBox<>();
        myChoiceBox.getItems().addAll("Game of Life", "Spreading Fire", "Percolation");
        myChoiceBox.setValue("Game of Life");
        myChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                resetSimulation(myChoiceBox.getItems().get((Integer) number2));
            }
        });

    }

    private void resetSimulation(String newSim){
        System.out.println(newSim);
        myChoiceBox.setValue(newSim);
        String testCase = "";
        switch(newSim){
            case "Game of Life":
                testCase = "tests/GOLTest.xml";
                break;
            case "Spreading Fire":
                testCase = "tests/SpreadingFireTest.xml";
                break;
            case "Percolation":
                testCase = "tests/PercolationTest.xml";
                break;
            default:
                testCase = "tests/GOLTest.xml";
                break;
        }

        File file = new File(testCase);
        var sim = myParser.getSimulation(file);
        try{
            mySimulation = sim;
        }catch (Exception e){
            e.printStackTrace();
        }
        myNode = new Group();
        if (mySimulation instanceof SpreadingFireSimulation) {
            System.out.println("hi");
            mySimulationPanel = new GUISpreadingFirePanel(mySimulation);
        }
        else
            mySimulationPanel = new GUISimulationPanel(sim);
        myNode.getChildren().addAll(mySimulationPanel.getGUISimulationPanel());
        makeGUIParts();
        render();
    }


}
