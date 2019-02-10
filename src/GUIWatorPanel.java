import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;

import java.util.HashMap;

public class GUIWatorPanel extends GUISimulationPanel {
    private Text myFishBreedTextBox;
    private Spinner<Integer> myFishBreedSpinner;
    private Text mySharkBreedTextBox;
    private Spinner<Integer> mySharkBreedSpinner;
    private Text mySharkStarveTextBox;
    private Spinner<Integer> mySharkStarveSpinner;
    private Text mySharkPercentBox;
    private Spinner<Integer> mySharkPercentSpinner;
    private Text myFishPercentBox;
    private Spinner<Integer> myFishPercentSpinner;
    private Text myEmptyPercentBox;
    private Spinner<Integer> myEmptyPercentSpinner;
    private HashMap<String,Double>  myMap = new HashMap<String,Double>();

    private static final int MIN_TURNS = 0;
    private static final int MAX_TURNS = 20;

    public GUIWatorPanel(Simulation mySim){
        super(mySim);

        for (String paramName: mySim.getInitialParams().keySet() )
            myMap.put(new String(paramName), new Double(mySim.getInitialParams().get(paramName)));
        
        myFishBreedTextBox = setUpLabel("Fish Breed");
        mySharkBreedTextBox = setUpLabel("Shark Breed");
        mySharkStarveTextBox = setUpLabel("Shark Starve");
        myFishPercentBox = setUpLabel("Fish Percent");
        mySharkPercentBox = setUpLabel("Shark Percent");
        myEmptyPercentBox = setUpLabel("Water Percent");

        myFishBreedSpinner = setUpSpinner(MIN_TURNS,MAX_TURNS,(int)(myMap.get(WatorSimulation.FISH_BREED_TIME) *1.0));
        mySharkBreedSpinner = setUpSpinner(MIN_TURNS,MAX_TURNS,(int)(myMap.get(WatorSimulation.SHARK_BREED_TIME)*1.0));
        mySharkStarveSpinner = setUpSpinner(MIN_TURNS,MAX_TURNS,(int)(myMap.get(WatorSimulation.STARVE_TIME)*1.0));
        mySharkPercentSpinner = setUpSpinner(0,100,(int)(myMap.get(WatorSimulation.SHARK_PERCENTAGE)*100));
        myFishPercentSpinner = setUpSpinner(0, 100, (int)(myMap.get(WatorSimulation.FISH_PERCENTAGE)*100));
        myEmptyPercentSpinner = setUpSpinner(0,100, (int)(myMap.get(WatorSimulation.EMPTY_PERCENTAGE)*100));

        super.addToStackPane(myFishBreedTextBox,myFishBreedSpinner);
        super.addToStackPane(mySharkBreedTextBox,mySharkBreedSpinner);
        super.addToStackPane(mySharkStarveTextBox,mySharkStarveSpinner);
        super.addToStackPane(mySharkPercentBox,mySharkPercentSpinner);
        super.addToStackPane(myFishPercentBox,myFishPercentSpinner);
        super.addToStackPane(myEmptyPercentBox,myEmptyPercentSpinner);

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
                    mySharkPercentSpinner.decrement(decrement/2);
                    myFishPercentSpinner.decrement(decrement - decrement/2);
                }
                else {
                    int increment = 100 - myEmptyPercentSpinner.getValue() - (mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue());
                    mySharkPercentSpinner.increment(increment/2);
                    myFishPercentSpinner.increment(increment - increment/2);
                }
            }
        });


    }

    public HashMap<String,Double> getMyParams(){
        myMap.put(WatorSimulation.FISH_BREED_TIME,1.0* myFishBreedSpinner.getValue());
        myMap.put(WatorSimulation.SHARK_BREED_TIME,1.0* mySharkBreedSpinner.getValue());
        myMap.put(WatorSimulation.STARVE_TIME,1.0* mySharkStarveSpinner.getValue());
        myMap.put(WatorSimulation.FISH_PERCENTAGE, 1.0* myFishPercentSpinner.getValue()/100);
        myMap.put(WatorSimulation.SHARK_PERCENTAGE, 1.0* mySharkPercentSpinner.getValue()/100);
        myMap.put(WatorSimulation.EMPTY_PERCENTAGE, 1.0* myEmptyPercentSpinner.getValue()/100);
        return myMap;
    }
}