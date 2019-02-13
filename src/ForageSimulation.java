import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForageSimulation extends Simulation {

    public static final String MAX_FOOD_PHEROMONES = "maxFoodPheromones";
    public static final String MAX_HOME_PHEROMONES = "maxHomePheromones";
    public static final String NUMBER_OF_ANTS = "numberOfAnts";
    private static final String EMPTY_PERCENTAGE = "emptyPercentage";
    private static final String OBSTACLE_PERCENTAGE = "obstaclePercentage";
    public static final List<String> FORAGE_DATA_FIELDS = List.of(MAX_FOOD_PHEROMONES, MAX_HOME_PHEROMONES,
            NUMBER_OF_ANTS, EMPTY_PERCENTAGE, OBSTACLE_PERCENTAGE);
    private static final int NEST_ROW = 0;
    private static final int NEST_COLUMN = 0;

    /**
     * Default constructor so it doesn't get mad
     */
    public ForageSimulation(){
        super();
    }

    /**
     * Constructor for the ForageSimulation, calls super constructor
     * @param params specific parameters for Forage simulation
     * @param grid grid being used in simulation
     */
    public ForageSimulation(Map<String, Double> params, Grid grid){
        super(params, grid);
    }

    /**
     * Creates the next iteration of the grid in the myNextGrid field and then sets myGrid to myNextGrid
     */
    @Override
    public void updateGrid() {
        myNextGrid = new GridFactory().generateGrid(myGrid);
        copyMyGrid();
        for (Cell patch: myGrid.getCells()){
            ((ForagePatch) patch).updateState(myNextGrid);
        }
        myGrid = myNextGrid;
    }

    private void copyMyGrid() {
        for (Cell patch : myGrid.getCells()){
            myNextGrid.put(patch.getMyLocation(), new ForagePatch((ForagePatch) patch, myNextGrid));
        }
    }

    /**
     * Simulation does not have an end point
     * @return false
     */
    @Override
    public boolean isOver() {
        return false;
    }

    /**
     *
     * @return name of the simulation
     */
    @Override
    public String getMyName() {
        return Simulation.FORAGE_SIMULATION_NAME;
    }

    /**
     *
     * @return percentages relevant for setting up the simulation (what to look for in XML parser)
     */
    @Override
    public List<String> getPercentageFields() {
        return List.of(OBSTACLE_PERCENTAGE,EMPTY_PERCENTAGE);
    }

    /**
     * Updates the neighbors of the cells, default are ADJACENT
     * @param styleProperties
     */
    @Override
    public void updateNeighbors(Map<String, String> styleProperties){
        super.updateNeighbors(styleProperties, NeighborsDefinitions.ADJACENT);
    }

    /**
     * Sets the initial states of the cells, ensures NEST and FOOD are in the appropriate places
     * @param initialStates initial states
     * @param simulationType forage simulation
     * @param parameters specific parameters
     */
    @Override
    public void setInitialStates(String[][] initialStates, String simulationType, Map<String, Double> parameters) {
        super.setInitialStates(initialStates, simulationType, parameters);
        setFoodAndNest(initialStates);
    }

    private void setFoodAndNest(String[][] initialStates) {
        Location nestLocation = new Location(NEST_ROW, NEST_COLUMN);
        myGrid.put(nestLocation, new ForagePatch(nestLocation, ForageState.NEST, myGrid, myNextGrid, myParameters));
        Location foodLocation = new Location(initialStates.length-1, initialStates[0].length-1);
        myGrid.put(foodLocation, new ForagePatch(foodLocation, ForageState.FOOD, myGrid, myNextGrid, myParameters));
    }
}
