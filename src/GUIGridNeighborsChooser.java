import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIGridNeighborsChooser extends GUIControlManager{
    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private List<Object> possibleNeighbors = Arrays.asList("adjacent", "box_neighbors", "diagnol", "knight",
            "i_formation","hexagon");
    public GUIGridNeighborsChooser(String initialNeighbor){
        super.setUpChoiceBox(myChooser, initialNeighbor, possibleNeighbors);
        myChooser.setValue("adjacent");
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
