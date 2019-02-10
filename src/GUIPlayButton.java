import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class GUIPlayButton extends GUIControlManager{

    private Button myButton = new Button();
    private Timeline myAnimation;

    private static final String NAME = "Play/Pause";

    public GUIPlayButton(Timeline t){
        myAnimation = t;
        super.setUpButton(myButton, NAME, e -> fire());
    }


    private void fire(){
        if (myAnimation.getStatus() == Animation.Status.RUNNING)
            myAnimation.pause();
        else {
            myAnimation.play();
        }
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myButton);
        return myList;
    }
}
