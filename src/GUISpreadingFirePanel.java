import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

public class GUISpreadingFirePanel extends GUISimulationPanel {
    Simulation mySimulation;
    Slider mySlider;
    public GUISpreadingFirePanel(Simulation mySim){
        super(mySim);
        mySimulation = mySim;
        mySlider = new Slider(0.0,1.0,0.5);
        mySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                mySlider.setValue(mySlider.getValue());
                if (mySim instanceof SpreadingFireSimulation)
                    System.out.println(mySlider.getValue());
            }
        });
        super.addToStackPane(mySlider);
    }
}
