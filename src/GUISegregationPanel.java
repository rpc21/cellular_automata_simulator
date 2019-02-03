import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.HashMap;

public class GUISegregationPanel extends GUISimulationPanel {
    private Simulation mySimulation;
    private Spinner<Integer> myThresholdSpinner = new Spinner<>();
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
        setUpInitialValues();

        myThresh = setUpLabel("Threshold %");
        myEmpty = setUpLabel("Empty %");
        myRaceOne = setUpLabel("Red:Blue = " + redCurrRatio + "/" + blueCurrRatio);

        myThresholdSpinner = setUpSpinner(0,100,30);
        myThresholdSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myMap.remove(SegregationSimulation.THRESHOLD);
                myMap.put(SegregationSimulation.THRESHOLD,myThresholdSpinner.getValue() *1.0/100);
                mySimulation.updateNewParams(myMap);
            }
        });
        super.addToStackPane(myThresh,myThresholdSpinner);

        myRaceOneSlider = new Slider(0,1.0,redCurrVal/(redCurrVal + blueCurrVal));
        toFraction(redCurrVal/blueCurrVal);
        myRaceOneSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myRaceOneSlider.setValue(myRaceOneSlider.getValue());
                redCurrVal = (mySim.getMyGrid().getCells().size() - emptyCurrVal * mySim.getMyGrid().getCells().size()) * myRaceOneSlider.getValue()/ (1+ myRaceOneSlider.getValue())/mySim.getMyGrid().getCells().size();
                blueCurrVal = 1 - redCurrVal - emptyCurrVal;
                toFraction(redCurrVal/blueCurrVal);
                myRaceOne.setText("Red:Blue = " + redCurrRatio + "/" + blueCurrRatio);
                myMap.remove(SegregationSimulation.RED_PERCENTAGE,SegregationSimulation.BLUE_PERCENTAGE);
                myMap.put(SegregationSimulation.RED_PERCENTAGE,redCurrVal);
                myMap.put(SegregationSimulation.BLUE_PERCENTAGE,blueCurrVal);
                mySimulation.updateNewParams(myMap);
            }
        });
        super.addToStackPane(myRaceOne,myRaceOneSlider);

        myEmptySlider = new Slider(0,1.0,emptyCurrVal);
        myEmptySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myEmptySlider.setValue(myEmptySlider.getValue());
                emptyCurrVal = myEmptySlider.getValue();
                myMap.remove(SegregationSimulation.EMPTY_PERCENTAGE);
                myMap.put(SegregationSimulation.EMPTY_PERCENTAGE,myEmptySlider.getValue());
                mySimulation.updateNewParams(myMap);
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

    private void setUpInitialValues(){
        double redCount = 0;
        double emptyCount = 0;
        double blueCount = 0;

        for (Cell c: mySimulation.getMyGrid().getCells()){ ;
            if (c.getMyColor() == Color.RED)
                redCount++;
            else if (c.getMyColor() == Color.BLUE)
                blueCount++;
            else if (c.getMyColor() == Color.WHITE)
                emptyCount++;
        }
        redCurrVal = redCount/(mySimulation.getMyGrid().getCells().size());
        blueCurrVal =  blueCount/(mySimulation.getMyGrid().getCells().size());
        emptyCurrVal = emptyCount/(mySimulation.getMyGrid().getCells().size());
        myEmptySlider = new Slider(0,1,emptyCurrVal);
    }
}