
import javafx.scene.control.Spinner;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.HashMap;

public class GUIPercolationPanel extends GUISimulationPanel{
    HashMap<String,Double> myMap = new HashMap<String,Double>();
    private Text myTextBox;
    private Spinner<Integer> mySpinner;

    public GUIPercolationPanel(Simulation mySim){
        super(mySim);
        myMap.put(PercolationSimulation.PERCOLATED_PERCENTAGE,super.getCurrentCount(Color.WHITE));
        myMap.put(PercolationSimulation.OPEN_PERCENTAGE,super.getCurrentCount(PercolationState.OPEN.getMyCellColor()));
        myMap.put(PercolationSimulation.CLOSED_PERCENTAGE,super.getCurrentCount(PercolationState.CLOSED.getMyCellColor()));
        myTextBox = setUpLabel("Blocked Cells %");
        mySpinner = setUpSpinner(0,100,(int)(myMap.get(PercolationSimulation.CLOSED_PERCENTAGE)*100));
        super.addToStackPane(myTextBox,mySpinner);
    }

    public HashMap<String,Double> getMyParams(){
        myMap.put(PercolationSimulation.CLOSED_PERCENTAGE,1.0* mySpinner.getValue()/100);
        myMap.put(PercolationSimulation.OPEN_PERCENTAGE, 1 - 1.0* mySpinner.getValue()/100);
        myMap.put(PercolationSimulation.PERCOLATED_PERCENTAGE,0.0);
        return myMap;
    }


}
