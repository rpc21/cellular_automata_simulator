import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.HashMap;
import java.util.Map;

public class GUIPanel {

    private boolean needsToReset;
    private static final int SPINNER_STEP = 1;
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

    public void setResetClicked(){
        needsToReset = true;
    }


    public Map<String,Double> getMyParams(){
        return new HashMap<String,Double>();
    }
}
