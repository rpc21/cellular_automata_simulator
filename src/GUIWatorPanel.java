import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GUIWatorPanel extends GUISimulationPanel {
    private Text myFishBreedTextBox;
    private Spinner<Integer> myFishBreedSpinner;
    private static final String myFishBreedName = "Fish Breed";

    private Text mySharkBreedTextBox;
    private Spinner<Integer> mySharkBreedSpinner;
    private static final String mySharkBreedName = "Shark Breed";

    private Text mySharkStarveTextBox;
    private Spinner<Integer> mySharkStarveSpinner;
    private static final String mySharkStarveName = "Shark Starve";

    private Text mySharkPercentBox;
    private Spinner<Integer> mySharkPercentSpinner;
    private static final String mySharkPercentName = "Shark Percent";

    private Text myFishPercentBox;
    private Spinner<Integer> myFishPercentSpinner;
    private static final String myFishPercentName = "Fish Percent";

    private Text myEmptyPercentBox;
    private Spinner<Integer> myEmptyPercentSpinner;
    private static final String myEmptyPercentName = "Water Percent";

    private HashMap<String,Double>  myMap = new HashMap<String,Double>();

    private static final int MIN_TURNS = 0;
    private static final int MAX_TURNS = 20;
    /**
     * Sets up Wator specific parameters for user to manipulate
     * @param mySimName needed for super constructor
     * @param initParams maps initial parameters set from xml file to set up spinner
     * @see Spinner<Integer>
     */
    public GUIWatorPanel(String mySimName, Map<String,Double> initParams) {
        super(mySimName);

        for (String paramName : initParams.keySet())
            myMap.put(new String(paramName), new Double(initParams.get(paramName)));

        myFishBreedTextBox = setUpLabel(myFishBreedName);
        mySharkBreedTextBox = setUpLabel(mySharkBreedName);
        mySharkStarveTextBox = setUpLabel(mySharkStarveName);
        myFishPercentBox = setUpLabel(myFishPercentName);
        mySharkPercentBox = setUpLabel(mySharkPercentName);
        myEmptyPercentBox = setUpLabel(myEmptyPercentName);

        myFishBreedSpinner = setUpSpinner(MIN_TURNS, MAX_TURNS, (int) (myMap.get(WatorSimulation.FISH_BREED_TIME) * 1.0));
        mySharkBreedSpinner = setUpSpinner(MIN_TURNS, MAX_TURNS, (int) (myMap.get(WatorSimulation.SHARK_BREED_TIME) * 1.0));
        mySharkStarveSpinner = setUpSpinner(MIN_TURNS, MAX_TURNS, (int) (myMap.get(WatorSimulation.STARVE_TIME) * 1.0));
        mySharkPercentSpinner = setUpSpinner(0, 100, (int) (myMap.get(WatorSimulation.SHARK_PERCENTAGE) * 100));
        myFishPercentSpinner = setUpSpinner(0, 100, (int) (myMap.get(WatorSimulation.FISH_PERCENTAGE) * 100));
        myEmptyPercentSpinner = setUpSpinner(0, 100, (int) (myMap.get(WatorSimulation.EMPTY_PERCENTAGE) * 100));
        setUpIntertwinedValues();

        super.addToStackPane(myFishBreedTextBox, myFishBreedSpinner);
        super.addToStackPane(mySharkBreedTextBox, mySharkBreedSpinner);
        super.addToStackPane(mySharkStarveTextBox, mySharkStarveSpinner);
        super.addToStackPane(mySharkPercentBox, mySharkPercentSpinner);
        super.addToStackPane(myFishPercentBox, myFishPercentSpinner);
        super.addToStackPane(myEmptyPercentBox, myEmptyPercentSpinner);
    }
    /**
     * Links values together so user can't input an illogical grouping of parameters (i.e. a set of percentages that don't sum to 100)
     * @see Spinner<Integer>
     */
        private void setUpIntertwinedValues() {
            mySharkPercentSpinner.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                    if ((myFishPercentSpinner.getValue() + mySharkPercentSpinner.getValue() + myEmptyPercentSpinner.getValue() != 100)) {
                        if (myFishPercentSpinner.getValue() + mySharkPercentSpinner.getValue() > 100 - myEmptyPercentSpinner.getValue())
                            myFishPercentSpinner.decrement(mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue() - 100 + myEmptyPercentSpinner.getValue());
                        else
                            myFishPercentSpinner.increment(100 - myEmptyPercentSpinner.getValue() - (mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue()));
                    }
                }
            });

            myFishPercentSpinner.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                    if ((myFishPercentSpinner.getValue() + mySharkPercentSpinner.getValue() + myEmptyPercentSpinner.getValue() != 100)) {
                        if (mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue() > (100 - myEmptyPercentSpinner.getValue()))
                            mySharkPercentSpinner.decrement(mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue() - 100 + myEmptyPercentSpinner.getValue());
                        else
                            mySharkPercentSpinner.increment(100 - myEmptyPercentSpinner.getValue() - (mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue()));
                    }
                }
            });

            myEmptyPercentSpinner.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                    if (mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue() > (100 - myEmptyPercentSpinner.getValue())) {
                        int decrement = mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue() - 100 + myEmptyPercentSpinner.getValue();
                        mySharkPercentSpinner.decrement(decrement / 2);
                        myFishPercentSpinner.decrement(decrement - decrement / 2);
                    } else {
                        int increment = 100 - myEmptyPercentSpinner.getValue() - (mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue());
                        mySharkPercentSpinner.increment(increment / 2);
                        myFishPercentSpinner.increment(increment - increment / 2);
                    }
                }
            });


        }
    /**
     * Returns parameters simulation needs to restart
     * @see Spinner<Integer>
     */
    public Map<String, Double> getMyParams(){
        myMap.put(WatorSimulation.FISH_BREED_TIME,1.0* myFishBreedSpinner.getValue());
        myMap.put(WatorSimulation.SHARK_BREED_TIME,1.0* mySharkBreedSpinner.getValue());
        myMap.put(WatorSimulation.STARVE_TIME,1.0* mySharkStarveSpinner.getValue());
        myMap.put(WatorSimulation.FISH_PERCENTAGE, 1.0* myFishPercentSpinner.getValue()/100);
        myMap.put(WatorSimulation.SHARK_PERCENTAGE, 1.0* mySharkPercentSpinner.getValue()/100);
        myMap.put(WatorSimulation.EMPTY_PERCENTAGE, 1.0* myEmptyPercentSpinner.getValue()/100);
        return Collections.unmodifiableMap(myMap);
    }
}