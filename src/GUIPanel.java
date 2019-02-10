import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIPanel {

    private boolean needsToReset;
    private static final int SPINNER_STEP = 1;
    protected static String DEFAULT_FONT_NAME = "Copperplate";
    protected static int CONTROL_TEXT_SIZE = 15;
    public GUIPanel(){

    }
    protected Spinner<Integer> setUpSpinner(int min, int max, int init){
        Spinner<Integer> mySpinner = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, init);
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) valFactory).setAmountToStepBy(SPINNER_STEP);
        mySpinner.setValueFactory(valFactory);
        mySpinner.setEditable(true);
        mySpinner.setMaxWidth(80);
        return mySpinner;
    }
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

    public void setResetClicked(){
        needsToReset = true;
    }


    public Map<String,Double> getMyParams(){
        return new HashMap<String,Double>();
    }
}
