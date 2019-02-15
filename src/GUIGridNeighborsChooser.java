import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GUIGridNeighborsChooser extends GUIControlManager{
    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private List<Object> possibleNeighbors = List.of("ADJACENT", "BOX_NEIGHBORS", "DIAGONAL", "KNIGHT", "I_FORMATION",
            "TRIANGLE_12_POINT_UP", "HEXAGON" );

    public GUIGridNeighborsChooser(String initialNeighbor) {
        super.setUpChoiceBox(myChooser, initialNeighbor, possibleNeighbors);
    }
    /**
     * This getter method was necessary to add the node that allows the user to access a list of neighbor options
     * to the root of the scene in GUI
     * type
     * @see Node
     * @return myList which is a list of nodes necessary for the user to understand and change values for the grid cells'
     * neighbor types
     */
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myChooser);
        return Collections.unmodifiableList(myList);
    }
    public String getNeighbors(){
        return myChooser.getValue().toString();
    }
}
