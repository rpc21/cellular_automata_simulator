import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.Collections;
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
    /**
     * Pauses animation and update the grid by one iteration
     * type
     * @see GUIGridStep
     */
    private void fire(){
        myAnimation.pause();
        myGUIStepFunction.guiGridStep();
    }
    /**
     * This getter method was necessary to add the node that allows the user to access the step button
     * type
     * @see Node
     * @return myList which is a list of nodes necessary for the user to step through the animation
     */
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myButton);
        return Collections.unmodifiableList(myList);
    }
}
