import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIGridBoundriesChooser extends GUIControlManager{
    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private List<Object> possibleBoundries = Arrays.asList("None", "Lined");
    public GUIGridBoundriesChooser(String initialBoundry){
        super.setUpChoiceBox(myChooser, initialBoundry, possibleBoundries);
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myChooser);
        return myList;
    }
    public Paint determineStroke(Paint c){
        if (myChooser.getValue().toString().equals("None"))
            return c;
        else
            return Color.BLACK;
    }
}
