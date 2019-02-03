import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;

public class GUIWatorPanel extends GUISimulationPanel {
    private Simulation mySimulation;
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
    private int percentEmpty = 20;
    private HashMap<String,Double>  myMap = new HashMap<String,Double>();

    private static final int MIN_TURNS = 0;
    private static final int MAX_TURNS = 10;

    public GUIWatorPanel(Simulation mySim){
        super(mySim);
        mySimulation = mySim;
        
        myFishBreedTextBox = setUpLabel("Fish Breed");
        mySharkBreedTextBox = setUpLabel("Shark Breed");
        mySharkStarveTextBox = setUpLabel("Shark Starve");
        myFishPercentBox = setUpLabel("Fish Percent");
        mySharkPercentBox = setUpLabel("Shark Percent");

        myFishBreedSpinner = setUpSpinner(MIN_TURNS,MAX_TURNS,5);
        mySharkBreedSpinner = setUpSpinner(MIN_TURNS,MAX_TURNS,5);
        mySharkStarveSpinner = setUpSpinner(MIN_TURNS,MAX_TURNS,5);
        mySharkPercentSpinner = setUpSpinner(0,100 - percentEmpty, (100-percentEmpty)/2);
        myFishPercentSpinner = setUpSpinner(0, 100 - percentEmpty, (100-percentEmpty)/2);

        myFishBreedSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myMap.remove("Fish Breed");
                myMap.put("Fish Breed",myFishBreedSpinner.getValue() * 1.0);
                mySimulation.updateNewParams(myMap);
            }
        });
        super.addToStackPane(myFishBreedTextBox,myFishBreedSpinner);


        mySharkBreedSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myMap.remove("Shark Breed");
                myMap.put("Shark Breed",mySharkBreedSpinner.getValue() * 1.0);
                mySimulation.updateNewParams(myMap);
            }
        });
        super.addToStackPane(mySharkBreedTextBox,mySharkBreedSpinner);


        mySharkStarveSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myMap.remove("Shark Starve");
                myMap.put("Shark Starve",mySharkStarveSpinner.getValue() * 1.0);
                mySimulation.updateNewParams(myMap);
            }
        });
        super.addToStackPane(mySharkStarveTextBox,mySharkStarveSpinner);

        mySharkPercentSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if (myFishPercentSpinner.getValue() + mySharkPercentSpinner.getValue() > 100 - percentEmpty)
                    myFishPercentSpinner.decrement(mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue() - 100 + percentEmpty);
                else
                    myFishPercentSpinner.increment(100 - percentEmpty - (mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue()));
                myMap.remove("sharkPercentage");
                myMap.put("sharkPercentage",mySharkPercentSpinner.getValue() * 1.0);
                mySimulation.updateNewParams(myMap);
            }
        });
        super.addToStackPane(mySharkPercentBox,mySharkPercentSpinner);

        myFishPercentSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if (mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue() > (100 - percentEmpty))
                    mySharkPercentSpinner.decrement(mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue() - 100 + percentEmpty );
                else
                    mySharkPercentSpinner.increment(100 - percentEmpty - (mySharkPercentSpinner.getValue() + myFishPercentSpinner.getValue()));
                myMap.remove("fishPercentage");
                myMap.put("fishPercentage",myFishPercentSpinner.getValue() * 1.0);
                mySimulation.updateNewParams(myMap);
            }
        });
        super.addToStackPane(myFishPercentBox,myFishPercentSpinner);

    }
}