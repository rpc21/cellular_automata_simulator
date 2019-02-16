import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that runs the SpreadingFire Simulation on the backend.  Extends Simulation abstract class
 */
public class SpreadingFireSimulation extends Simulation {
    public static final String TREE_PERCENTAGE = "treePercentage";
    public static final String EMPTY_PERCENTAGE = "emptyPercentage";
    public static final String FIRE_PERCENTAGE = "firePercentage";
    public static final String PROB_CATCH = "probCatch";
    public static final List<String> SPREADING_FIRE_DATA_FIELDS = List.of(
            PROB_CATCH, TREE_PERCENTAGE, EMPTY_PERCENTAGE, FIRE_PERCENTAGE
    );

    /**
     * Constructor for the SpreadingFireSimulation. Initializes the grid for the simulation to a BasicGrid of size
     * rows x cols
     * @param rows number of rows in the grid for the simulation
     * @param cols number of columns in the grid for the simulation
     */
    public SpreadingFireSimulation(HashMap<String, Double> params, int rows, int cols){
        super(params, rows, cols);
        setMyGrid(new BasicGrid(rows, cols));
    }

    /**
     * Constructor for the SpreadingFireSimulation
     * @param params parameters of the simulation
     * @param grid grid to be used in the simulation
     */
    public SpreadingFireSimulation(Map<String, Double> params, Grid grid){
        super(params, grid);
    }

    /**
     * If there are any fires, there is at least one more round
     * @return whether or not the simulation is over
     */
    @Override
    public boolean isOver() {
        for (Cell cell: myGrid.getCells()){
            if (((SpreadingFireCell) cell).isOnFire()){
                return false;
            }
        }
        return true;
    }

    /**
     * @return the name of the Spreading Fire Simulation
     */
    @Override
    public String getMyName() {
        return Simulation.SPREADING_FIRE_SIMULATION_NAME;
    }

    /**
     * Return the percentage fields to look for when parsing an XML file specifying a Spreading Fire simulation
     * @return list of the percentages strings to look for
     */
    @Override
    public List<String> getPercentageFields() {
        return List.of(TREE_PERCENTAGE, EMPTY_PERCENTAGE, FIRE_PERCENTAGE);
    }

    /**
     * Updates the neighbors of the cells, specifying ADJACENT as the default if something goes wrong
     * @param styleProperties map including the neighborsType and new neighborstype
     */
    @Override
    public void updateNeighbors(Map<String, String> styleProperties){
        super.updateNeighbors(styleProperties, NeighborsDefinitions.ADJACENT);
    }
}
