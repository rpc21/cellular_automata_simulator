import java.util.List;

public class SegregationSimulation extends Simulation{

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
