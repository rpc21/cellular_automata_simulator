
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GUISpreadingFirePanel extends GUISimulationPanel {
    private Text myProbTextBox;
    private Spinner<Integer> myProbSpinner;
    private static final String PROB_NAME = "Chance of Fire %";
    private Text myTreeTextBox;
    private Spinner<Integer> myTreeSpinner;
    private static final String TREE_NAME = "Trees %";
    private Text myEmptyTextBox;
    private Spinner<Integer> myEmptySpinner;
    private static final String EMPTY_NAME = "Empty %";
    private Text myFireTextBox;
    private Spinner<Integer> myFireSpinner;
    private static final String FIRE_NAME = "Fire %";

    private static final int SPINNER_MAX = 100;

    private Map<String,Double>  myMap = new HashMap<>();
    /**
     * Sets up SpreadingFire specific parameters for user to manipulate
     * @param mySimName needed for super constructor
     * @param initParams maps initial parameters set from xml file to set up spinner
     * @see Spinner<Integer>
     */
    public GUISpreadingFirePanel(String mySimName, Map<String,Double> initParams){
        super(mySimName);

       for (String paramName: initParams.keySet() )
            myMap.put(new String(paramName), new Double(initParams.get(paramName)));

        myProbTextBox = setUpLabel(PROB_NAME);
        myProbSpinner = setUpSpinner(0,SPINNER_MAX ,(int)(myMap.get(SpreadingFireSimulation.PROB_CATCH) * SPINNER_MAX ));
        super.addToStackPane(myProbTextBox,myProbSpinner);

        myTreeTextBox = setUpLabel(TREE_NAME);
        myTreeSpinner = setUpSpinner(0,SPINNER_MAX ,(int)(myMap.get(SpreadingFireSimulation.TREE_PERCENTAGE) * SPINNER_MAX ));
        super.addToStackPane(myTreeTextBox,myTreeSpinner);

        myFireTextBox = setUpLabel(FIRE_NAME);
        myFireSpinner = setUpSpinner(0,SPINNER_MAX ,(int)(myMap.get(SpreadingFireSimulation.FIRE_PERCENTAGE) * SPINNER_MAX ));

        super.addToStackPane(myFireTextBox,myFireSpinner);

        myEmptyTextBox = setUpLabel(EMPTY_NAME);
        myEmptySpinner = setUpSpinner(0,SPINNER_MAX ,(int)(myMap.get(SpreadingFireSimulation.EMPTY_PERCENTAGE) * SPINNER_MAX ));
        super.addToStackPane(myEmptyTextBox,myEmptySpinner);
    }
    /**
     * Returns parameters simulation needs to restart
     * @see Spinner<Integer>
     */
    @Override
    public Map<String, Double> getMyParams(){
        myMap.put(SpreadingFireSimulation.PROB_CATCH,1.0* myProbSpinner.getValue()/SPINNER_MAX );
        myMap.put(SpreadingFireSimulation.FIRE_PERCENTAGE,1.0* myFireSpinner.getValue()/SPINNER_MAX );
        myMap.put(SpreadingFireSimulation.EMPTY_PERCENTAGE, 1.0* myEmptySpinner.getValue()/SPINNER_MAX );
        myMap.put(SpreadingFireSimulation.TREE_PERCENTAGE, 1.0* myTreeSpinner.getValue()/SPINNER_MAX );
        return Collections.unmodifiableMap(myMap);
    }

}
