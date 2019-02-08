
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIShapeChooser extends GUIControlManager{

    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private List<Object> possibleShapes = Arrays.asList("Rectangle", "Triangle", "Hexagon");
    public GUIShapeChooser(String initialShape){
        super.setUpChoiceBox(myChooser, initialShape, possibleShapes);
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myChooser);
        return myList;
    }
}