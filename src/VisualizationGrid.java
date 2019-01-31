import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class VisualizationGrid {
    private int myRows;
    private int myCols;
    private GridPane myGridPane = new GridPane();

    private Stage myStage;
    private Scene myScene;
    private Timeline myAnimation;
    private KeyFrame myFrame;
    private Group myNode;



    private Simulation mySimulation;
    private DefaultPanel myDefaultPanel;

    private static final int FRAMES_PER_SECOND = 60;
    private static final double MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;
    private static final int STAGE_SIZE = 800;
    private static final String STAGE_TITLE = "Cellular Automata Simulation";
    private static final Color BACKGROUND_COLOR = Color.LIGHTSKYBLUE;
    private static final Color ALIVE = Color.LIGHTGREEN;
    private static final Color DEAD = Color.LIGHTPINK;
    private static final Color ON_FIRE = Color.RED;
    private static final Color TREE = Color.GREEN;
    private static final Color EMPTY = Color.WHITE;
    private static final Color BLOCKED = Color.DARKGRAY;
    private static final Color OPEN = Color.WHITE;
    private static final Color PERCOLATED = Color.DARKBLUE;

    public VisualizationGrid(Stage stage, Simulation sim ){
        myStage = stage;
        mySimulation = sim;
        myRows = mySimulation.getMyGrid().getNumRows();
        myCols = mySimulation.getMyGrid().getNumCols();
        myDefaultPanel = new DefaultPanel();

    }
    public DefaultPanel getMyDefaultPanel(){
        return myDefaultPanel;
    }


    public void start(){
        myFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        makeShapeGrid(mySimulation.getMyGrid().getGrid());
        myNode = new Group();
        myNode.getChildren().addAll(myGridPane,myDefaultPanel.getMyDefaultPanel());
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
        makeShapeGrid(mySimulation.getMyGrid().getGrid());
    }

    public Timeline getAnimation(){
        return myAnimation;
    }
    public void resetAnimation(double dur){
        myAnimation.stop();
        myFrame = new KeyFrame(Duration.millis(dur), e -> step());
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(myFrame);
        myAnimation.play();
    }


    public void makeShapeGrid(List<Cell> myCells){
        int r = 0;
        int c = 0;
        while (r <  myRows){
            while (c < myCols){
                Shape currShape = new Rectangle(CellularAutomataMain.SQUARE_LENGTH,CellularAutomataMain.SQUARE_LENGTH);
                if (myCells.get(r * myRows + c).getMyCurrentState() == 1200) {
                    currShape.setFill(ALIVE);
                }
                else if (myCells.get(r * myRows + c).getMyCurrentState() == 1201)
                    currShape.setFill(DEAD);
                else if (myCells.get(r * myRows + c).getMyCurrentState() == 140002)
                    currShape.setFill(ON_FIRE);
                else if (myCells.get(r * myRows + c).getMyCurrentState() == 140003)
                    currShape.setFill(TREE);
                else if (myCells.get(r * myRows + c).getMyCurrentState() == 140004)
                    currShape.setFill(EMPTY);
                else if (myCells.get(r * myRows + c).getMyCurrentState() == 0)
                    currShape.setFill(DEAD);
//                else if (myCells.get(r * myRows + c).getMyCurrentState() == 150004)
//                    currShape.setFill(BLOCKED);
//                else if (myCells.get(r * myRows + c).getMyCurrentState() == 150003)
//                    currShape.setFill(OPEN);
//                else if (myCells.get(r * myRows + c).getMyCurrentState() == 150002)
//                    currShape.setFill(PERCOLATED);
                currShape.setStroke(Color.BLACK);
                myGridPane.add(currShape, c, r);
                c = c + 1;
            }
            c = 0;
            r = r + 1;
        }
        myGridPane.setLayoutX(CellularAutomataMain.WINDOW_SIZE/2 - myRows * CellularAutomataMain.SQUARE_LENGTH/2);
        myGridPane.setLayoutY(CellularAutomataMain.WINDOW_SIZE/2 - myCols * CellularAutomataMain.SQUARE_LENGTH/2);
    }






}
