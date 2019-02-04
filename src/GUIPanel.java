import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class GUIPanel {
    private boolean needsToChangeSim;
    public GUIPanel(){
        needsToChangeSim = false;
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
    public boolean needsToReset(){
        return needsToChangeSim;
    }

    public void setNeedsToReset(){
        needsToChangeSim = true;
    }
}
