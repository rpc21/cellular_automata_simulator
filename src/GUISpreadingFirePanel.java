
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;
import java.util.HashMap;

public class GUISpreadingFirePanel extends GUISimulationPanel {
    private Simulation mySimulation;
    private Text myProbTextBox;
    private Spinner<Integer> myProbSpinner;
    private Text myTreeTextBox;
    private Spinner<Integer> myTreeSpinner;
    private Text myEmptyTextBox;
    private Spinner<Integer> myEmptySpinner;
    private Text myFireTextBox;
    private Spinner<Integer> myFireSpinner;

    private HashMap<String,Double>  myMap = new HashMap<String,Double>();
    public GUISpreadingFirePanel(Simulation mySim){
        super(mySim);
        mySimulation = mySim;

       for (String paramName: mySim.getInitialParams().keySet() )
            myMap.put(new String(paramName), new Double(mySim.getInitialParams().get(paramName)));

        myProbTextBox = setUpLabel("Chance of Fire %");
        myProbSpinner = setUpSpinner(0,100,(int)(myMap.get(SpreadingFireSimulation.PROB_CATCH) * 100));
        super.addToStackPane(myProbTextBox,myProbSpinner);

        myTreeTextBox = setUpLabel("Trees %");
        myTreeSpinner = setUpSpinner(0,100,(int)(myMap.get(SpreadingFireSimulation.TREE_PERCENTAGE) * 100));
        super.addToStackPane(myTreeTextBox,myTreeSpinner);

        myFireTextBox = setUpLabel("Fire %");
        myFireSpinner = setUpSpinner(0,100,(int)(myMap.get(SpreadingFireSimulation.FIRE_PERCENTAGE) * 100));

        super.addToStackPane(myFireTextBox,myFireSpinner);

        myEmptyTextBox = setUpLabel("Empty %");
        myEmptySpinner = setUpSpinner(0,100,(int)(myMap.get(SpreadingFireSimulation.EMPTY_PERCENTAGE) * 100));
        super.addToStackPane(myEmptyTextBox,myEmptySpinner);
    }

    public HashMap<String,Double> getMyParams(){
        myMap.put(SpreadingFireSimulation.PROB_CATCH,1.0* myProbSpinner.getValue()/100);
        myMap.put(SpreadingFireSimulation.FIRE_PERCENTAGE,1.0* myFireSpinner.getValue()/100);
        myMap.put(SpreadingFireSimulation.EMPTY_PERCENTAGE, 1.0* myEmptySpinner.getValue()/100);
        myMap.put(SpreadingFireSimulation.TREE_PERCENTAGE, 1.0* myTreeSpinner.getValue()/100);
        return myMap;
    }
}
