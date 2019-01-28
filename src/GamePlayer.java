import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GamePlayer extends Application{
    public static final int SIZE = 500;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Simulation currentSimulation;



    /**
     * Initialize the stageManager and switch scenes to load the Main Screen of the application
     * Establish the animation loop
     */
    @Override
    public void start (Stage stage) {

        currentSimulation = new GOLSimulation(5,5);
        int[][] golTestStates = {{0, 1, 1 ,0 , 1},
                {1, 1, 0, 1, 0},
                {0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1}
                                };
        for (int i = 0; i < golTestStates.length; i++){
            for (int j = 0; j < golTestStates[0].length; j++){
                golTestStates[i][j] = golTestStates[i][j] + 1200;
            }
        }
        ((GOLSimulation) currentSimulation).setInitialStates(golTestStates);

        for (int i = 0; i < 10; i++){
            System.out.println("Iteration: "+i+"---------------------------");
            currentSimulation.updateGrid();

        }

        //attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }

    private void step(double secondDelay) {

    }

}
