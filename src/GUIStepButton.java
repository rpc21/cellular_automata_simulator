import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;

public class GUIStepButton extends GUIControlManager{

    private Button myButton = new Button();

    private static final String NAME = "Step";
    private GUIGridStep myGUIStepFunction;
    private Timeline myAnimation;

    public GUIStepButton(Timeline t, GUIGridStep f){
        myAnimation = t;
        myGUIStepFunction = f;
        super.setUpButton(myButton, NAME, e -> fire());
    }


    private void fire(){
        myAnimation.pause();
        myGUIStepFunction.guiGridStep();
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myButton);
        return myList;
    }
}
