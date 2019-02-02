import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.sql.SQLOutput;
import java.util.HashMap;

public class GUISegregationPanel extends GUISimulationPanel {
    private Simulation mySimulation;
    private Spinner<Double> myThresholdSpinner = new Spinner<>();
    private Text myThresh;
    private Slider myRaceOneSlider;
    private Text myRaceOne;
    private Slider myEmptySlider;
    private Text myEmpty;
    private HashMap<String,Double>  myMap = new HashMap<String,Double>();
    double emptyCurrVal;
    double redCurrVal;
    double blueCurrVal;
    int redCurrRatio;
    int blueCurrRatio;

    public GUISegregationPanel(Simulation mySim){
        super(mySim);
        mySimulation = mySim;

        double redCount = 0;
        double emptyCount = 0;
        double blueCount = 0;
        for (Cell c: mySim.getMyGrid().getCells()){ ;
            if (c.getMyColor() == Color.RED)
                redCount++;
            else if (c.getMyColor() == Color.BLUE)
                blueCount++;
            else if (c.getMyColor() == Color.WHITE)
                emptyCount++;
        }
        redCurrVal = redCount/(mySim.getMyGrid().getCells().size());
        blueCurrVal =  blueCount/(mySim.getMyGrid().getCells().size());
        emptyCurrVal = emptyCount/(mySim.getMyGrid().getCells().size());
        myEmptySlider = new Slider(0,1,emptyCurrVal);


        myThresh = new Text("Threshold");
        myThresh.setTextAlignment(TextAlignment.LEFT);
        SpinnerValueFactory<Double> valueFactory = //
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0.3);
        ((SpinnerValueFactory.DoubleSpinnerValueFactory) valueFactory).setAmountToStepBy(0.01);
        myThresholdSpinner.setValueFactory(valueFactory);
        myThresholdSpinner.setEditable(true);
        myThresholdSpinner.setMaxWidth(80);
        myThresholdSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myMap.remove("Threshold");
                myMap.put("Threshold",myThresholdSpinner.getValue());
                if (mySimulation instanceof SpreadingFireSimulation) {
                    mySimulation.updateNewParams(myMap);
                }
            }
        });
        super.addToStackPane(myThresh,myThresholdSpinner);

        myRaceOneSlider = new Slider(0,1.0,redCurrVal/(redCurrVal + blueCurrVal));
        toFraction(redCurrVal/blueCurrVal);
        myRaceOne = new Text("Red:Blue = " + redCurrRatio + "/" + blueCurrRatio);
        myRaceOne.setTextAlignment(TextAlignment.LEFT);
        myRaceOneSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myRaceOneSlider.setValue(myRaceOneSlider.getValue());
                redCurrVal = (mySim.getMyGrid().getCells().size() - emptyCurrVal * mySim.getMyGrid().getCells().size()) * myRaceOneSlider.getValue()/ (1+ myRaceOneSlider.getValue())/mySim.getMyGrid().getCells().size();
                blueCurrVal = 1 - redCurrVal - emptyCurrVal;
                toFraction(redCurrVal/blueCurrVal);
                myRaceOne.setText("Red:Blue = " + redCurrRatio + "/" + blueCurrRatio);
            }
        });
        super.addToStackPane(myRaceOne,myRaceOneSlider);

        myEmpty = new Text("% Empty");
        myEmpty.setTextAlignment(TextAlignment.LEFT);
        myEmptySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myEmptySlider.setValue(myEmptySlider.getValue());
                myMap.remove("Empty");
                myMap.put("Empty",myEmptySlider.getValue());
                if (mySimulation instanceof SpreadingFireSimulation) {
                    mySimulation.updateNewParams(myMap);
                }
            }
        });
        super.addToStackPane(myEmpty,myEmptySlider);

    }

    private void toFraction(double x) {
        // Approximate x with p/q.
        final double eps = 0.01;
        int pfound = (int) Math.round(x);
        int qfound = 1;
        double errorfound = Math.abs(x - pfound);
        for (int q = 2; q < 100 && errorfound > eps; ++q) {
            int p = (int) (x * q);
            for (int i = 0; i < 2; ++i) { // below and above x
                double error = Math.abs(x - ((double) p / q));
                if (error < errorfound) {
                    pfound = p;
                    qfound = q;
                    errorfound = error;
                }
                ++p;
            }
        }
        redCurrRatio = pfound;
        blueCurrRatio = qfound;


    }
}