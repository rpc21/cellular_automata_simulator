import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;

public class GUIGameOfLifePanel extends GUISimulationPanel{

    private HashMap<String,Double> myMap = new HashMap<>();
    private double myInitialDead;
    private Text myTextBox;
    private Spinner<Integer> mySpinner;

    public GUIGameOfLifePanel(Simulation mySim){
        super(mySim);
        for (String paramName: mySim.getInitialParams().keySet() )
            myMap.put(new String(paramName), new Double(mySim.getInitialParams().get(paramName)));
        myTextBox = setUpLabel("Dead Cells %");
        mySpinner = setUpSpinner(0,100,(int)(myMap.get(GOLSimulation.DEAD_PERCENTAGE) * 100));
        super.addToStackPane(myTextBox,mySpinner);
    }

    public HashMap<String,Double> getMyParams(){
        myMap.put(GOLSimulation.DEAD_PERCENTAGE,1.0*mySpinner.getValue()/100);
        myMap.put(GOLSimulation.ALIVE_PERCENTAGE, 1- 1.0*mySpinner.getValue()/100);
        return myMap;
    }
}
