import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;
import java.util.HashMap;

public class GUISpreadingFirePanel extends GUISimulationPanel {
    private Simulation mySimulation;
    private Text myTextBox;
    private Spinner<Integer> mySpinner;
    private HashMap<String,Double>  myMap = new HashMap<String,Double>();

    public GUISpreadingFirePanel(Simulation mySim){
        super(mySim);
        mySimulation = mySim;

        myTextBox = setUpLabel("% Chance of Fire");
        mySpinner = setUpSpinner(0,100,1);
        mySpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myMap.remove("probCatch");
                myMap.put("probCatch",1.0*mySpinner.getValue()/100);
                mySimulation.updateNewParams(myMap);
            }
        });
        super.addToStackPane(myTextBox,mySpinner);
    }
}
