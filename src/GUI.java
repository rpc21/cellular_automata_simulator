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
import javafx.scene.text.Text;
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

    private GUISimulationFactory myGUISimulationFactory;
    private GUIGrid myGUIGrid;
    private GUIDefaultPanel myGUIDefaultPanel;
    private GUISimulationPanel myGUISimulationPanel;
    private Button myPlayButton;
    private Button myStepButton;
    private Slider mySpeedSlider;
    private Text mySpeedLabel;
    private ChoiceBox<String> myChoiceBox;

    public static final int STAGE_SIZE = 800;
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

    private void makeGUIParts(){
        myGUIGrid = new GUIGrid(mySimulation.getMyGrid().getNumRows(),mySimulation.getMyGrid().getNumCols());
        myGUIGrid.makeGUIGrid(mySimulation.getMyGrid().getCells());
        makeControls();
        myGUIDefaultPanel = new GUIDefaultPanel(myPlayButton,myStepButton,mySpeedLabel,mySpeedSlider,myChoiceBox);
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
        myStepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                step();
            }
        });
    }

    private void makeSpeedSlider(){
        mySpeedLabel = new Text("Animation Speed");
        mySpeedSlider = new Slider(-17000,-3000,-10000);
        mySpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                mySpeedSlider.setValue(mySpeedSlider.getValue());
                resetAnimation(mySpeedSlider.getValue() * -1/FRAMES_PER_SECOND,myAnimation.getStatus() == Animation.Status.RUNNING);
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
        myChoiceBox.getItems().addAll("Game of Life", "Spreading Fire", "Percolation", "Segregation", "Predator-Prey");
        myChoiceBox.setValue("Game of Life");
        myChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                System.out.println(myChoiceBox.getItems().get((Integer) number2));
                myAnimation.stop();
                myChoiceBox.setValue(myChoiceBox.getItems().get((Integer) number2));
                resetSimulation(myChoiceBox.getItems().get((Integer) number2));
            }
        });

    }

    private void resetSimulation(String newSim){
        myChoiceBox.setValue(newSim);
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
