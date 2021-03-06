
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GUIGameOfLifePanel extends GUISimulationPanel{

    private Map<String,Double> myMap = new HashMap<>();
    private Text myTextBox;
    private Spinner<Integer> mySpinner;
    private static final String SPINNER_LABEL = "Dead Cells %";
    private static final int MIN = 0;
    private static final int MAX = 100;

    public GUIGameOfLifePanel(String mySimName, Map<String,Double> initParams){
        super(mySimName);
        for (String paramName: initParams.keySet() )
            myMap.put(new String(paramName), new Double(initParams.get(paramName)));
        myTextBox = setUpLabel(SPINNER_LABEL);
        mySpinner = setUpSpinner(MIN,MAX,(int)(myMap.get(GOLSimulation.DEAD_PERCENTAGE) * MAX));
        super.addToStackPane(myTextBox,mySpinner);
    }
    /**
     * This method returns GOL's simulation specific parameters so when a user makes a change that requires a restart,
     * the resulting grid can still parallel in cell percentage distribution
     * type
     * @see GOLSimulation
     */
    public Map<String, Double> getMyParams(){
        myMap.put(GOLSimulation.DEAD_PERCENTAGE,1.0*mySpinner.getValue()/MAX);
        myMap.put(GOLSimulation.ALIVE_PERCENTAGE, 1- 1.0*mySpinner.getValue()/MAX);
        return Collections.unmodifiableMap(myMap);
    }
}
