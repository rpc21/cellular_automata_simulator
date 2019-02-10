import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GUISpeedSlider extends GUIControlManager{
    private static final String NAME = "Animation Speed";

    private static final double LOW_SPEED = -17000;
    private static final double DEFAULT_SPEED = -10000;
    private static final double HIGH_SPEED = -3000;
    private static final double FRAMES_PER_SECOND = 60;

    private Timeline myAnimation;
    private KeyFrame myFrame;
    private GUIGridStep myStepFunction;
    private Text mySpeedSliderLabel = new Text(NAME);
    private Slider mySlider = new Slider();

    public GUISpeedSlider(Timeline a, KeyFrame f, GUIGridStep step){
        myAnimation = a;
        myFrame = f;
        myStepFunction = step;
        super.setUpLabel(mySpeedSliderLabel);
        super.setUpSlider(mySlider, DEFAULT_SPEED,LOW_SPEED,HIGH_SPEED, e -> fire());
    }

    private void fire(){
        mySlider.setValue(mySlider.getValue());
        resetAnimation(mySlider.getValue() * -1/FRAMES_PER_SECOND);
    }

    private void resetAnimation(double duration){
        myAnimation.stop();
        myFrame = new KeyFrame(Duration.millis(duration), e -> myStepFunction.guiGridStep());
        myAnimation.getKeyFrames().setAll(myFrame);
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(mySpeedSliderLabel);
        myList.add(mySlider);
        return myList;
    }
}
