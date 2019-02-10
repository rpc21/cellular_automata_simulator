import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIGridNeighborsChooser extends GUIControlManager{
    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private List<Object> possibleNeighbors = Arrays.asList("Adjacent");
    public GUIGridNeighborsChooser(String initialNeighbor){
        super.setUpChoiceBox(myChooser, initialNeighbor, possibleNeighbors);
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myChooser);
        return myList;
    }
}
