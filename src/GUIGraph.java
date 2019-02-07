import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GUIGraph {
    private HashMap<String,XYChart.Series> myMap = new HashMap<>();
    private LineChart<Number,Number> myChart;
    private int currStep = 0;

    public GUIGraph(Simulation mySim){
        NumberAxis xAxis = new NumberAxis(0,50,1);
        xAxis.setLabel("Cycle");
        NumberAxis yAxis = new NumberAxis(0,1,0.01);
        yAxis.setLabel("Cell Percentage");

        myChart = new LineChart<Number,Number>(xAxis,yAxis);
        myChart.setTitle("Cell Percentage Distribution");
        for (String a: mySim.getPercentageFields()) {
            myMap.put(a, new XYChart.Series<>());
            myMap.get(a).setName(a);
            myMap.get(a).getData().add(new XYChart.Data(0,mySim.getInitialParams().get(a)));
            myChart.getData().add(myMap.get(a));
        }
        currStep++;
    }

    public LineChart<Number,Number> getMyChart(){
        myChart.setMaxHeight(270);
        myChart.setMinWidth(GUI.STAGE_SIZE *.92);
        return myChart;
    }

    public void updateChart(List<Cell> myCells) {
        //would be nice to have a method that accesses list of this sim's cell states
        HashMap<String, Double> paramCountMap = new HashMap<>();
        for (String cellType : myMap.keySet())
            paramCountMap.put(cellType, 0.0);


        for (Cell c : myCells) {
            if (c.getMyColor() == GOLState.DEAD.getMyCellColor()) {
                paramCountMap.put(GOLSimulation.DEAD_PERCENTAGE, paramCountMap.get(GOLSimulation.DEAD_PERCENTAGE) + 1.0);
            } else {
                paramCountMap.put(GOLSimulation.ALIVE_PERCENTAGE, paramCountMap.get(GOLSimulation.ALIVE_PERCENTAGE) + 1.0);
            }
        }
        paramCountMap.put(GOLSimulation.ALIVE_PERCENTAGE, paramCountMap.get(GOLSimulation.ALIVE_PERCENTAGE) / myCells.size());
        paramCountMap.put(GOLSimulation.DEAD_PERCENTAGE, paramCountMap.get(GOLSimulation.DEAD_PERCENTAGE) / myCells.size());


        myMap.get(GOLSimulation.ALIVE_PERCENTAGE).getData().add(new XYChart.Data(currStep, paramCountMap.get(GOLSimulation.ALIVE_PERCENTAGE)));
        myMap.get(GOLSimulation.DEAD_PERCENTAGE).getData().add(new XYChart.Data(currStep, paramCountMap.get(GOLSimulation.DEAD_PERCENTAGE)));
        currStep++;
    }


}
