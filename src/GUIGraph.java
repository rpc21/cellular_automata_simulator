import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.List;

public class GUIGraph {
    private HashMap<String,XYChart.Series> myMap = new HashMap<>();
    private LineChart<Number,Number> myChart;
    private int currStep = 0;
    private static final int CHART_HEIGHT = 280;
    private static final int MAX_STEPS = 50;
    private static final double Y_STEP = 0.01;

    private static final String X_AXIS = "Cycle";
    private static final String Y_AXIS = "Cell Percentage";
    private static final String TITLE = "Cell Perecentage Distribution";

    public GUIGraph(Simulation mySim){
        NumberAxis xAxis = new NumberAxis(0,MAX_STEPS,1);
        xAxis.setLabel(X_AXIS);
        NumberAxis yAxis = new NumberAxis(0,1,Y_STEP);
        yAxis.setLabel(Y_AXIS);

        myChart = new LineChart<Number,Number>(xAxis,yAxis);
        myChart.setTitle(TITLE);
        for (String a: mySim.getMyPossibleStates()) {
            myMap.put(a, new XYChart.Series<>());
            myMap.get(a).setName(a);
            myMap.get(a).getData().add(new XYChart.Data(0,mySim.getInitialParams().get(a)));
            myChart.getData().add(myMap.get(a));
        }
        currStep++;
    }
    /**
     * This method sets parameters for the linechart's display and returns its display
     * type
     * @see LineChart
     * @return myChart to add to root node of the scene
     */
    public LineChart<Number,Number> getMyChart(){
        myChart.setMaxHeight(CHART_HEIGHT);
        myChart.setMinWidth(GUI.STAGE_SIZE * 1.0/GUI.SCALE);
        return myChart;
    }
    /**
     * This method sets parameters for the linechart's display and returns its display
     * type
     * @see LineChart
     */
    public void updateChart(List<Cell> myCells) {
        HashMap<String, Double> paramCountMap = new HashMap<>();
        for (String cellType : myMap.keySet())
            paramCountMap.put(cellType, 0.0);
        for (Cell c : myCells) {
            paramCountMap.put(c.getMyCurrentState().toString(),paramCountMap.get(c.getMyCurrentState().toString()) + 1.0);
        }
        for (String a: paramCountMap.keySet()) {
            paramCountMap.put(a, paramCountMap.get(a) / myCells.size());
            myMap.get(a).getData().add(new XYChart.Data(currStep, paramCountMap.get(a)));
        }
        currStep++;
    }


}
