import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class GUIAddSimulationButton extends GUIControlManager{

    private Button myButton = new Button();

    private static final String NAME_ONE = "One Simulation";
    private static final String NAME_TWO = "Two Simulations";
    private GUIAddSimulation myAddSimFunction;
    private GUIRemoveSimulation myRemoveSimFunction;

    public GUIAddSimulationButton(GUIAddSimulation addSim, GUIRemoveSimulation removeSim){
        myAddSimFunction = addSim;
        myRemoveSimFunction = removeSim;
        super.setUpButton(myButton,NAME_TWO, e -> fire());
    }

    private void fire(){
        if (myButton.getText() == NAME_TWO) {
            myAddSimFunction.guiAddSim();
            myButton.setText(NAME_ONE);
        }
        else {
            myRemoveSimFunction.guiRemoveSim();
            myButton.setText(NAME_TWO);
        }

    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myButton);
        return myList;
    }

}
