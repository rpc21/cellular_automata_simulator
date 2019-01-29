import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import com.sun.javafx.scene.layout.*;
import javafx.util.Duration;


import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;


/**
 * Class to kick off the Wator World simulation and GUI.
 */
public class CellularAutomataMain extends Application {
    Stage window;
    Scene scene1;
    boolean isPlaying;
    private Group node = new Group();
    private GridPane myGridPane = new GridPane();
    private static final Color ALIVE = Color.GREEN;
    private static final Color DEAD = Color.RED;
    private static final int TRIANGLE_LENGTH = 50;
    private static final int SQUARE_LENGTH = 30;
    private static final int GRID_ROWS = 10;
    private static final int GRID_COLS = 10;
    private static final int FRAMES_PER_SECOND = 60;
    private static final double MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Slider myAnimationSpeedSlider = new Slider(5000,15000,10000);
    private Timeline animation;
    private KeyFrame frame;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));

        Rectangle[][] myGrid = getGrid(0.5);
        makeGridPane(myGrid);

        Button myPlayButton = new Button("Play");
        Button myStepButton = new Button("Step");


        myPlayButton.setLayoutX(GRID_ROWS * SQUARE_LENGTH + 50);
        myStepButton.setLayoutX(GRID_ROWS * SQUARE_LENGTH + 50);
        myStepButton.setLayoutY(3* SQUARE_LENGTH);
        myAnimationSpeedSlider.setLayoutX(GRID_ROWS * SQUARE_LENGTH + 50);
        myAnimationSpeedSlider.setLayoutY(6*SQUARE_LENGTH);

        node.getChildren().addAll(myGridPane,myPlayButton,myStepButton,myAnimationSpeedSlider);
        scene1 = new Scene(node,SQUARE_LENGTH * (GRID_ROWS + 8),SQUARE_LENGTH * (GRID_ROWS + 4));

        stage.setScene(scene1);
        stage.setTitle("Game of Life");
        stage.show();
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        isPlaying = false;

        myPlayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isPlaying)
                    animation.pause();
                else {

                    frame = new KeyFrame(Duration.millis(myAnimationSpeedSlider.getValue()/FRAMES_PER_SECOND), e -> step(SECOND_DELAY));
//                    animation.getKeyFrames().add(frame);
                    animation.play();
                }
                isPlaying = !isPlaying;
            }
        });
        myStepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                step(SECOND_DELAY);
            }
        });


    }

    private void step(double elapsedTime){
        Rectangle[][] myGrid = getGrid(0.5);
        makeGridPane(myGrid);
        myGridPane.getChildren().clear();
        for (int r = 0; r < GRID_ROWS; r++) {
            for (int c = 0; c < GRID_COLS; c++) {
                myGridPane.add(new Circle(10,Color.PURPLE), r, c);
            }
        }




    }

    private void makeGridPane(Rectangle[][] shapeGrid){
        myGridPane.getChildren().clear();
        for (int r = 0; r < GRID_ROWS; r++) {
            for (int c = 0; c < GRID_COLS; c++) {
                myGridPane.add(shapeGrid[r][c], r, c);
            }
        }
    }
    private static Rectangle[][] getGrid(double proportionDead){
        Rectangle[][] myGrid = new Rectangle[GRID_ROWS][GRID_COLS];
        for (int r = 0; r < GRID_ROWS; r++){
            for (int c = 0; c < GRID_COLS; c++) {
                myGrid[r][c] = new Rectangle(SQUARE_LENGTH, SQUARE_LENGTH);
                if (Math.random() < proportionDead)
                    myGrid[r][c].setFill(DEAD);
                else
                    myGrid[r][c].setFill(ALIVE);
                myGrid[r][c].setStroke(Color.BLACK);
            }
        }
        return myGrid;
    }

}