import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.HashMap;

public class GUIPanel {
    private boolean needsToChangeSim;
    private boolean needsToUpdateParam;
    private boolean needsToReset;
    public GUIPanel(){
        needsToChangeSim = false;
        needsToUpdateParam = false;
    }
    protected Spinner<Integer> setUpSpinner(int min, int max, int init){
        Spinner<Integer> mySpinner = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, init);
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) valFactory).setAmountToStepBy(1);
        mySpinner.setValueFactory(valFactory);
        mySpinner.setEditable(true);
        mySpinner.setMaxWidth(80);
        return mySpinner;
    }

    public void setResetClicked(){
        needsToReset = true;
    }
    public boolean getResetClicks(){
        return needsToReset;
    }

    public boolean needsToReset(){
        return needsToChangeSim;
    }

    public void setNeedsToReset(){
        needsToChangeSim = true;
    }

    public boolean needsToUpdate() {return needsToUpdateParam;}

    public void setNeedsToUpdate() {needsToUpdateParam = true;}

    public HashMap<String,Double> getMyParams(){
        return new HashMap<String,Double>();
    }
}
