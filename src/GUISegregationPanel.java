
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;
import java.util.HashMap;

public class GUISegregationPanel extends GUISimulationPanel {
    private Spinner<Integer> myThresholdSpinner = new Spinner<>();
    private Text myThresh;
    private Spinner<Integer> myRaceOneSpinner;
    private Text myRaceOne;
    private Spinner<Integer> myRaceTwoSpinner;
    private Text myRaceTwo;
    private Spinner<Integer> myEmptySpinner;
    private Text myEmpty;
    private HashMap<String,Double>  myMap = new HashMap<String,Double>();
//    private int redCurrRatio;
//    int blueCurrRatio;

    public GUISegregationPanel(Simulation mySim){
        super(mySim);
        for (String paramName: mySim.getInitialParams().keySet() )
            myMap.put(new String(paramName), new Double(mySim.getInitialParams().get(paramName)));

        myThresholdSpinner = setUpSpinner(0,100,(int)(myMap.get(SegregationSimulation.THRESHOLD)*100.0));
        myRaceOneSpinner = setUpSpinner(0,100,(int)(myMap.get(SegregationSimulation.RED_PERCENTAGE)*100.0));
        myRaceTwoSpinner = setUpSpinner(0,100,(int)(myMap.get(SegregationSimulation.BLUE_PERCENTAGE)*100.0));
        myEmptySpinner = setUpSpinner(0,100,(int)(myMap.get(SegregationSimulation.EMPTY_PERCENTAGE)*100.0));

        //toFraction(myMap.get(SegregationSimulation.RED_PERCENTAGE)/myMap.get(SegregationSimulation.BLUE_PERCENTAGE));

        myThresh = setUpLabel("Threshold %");
        myEmpty = setUpLabel("Empty %");
        myRaceOne = setUpLabel("Red %");
        myRaceTwo = setUpLabel("Blue %");

        super.addToStackPane(myThresh,myThresholdSpinner);
        super.addToStackPane(myRaceOne,myRaceOneSpinner);
        super.addToStackPane(myRaceTwo,myRaceTwoSpinner);
        super.addToStackPane(myEmpty,myEmptySpinner);


    }

//    private void toFraction(double x) {
//        final double eps = 0.01;
//        int pfound = (int) Math.round(x);
//        int qfound = 1;
//        double errorfound = Math.abs(x - pfound);
//        for (int q = 2; q < 100 && errorfound > eps; ++q) {
//            int p = (int) (x * q);
//            for (int i = 0; i < 2; ++i) { // below and above x
//                double error = Math.abs(x - ((double) p / q));
//                if (error < errorfound) {
//                    pfound = p;
//                    qfound = q;
//                    errorfound = error;
//                }
//                ++p;
//            }
//        }
//        redCurrRatio = pfound;
//        blueCurrRatio = qfound;
//    }

    public HashMap<String,Double> getMyParams(){
        myMap.put(SegregationSimulation.THRESHOLD,1.0*myThresholdSpinner.getValue()/100);
        myMap.put(SegregationSimulation.EMPTY_PERCENTAGE, 1.0 * myEmptySpinner.getValue() /100);
        myMap.put(SegregationSimulation.RED_PERCENTAGE,1.0 *myRaceOneSpinner.getValue()/100);
        myMap.put(SegregationSimulation.BLUE_PERCENTAGE, 1.0 *myRaceTwoSpinner.getValue()/100);
        return myMap;
    }
}