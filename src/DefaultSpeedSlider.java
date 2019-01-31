import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.util.Duration;

public class DefaultSpeedSlider extends DefaultControl{
    private Slider mySlider;
    private VisualizationGrid myVisGrid;

    private static final int FRAMES_PER_SECOND = 60;
    private static final int MY_Y_COORDINATE = 130;
    public DefaultSpeedSlider(VisualizationGrid grid){
        super(grid,MY_Y_COORDINATE);
        mySlider = new Slider(-17000,-3000,-10000);
        mySlider.setLayoutY(3* DefaultPanel.DEFAULT_CONTROL_SPACING);
        myVisGrid = grid;
    }
    public void update(){
            myVisGrid.resetAnimation(mySlider.getValue() * -1.0/FRAMES_PER_SECOND);
    }
    public Control getMyControl(){
        return mySlider;
    }

}
