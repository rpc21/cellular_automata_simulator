import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    /**
     * Determines whether to remove or add a simulation based upon the current state of the GUI
     * @see GUI
     * @see GUIAddSimulation
     * @see GUIRemoveSimulation
     */
    private void fire(){
        if (myButton.getText().equals(NAME_TWO)) {
            myAddSimFunction.guiAddSim();
            myButton.setText(NAME_ONE);
        }
        else {
            myRemoveSimFunction.guiRemoveSim();
            myButton.setText(NAME_TWO);
        }

    }
    /**
     * Returns a list of all the nodes relevant to this control function
     * @see Button
     */
    public Collection<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myButton);
        return Collections.unmodifiableList(myList);
    }

}
