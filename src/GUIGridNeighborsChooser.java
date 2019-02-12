import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.List;

public class GUIGridNeighborsChooser extends GUIControlManager{
    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private List<Object> possibleNeighbors = List.of("ADJACENT", "BOX_NEIGHBORS", "DIAGONAL", "KNIGHT", "I_FORMATION",
            "TRIANGLE_12_POINT_UP", "HEXAGON" );

    public GUIGridNeighborsChooser(String initialNeighbor){
        super.setUpChoiceBox(myChooser, initialNeighbor, possibleNeighbors);
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myChooser);
        return myList;
    }
    public String getNeighbors(){
        return myChooser.getValue().toString();
    }
}
