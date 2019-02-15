
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;
import java.util.HashMap;
import java.util.Map;

public class GUISegregationPanel extends GUISimulationPanel {
    private Spinner<Integer> myThresholdSpinner;
    private Text myThresh;
    private Spinner<Integer> myRaceOneSpinner;
    private Text myRaceOne;
    private Spinner<Integer> myRaceTwoSpinner;
    private Text myRaceTwo;
    private Spinner<Integer> myEmptySpinner;
    private Text myEmpty;
    private HashMap<String,Double>  myMap = new HashMap<String,Double>();
    private static final int SPINNER_MAX = 100;

    private static final String THRESH_NAME = "Threshold %";
    private static final String EMPTY_NAME = "Empty %";
    private static final String RACE_ONE_NAME = "Red %";
    private static final String RACE_TWO_NAME = "Blue %";

    /**
     * Sets up segregation simulation panel with needed spinners and labels
     * @see Spinner<></>
     */

    public GUISegregationPanel(String mySimName, Map<String,Double> initParams){
        super(mySimName);

        for (String paramName: initParams.keySet() )
            myMap.put(new String(paramName), new Double(initParams.get(paramName)));

        myThresholdSpinner = setUpSpinner(0,SPINNER_MAX,(int)(myMap.get(SegregationSimulation.THRESHOLD)*100.0));
        myRaceOneSpinner = setUpSpinner(0,SPINNER_MAX,(int)(myMap.get(SegregationSimulation.RED_PERCENTAGE)*100.0));
        myRaceTwoSpinner = setUpSpinner(0,SPINNER_MAX,(int)(myMap.get(SegregationSimulation.BLUE_PERCENTAGE)*100.0));
        myEmptySpinner = setUpSpinner(0,SPINNER_MAX,(int)(myMap.get(SegregationSimulation.EMPTY_PERCENTAGE)*100.0));


        myThresh = setUpLabel(THRESH_NAME);
        myEmpty = setUpLabel(EMPTY_NAME);
        myRaceOne = setUpLabel(RACE_ONE_NAME);
        myRaceTwo = setUpLabel(RACE_TWO_NAME);

        super.addToStackPane(myThresh,myThresholdSpinner);
        super.addToStackPane(myRaceOne,myRaceOneSpinner);
        super.addToStackPane(myRaceTwo,myRaceTwoSpinner);
        super.addToStackPane(myEmpty,myEmptySpinner);


    }

    /**
     * Returns parameters Simulation needs to restart a simulation with this distribution of percentages of cells
     * @see Spinner<Integer>
     */
    public HashMap<String,Double> getMyParams(){
        myMap.put(SegregationSimulation.THRESHOLD,1.0*myThresholdSpinner.getValue()/SPINNER_MAX);
        myMap.put(SegregationSimulation.EMPTY_PERCENTAGE, 1.0 * myEmptySpinner.getValue() /SPINNER_MAX);
        myMap.put(SegregationSimulation.RED_PERCENTAGE,1.0 *myRaceOneSpinner.getValue()/SPINNER_MAX);
        myMap.put(SegregationSimulation.BLUE_PERCENTAGE, 1.0 *myRaceTwoSpinner.getValue()/SPINNER_MAX);
        return myMap;
    }
}