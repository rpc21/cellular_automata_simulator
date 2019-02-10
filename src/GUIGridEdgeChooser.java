import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIGridEdgeChooser extends GUIControlManager{
    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private List<Object> possibleEdges = Arrays.asList("Finite", "Toroidal", "Infinite");
    public GUIGridEdgeChooser(String initialEdge){
        super.setUpChoiceBox(myChooser, initialEdge, possibleEdges);
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myChooser);
        return myList;
    }
}