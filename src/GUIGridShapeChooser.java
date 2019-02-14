
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIGridShapeChooser extends GUIControlManager{

    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private List<Object> possibleShapes = Arrays.asList("rectangle", "triangle", "hexagon");


    public GUIGridShapeChooser(String initialShape){
        super.setUpChoiceBox(myChooser, initialShape, possibleShapes);
    }
    /**
     * This getter method was necessary to add the node that allows the user to access a list of shape options
     * to the root of the scene in GUI
     * type
     * @see Node
     * @return myList which is a list of nodes necessary for the user to understand and change values for the grid cells'
     * shape
     */
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myChooser);
        return myList;
    }
    /**
     * This getter method was necessary to get the current shape the user had just input in order to properly update
     * the GUI grid on its next iteration of updates
     * @return String name of the shape meant to be displayed
     */
    public String getShape(){
        return myChooser.getValue().toString();
    }
}