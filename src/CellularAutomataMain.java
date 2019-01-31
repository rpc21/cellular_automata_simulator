import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;



public class CellularAutomataMain extends Application {
    Stage window;
    Scene scene1;
    boolean isPlaying;
    private int grid_rows;
    private int grid_cols;
    String currTestFile = "tests/GOLTest1.txt";
    Button myStepButton;
    Button myPlayButton;
    Slider myAnimateSpeedSlider;
    ChoiceBox<String> mySimulationChooser;
    private VisualizationGrid myVisGrid;
    private Group node = new Group();
    private GridPane myGridPane = new GridPane();
    public static final int WINDOW_SIZE = 800;
    public static final int SQUARE_LENGTH = 80;
    private static final int FRAMES_PER_SECOND = 60;
    private static final double MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;
    private static final Color BACKGROUND_COLOR = Color.LIGHTSKYBLUE;
    private static final String GOLTESTFILE = "tests/GOLTest1.txt";
    private static final String SPREADINGFIREFILE = "tests/SpreadingFire1.txt";
    private static final String PERCOLATIONFILE = "tests/PercolationTest1.txt";
    private Timeline animation;
    private KeyFrame frame;
    private Simulation myCurrentSimulation;

    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        window = stage;
        Initialize init = new Initialize(currTestFile);
        myCurrentSimulation = init.getSimulation();
//        grid_rows = myCurrentSimulation.getMyGrid().getNumRows();
//        grid_cols = myCurrentSimulation.getMyGrid().getNumCols();
        myVisGrid = new VisualizationGrid(stage,myCurrentSimulation);
        myVisGrid.getMyDefaultPanel().makeButtons(myVisGrid);
        myVisGrid.start();//*******
//        frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
//
//        myVisGrid.makeShapeGrid(myCurrentSimulation.getMyGrid().getGrid(), myGridPane);
//
//        makeButtons();
//        node = new Group();
//        node.getChildren().addAll(myGridPane,myStepButton,myPlayButton,myAnimateSpeedSlider,mySimulationChooser);
//        scene1 = new Scene(node,WINDOW_SIZE,WINDOW_SIZE,BACKGROUND_COLOR);
//        window.setScene(scene1);
//        window.setTitle("Cellular Automata");
//        window.show();
//        animation = new Timeline();
//        animation.setCycleCount(Timeline.INDEFINITE);
//        animation.getKeyFrames().add(frame);
//        isPlaying = false;
    }
//    private void step(){
//        myCurrentSimulation.updateGrid();
//        List<Cell> myCurrGrid = myCurrentSimulation.getMyGrid().getGrid();
//        myVisGrid.makeShapeGrid(myCurrGrid,myGridPane);
//        updateAnimateSpeed();
//    }
//
//
//
//    private void makeButtons(){
//        makePlayButton(myGridPane.getLayoutX() + grid_rows * SQUARE_LENGTH + 10, myGridPane.getLayoutY());
//        makeStepButton(myGridPane.getLayoutX() + grid_rows * SQUARE_LENGTH + 10, myGridPane.getLayoutY() + SQUARE_LENGTH * .5);
//        makeAnimateSpeedSlider(myGridPane.getLayoutX() + grid_rows * SQUARE_LENGTH + 10, myGridPane.getLayoutY() + SQUARE_LENGTH * 1.0);
//        makeSimulationDropDownMenu(myGridPane.getLayoutX() + grid_rows * SQUARE_LENGTH + 10, myGridPane.getLayoutY() + SQUARE_LENGTH * 1.5);
//    }
//
//    private void makePlayButton(double x, double y){
//        myPlayButton = new Button("Play");
//        myPlayButton.setLayoutX(x);
//        myPlayButton.setLayoutY(y);
//        myPlayButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if (isPlaying)
//                    animation.pause();
//                else {
//                    frame = new KeyFrame(Duration.millis(myAnimateSpeedSlider.getValue() * -1/FRAMES_PER_SECOND), e -> step());
//                    animation.play();
//                }
//                isPlaying = !isPlaying;
//            }
//        });
//    }
//
//    private void makeStepButton(double x, double y){
//        myStepButton = new Button("Step");
//        myStepButton.setLayoutX(x);
//        myStepButton.setLayoutY(y);
//        myStepButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                //myPlayButton.fire();
//                step();
//            }
//        });
//    }
//
//    private void makeAnimateSpeedSlider(double x,double y){
//        myAnimateSpeedSlider = new Slider(-17000,-3000,-10000);
//        myAnimateSpeedSlider.setLayoutX(x);
//        myAnimateSpeedSlider.setLayoutY(y);
//
//    }
//
//
//    private void updateAnimateSpeed(){
//        animation.stop();
//        frame = new KeyFrame(Duration.millis(myAnimateSpeedSlider.getValue() * -1.0/FRAMES_PER_SECOND), e -> step());
//        animation = new Timeline();
//        animation.setCycleCount(Timeline.INDEFINITE);
//        animation.getKeyFrames().add(frame);
//        if (isPlaying)
//            animation.play();
//    }
//
//
//    private void makeSimulationDropDownMenu(double x,double y){
//        mySimulationChooser = new ChoiceBox<>();
//        mySimulationChooser.getItems().addAll("Game of Life", "Spreading Fire", "Percolation");
//        if (currTestFile == GOLTESTFILE)
//            mySimulationChooser.setValue("Game of Life");
//        else if (currTestFile == SPREADINGFIREFILE)
//            mySimulationChooser.setValue("Spreading Fire");
//        else if (currTestFile == PERCOLATIONFILE)
//            mySimulationChooser.setValue("Percolation");
//        mySimulationChooser.setLayoutX(x);
//        mySimulationChooser.setLayoutY(y);
//        mySimulationChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
//                resetSimulation(mySimulationChooser.getItems().get((Integer) number2));
//            }
//        });
//
//    }
//    private void resetSimulation(String mySim){
//        animation.stop();
//        mySimulationChooser.setValue(mySim);
//        if (mySim.equals("Game of Life")){
//            currTestFile = GOLTESTFILE;
//        }
//        else if (mySim.equals("Spreading Fire")){
//            currTestFile = SPREADINGFIREFILE;
//        }
//        else if (mySim.equals("Percolation"))
//            currTestFile = PERCOLATIONFILE;
//        start(window);
//
//
//    }
}


