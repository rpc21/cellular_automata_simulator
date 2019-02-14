import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for the percolation simulation, overrides the methods in Simulation in a Percolation-specific way
 */
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

    /**
     * Constructor for the percolation simulation.
     * @param params specifies grid size and type, neighbors and percentages
     * @param grid grid to be used in the simulation
     */
    public PercolationSimulation(Map<String, Double> params, Grid grid){
        super(params, grid);
    }

    /**
     * @return true if simulation is over (can no longer change state)
     */
    @Override
    public boolean isOver(){
        for (Cell cell : myGrid.getCells()){
            if (cell.isEmpty() && ((PercolationCell) cell).nextToPercolatedCell()){
                return false;
            }
        }
        return true;
    }

    /**
     * @return Percolation simulation name
     */
    @Override
    public String getMyName() {
        return Simulation.PERCOLATION_SIMULATION_NAME;
    }

    /**
     * @return list of the percentage fields to look for in the xml file
     */
    @Override
    public List<String> getPercentageFields() {
        return List.of(OPEN_PERCENTAGE, CLOSED_PERCENTAGE, PERCOLATED_PERCENTAGE);
    }

    /**
     * Updates the neighbors as a response to the change in neighbors from the GUI
     * @param styleProperties
     */
    @Override
    public void updateNeighbors(Map<String, String> styleProperties){
        super.updateNeighbors(styleProperties, NeighborsDefinitions.BOX_NEIGHBORS);
    }
}
