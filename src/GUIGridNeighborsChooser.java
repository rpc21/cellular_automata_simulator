import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIGridNeighborsChooser extends GUIControlManager{
    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private List<Object> possibleNeighbors = List.of("adjacent", "box_neighbors", "diagonal", "knight", "i_formation",
            "triangle_12_point_up", "hexagon" );

    public GUIGridNeighborsChooser(String initialNeighbor){
        System.out.println("****" + initialNeighbor);
        String holder = initialNeighbor.toLowerCase();
        super.setUpChoiceBox(myChooser, holder, possibleNeighbors);
//        myChooser.setValue("adjacent");
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
