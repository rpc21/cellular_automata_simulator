import java.util.HashMap;
import java.util.List;

public class PercolationSimulation extends Simulation {
    public static final String OPEN_PERCENTAGE = "openPercentage";
    public static final String CLOSED_PERCENTAGE = "closedPercentage";
    public static final String PERCOLATED_PERCENTAGE = "percolatedPercentage";
    public static final List<String> PERCOLATION_DATA_FIELDS = List.of(
            OPEN_PERCENTAGE, CLOSED_PERCENTAGE, PERCOLATED_PERCENTAGE
    );
    /**
     * Constructor for the PercolationSimulation. Initializes the grid for the simulation to a BasicGrid of size rows x
     * cols
     * @param rows number of rows in the grid for the simulation
     * @param cols number of columns in the grid for the simulation
     */
    public PercolationSimulation(HashMap<String, Double> params, int rows, int cols){
        super(params, rows, cols);
        setMyGrid(new BasicGrid(rows, cols));
    }


    @Override
    public void simulate(double simulationSpeed) {}

    @Override
    public boolean isOver(){
        for (Cell cell : myGrid.getCells()){
            if (cell.isEmpty() && ((PercolationCell) cell).nextToPercolatedCell()){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getMyName() {
        return Simulation.PERCOLATION_SIMULATION_NAME;
    }

}
