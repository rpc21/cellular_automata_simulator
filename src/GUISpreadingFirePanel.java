import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;

public class GUISpreadingFirePanel extends GUISimulationPanel {
    private Simulation mySimulation;
    private Text myTextBox;
    private Spinner<Double> mySpinner;
    private HashMap<String,Double>  myMap = new HashMap<String,Double>();

    public GUISpreadingFirePanel(Simulation mySim){
        super(mySim);

        myTextBox = new Text("Probability of Fire");
        myTextBox.setTextAlignment(TextAlignment.LEFT);
        mySimulation = mySim;
        mySpinner = new Spinner<Double>();
        final double initialValue = 1.0;
        SpinnerValueFactory<Double> valueFactory = //
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1.0, initialValue);
        ((SpinnerValueFactory.DoubleSpinnerValueFactory) valueFactory).setAmountToStepBy(0.01);
        mySpinner.setValueFactory(valueFactory);
        mySpinner.setEditable(true);
        mySpinner.setMaxWidth(80);
        mySpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myMap.remove("probCatch");
                myMap.put("probCatch",mySpinner.getValue());
                if (mySim instanceof SpreadingFireSimulation) {
                    mySimulation.updateNewParams(myMap);
                }
            }
        });
        super.addToStackPane(myTextBox,mySpinner);
        //super.addToStackPane(mySlider);
    }
}
