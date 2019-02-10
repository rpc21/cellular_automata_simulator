
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
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myChooser);
        return myList;
    }
    public String getShape(){
        return myChooser.getValue().toString();
    }
}