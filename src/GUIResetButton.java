
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
    /**
     * Pauses animation and resets the GUI by invoking GUI's resetSimulation method
     * @see GUIReset
     */
    private void fire(){
        myAnimation.pause();
        myResetFunction.guiReset();
    }
    /**
     * This getter method was necessary to add the node that allows the user to access the play button
     * type
     * @see Node
     * @return myList which is a list of nodes necessary for the user to reset the simulation
     */
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myButton);
        return myList;
    }
}