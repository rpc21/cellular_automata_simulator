
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class GUIResetButton extends GUIControlManager{

    private Button myButton = new Button();

    private static final String NAME = "Reset";
    private GUIReset myResetFunction;
    private Timeline myAnimation;

    public GUIResetButton(Timeline t, GUIReset reset){
        myResetFunction = reset;
        myAnimation = t;
        super.setUpButton(myButton, NAME, e -> fire());
    }

    private void fire(){
        myAnimation.pause();
        myResetFunction.guiReset();
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myButton);
        return myList;
    }
}