import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIGridBoundriesChooser extends GUIControlManager{
    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private static final String BORDER_ON = "yes";
    private static final String BORDER_OFF = "no";

    private List<Object> possibleBoundries = Arrays.asList(BORDER_ON, BORDER_OFF);

    public GUIGridBoundriesChooser(String initialBoundry){
        super.setUpChoiceBox(myChooser, initialBoundry, possibleBoundries);
    }
    /**
     * This getter method was necessary to add the node that allows the user to access a list of boundary options
     * to the root of the scene in GUI
     * type
     * @see Node
     * @return myList which is a list of nodes necessary for the user to understand and change values for the grid cells'
     * outline types
     */
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myChooser);
        return myList;
    }
    /**
     * This getter method for the value of the choicebox was necessary to determine whether or not to draw a border around
     * the cells
     * @return boolean true if black outline, false if cells shouldn't have notable outline
     */
    public boolean determineStroke(){
        return (myChooser.getValue().toString().equals(BORDER_ON));
    }
}
