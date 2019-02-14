import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIPanel {

    private static final int SPINNER_STEP = 1;
    private static final int SPINNER_MAX_WIDTH = 80;
    protected static String DEFAULT_FONT_NAME = "Copperplate";
    protected static int CONTROL_TEXT_SIZE = 15;

    public GUIPanel(){
    }
    /**
     * This is a generic method panels may use to set up spinners such that it is only set to save its current inputted
     * value. Strictly using an integer-only spinner helps standardize calculations
     * @param min this is the minimum value permitted to be input (any value the user inputs below this will default to this minimum)
     * @param init this is the default value of the spinner
     * @param max this is the maximum value permitted to be input (any value the user inputs above this will default to this maximum)
     * @see Spinner<Integer>
     */
    protected Spinner<Integer> setUpSpinner(int min, int max, int init){
        Spinner<Integer> mySpinner = new Spinner<>();
        SpinnerValueFactory<Integer> valFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, init);
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) valFactory).setAmountToStepBy(SPINNER_STEP);
        mySpinner.setValueFactory(valFactory);
        mySpinner.setEditable(true);
        mySpinner.setMaxWidth(SPINNER_MAX_WIDTH);
        return mySpinner;
    }
    /**
     * This is a generic method panels may use to set up choice boxes such that it is only set to save its current inputted
     * value. Flexible to all object types
     * @see ChoiceBox<Object>
     */
    protected void setUpChoiceBox(ChoiceBox<Object> cb, Object initVal, List<Object> possibleChoices){
        cb.getItems().addAll(possibleChoices);
        cb.setValue(initVal);
        cb.setStyle("-fx-font: " + CONTROL_TEXT_SIZE + "px \"" + DEFAULT_FONT_NAME + "\";");
        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                cb.setValue(cb.getItems().get((Integer)number2));
            }
        });
    }

    /**
     * This is a generic method panels may use to return important parameters for the GUIManager to use
     * @see ChoiceBox<Object>
     */
    public Map<String,Double> getMyParams(){
        return new HashMap<String,Double>();
    }
}
