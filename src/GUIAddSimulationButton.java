import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class GUIAddSimulationButton extends GUIControlManager{

    private Button myButton = new Button();

    private static final String NAME = "Add Simulation";
    private GUIAddSimulation myAddSimFunction;

    public GUIAddSimulationButton(GUIAddSimulation addSim){
        myAddSimFunction = addSim;
        super.setUpButton(myButton, NAME, e -> fire());
    }

    private void fire(){
        myAddSimFunction.guiAddSim();
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myButton);
        return myList;
    }

}
