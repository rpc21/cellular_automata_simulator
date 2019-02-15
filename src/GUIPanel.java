import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import java.util.List;
/**
 * This class allows all subclasses with control options to have a uniform manner in the user interface and design
 * of spinners and choice boxes between the default panel, simulation specific panel, and the GUIGrid options window.
 * I believe this code is well designed because it allows the subclasses to not have excessively repetitive boilerplate
 * code. Additionally, I was able to impose constraints, such as only having integer spinners, but allow for more user
 * flexibility by having object choice boxes instead of String choice boxes. This way, I could display shapes or images
 * as options instead without having to go through and alter every instance of a choicebox if I wanted to maintain this
 * hierarchy
 */
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
}
