import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SegregationSimulation extends Simulation{

    public static final String RED_PERCENTAGE = "redPercentage";
    public static final String BLUE_PERCENTAGE = "bluePercentage";
    public static final String EMPTY_PERCENTAGE = "emptyPercentage";
    public static final String THRESHOLD = "threshold";

    public static final List<String> SEGREGATION_DATA_FIELDS = List.of(
            RED_PERCENTAGE, BLUE_PERCENTAGE, EMPTY_PERCENTAGE, THRESHOLD
    );


    /**
     * Constructor for the SegregationSimulation. Initializes the grid for the simulation using the parameters in the map params
     * @param params the basic parameters associated with the simulation, which are listed in SEGREGATION_DATA_FIELDS
     */
    public SegregationSimulation(Map<String, Double> params, Grid grid){
        super(params, grid);
    }

    /**
     * Determines if the simulation is ready to be ended
     * @return true when the simulation is over
     */
    @Override
    public boolean isOver() {
        for (Cell cell: myGrid.getCells()){
            if (!((SegregationCell) cell).isSatisfied()){
                return false;
            }
        }
        return true;
    }

    /**
     * Used to get the string value of the name of the simulation
     * Used to keep consistency in simulation naming
     * @return the string value of the name of the simulation
     */
    @Override
    public String getMyName() {
        return Simulation.SEGREGATION_SIMULATION_NAME;
    }

    /**
     * Gets the fields in the simulation data fields that determine percentages of cell types
     * Used when the initial states for the simulation should be determine based on the specified
     * population percentages
     * @return fields in the simulation data fields that determine percentages of cell types
     */
    @Override
    public List<String> getPercentageFields() {
        return List.of(RED_PERCENTAGE, BLUE_PERCENTAGE, EMPTY_PERCENTAGE);
    }

    /**
     * Used when the user changes style properties from the GUI
     * @param styleProperties the new style properties: the neighbors type, grid line option, shape, edge type for grid
     */
    @Override
    public void updateNeighbors(Map<String, String> styleProperties){
        super.updateNeighbors(styleProperties, NeighborsDefinitions.BOX_NEIGHBORS);
    }
}
