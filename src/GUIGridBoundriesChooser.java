import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIGridBoundriesChooser extends GUIControlManager{
    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private static final String BORDER_ON = "yes";
    private static final String BORDER_OFF = "no";

    private List<Object> possibleBoundries = Arrays.asList(BORDER_ON, BORDER_OFF);
    
    public GUIGridBoundriesChooser(String initialBoundry){
        super.setUpChoiceBox(myChooser, initialBoundry, possibleBoundries);
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myChooser);
        return myList;
    }
    public boolean determineStroke(){
        return (myChooser.getValue().toString().equals(BORDER_ON));
    }
}
