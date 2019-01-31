import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Control;


import java.util.HashSet;

public class DefaultPanel {
    private StackPane myStackPane;
    private HashSet<DefaultControl> myDefaultControls;
    private static final int DEFAULT_CONTROL_OFFSET = 600;
    public static final int DEFAULT_CONTROL_SPACING = 40;

    public DefaultPanel(){
        myStackPane = new StackPane();
        myDefaultControls = new HashSet<>();

    }
    public void addControl(Control c, double y){
        myStackPane.getChildren().addAll(c);
        c.setLayoutX(DEFAULT_CONTROL_OFFSET);
        c.setLayoutY(y);
    }

    public StackPane getMyDefaultPanel(){
        return myStackPane;
    }

    public void makeButtons(VisualizationGrid myGrid){
        DefaultPlayButton myPB = new DefaultPlayButton(myGrid);
        DefaultStepButton mySB = new DefaultStepButton(myGrid);
        DefaultSpeedSlider mySS = new DefaultSpeedSlider(myGrid);
        myDefaultControls.add(myPB);
        myDefaultControls.add(mySS);
        myDefaultControls.add(mySB);
        setUpStackPane();

    }
    private void setUpStackPane(){
        int iter = 1;
        for(DefaultControl c: myDefaultControls) {
            myStackPane.getChildren().addAll(c.getMyControl());
            myStackPane.setLayoutX(DEFAULT_CONTROL_OFFSET);
            c.getMyControl().setTranslateY(iter * DEFAULT_CONTROL_SPACING);
            myStackPane.setLayoutY(iter * DEFAULT_CONTROL_SPACING);
            iter++;
        }


    }

}
