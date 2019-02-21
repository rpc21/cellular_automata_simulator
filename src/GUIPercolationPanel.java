
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GUIPercolationPanel extends GUISimulationPanel{
    Map<String,Double> myMap = new HashMap<String,Double>();
    private Text myTextBox;
    private Spinner<Integer> mySpinner;
    private static final String SPINNER_NAME = "Blocked Cells %";
    private static final int SPINNER_MAX = 100;
    /**
     * Sets up Percolation specific parameters for user to manipulate
     * @param mySimName needed for super constructor
     * @param initParams maps initial parameters set from xml file to set up spinner
     * @see Spinner<Integer>
     */
    public GUIPercolationPanel(String mySimName, Map<String,Double> initParams){
        super(mySimName);

        for (String paramName: initParams.keySet() )
            myMap.put(new String(paramName), new Double(initParams.get(paramName)));


        myTextBox = setUpLabel(SPINNER_NAME);
        mySpinner = setUpSpinner(0,SPINNER_MAX,(int)(myMap.get(PercolationSimulation.CLOSED_PERCENTAGE)*SPINNER_MAX));
        super.addToStackPane(myTextBox,mySpinner);
    }
    /**
     * Returns parameters simulation needs to restart. Assumes the user wishes to begin the simulation before any water
     * has begun to flow through the grid
     * @see Spinner<Integer>
     */
    public Map<String, Double> getMyParams(){
        myMap.put(PercolationSimulation.CLOSED_PERCENTAGE,1.0* mySpinner.getValue()/SPINNER_MAX);
        myMap.put(PercolationSimulation.OPEN_PERCENTAGE, 1 - 1.0* mySpinner.getValue()/SPINNER_MAX);
        myMap.put(PercolationSimulation.PERCOLATED_PERCENTAGE,0.0);
        return Collections.unmodifiableMap(myMap);
    }


}
