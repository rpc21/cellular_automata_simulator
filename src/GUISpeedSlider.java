import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
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
    /**
     * Sets slider to mouse-dragged value and calls helper method to understand new inputted value. Larger values imply a longer
     * time gap between updates but spinners are limited to allowing a left-to-right increasing range. To resolve this,
     * this method multiplies the inputted value by negative one, so dragging to the right increases the speed of the animation
     * @see Timeline
     * @see Slider
     * @see KeyFrame
     */
    private void fire(){
        mySlider.setValue(mySlider.getValue());
        resetAnimation(mySlider.getValue() * -1.0/FRAMES_PER_SECOND);
    }

    private void resetAnimation(double duration){
        myAnimation.stop();
        myFrame = new KeyFrame(Duration.millis(duration), e -> myStepFunction.guiGridStep());
        myAnimation.getKeyFrames().setAll(myFrame);
    }
    /**
     * This getter method was necessary to add the node that allows the user to access the speed slider
     * type
     * @see Node
     * @return myList which is a list of nodes necessary for the user to change the speed of the animation
     */
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(mySpeedSliderLabel);
        myList.add(mySlider);
        return Collections.unmodifiableList(myList);
    }
}
