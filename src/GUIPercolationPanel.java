
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class GUIPercolationPanel extends GUISimulationPanel{
    HashMap<String,Double> myMap = new HashMap<String,Double>();
    private Text myTextBox;
    private Spinner<Integer> mySpinner;
    private static final String SPINNER_NAME = "Blocked Cells %";
    private static final int SPINNER_MAX = 100;
    public GUIPercolationPanel(String mySimName, Map<String,Double> initParams){
        super(mySimName);

        for (String paramName: initParams.keySet() )
            myMap.put(new String(paramName), new Double(initParams.get(paramName)));


        myTextBox = setUpLabel(SPINNER_NAME);
        mySpinner = setUpSpinner(0,SPINNER_MAX,(int)(myMap.get(PercolationSimulation.CLOSED_PERCENTAGE)*SPINNER_MAX));
        super.addToStackPane(myTextBox,mySpinner);
    }

    public HashMap<String,Double> getMyParams(){
        myMap.put(PercolationSimulation.CLOSED_PERCENTAGE,1.0* mySpinner.getValue()/SPINNER_MAX);
        myMap.put(PercolationSimulation.OPEN_PERCENTAGE, 1 - 1.0* mySpinner.getValue()/SPINNER_MAX);
        myMap.put(PercolationSimulation.PERCOLATED_PERCENTAGE,0.0);
        return myMap;
    }


}
