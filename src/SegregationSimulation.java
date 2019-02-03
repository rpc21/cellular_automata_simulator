import java.util.List;

public class SegregationSimulation extends Simulation{

    public static final String RED_PERCENTAGE = "redPercentage";
    public static final String BLUE_PERCENTAGE = "bluePercentage";
    public static final String EMPTY_PERCENTAGE = "emptyPercentage";
    public static final String THRESHOLD = "threshold";

    public SegregationSimulation(int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
    }


    @Override
    public void simulate(double simulationSpeed) {

    }

    @Override
    public boolean isOver() {
        for (Cell cell: myGrid.getCells()){
            if (!((SegregationCell) cell).isSatisfied()){
                return false;
            }
        }
        return true;
    }

}
