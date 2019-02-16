import java.util.List;
import java.util.Map;

/**
 * Extension of Simulation used to implement the Wator simulation
 */
public class WatorSimulation extends Simulation {
    public static final String SHARK_PERCENTAGE = "sharkPercentage";
    public static final String FISH_PERCENTAGE = "fishPercentage";
    public static final String EMPTY_PERCENTAGE = "emptyPercentage";
    public static final String SHARK_BREED_TIME = "sharkBreedTime";
    public static final String FISH_BREED_TIME = "fishBreedTime";
    public static final String STARVE_TIME = "starveTime";
    public static final List<String> WATOR_DATA_FIELDS = List.of(
            SHARK_PERCENTAGE, FISH_PERCENTAGE, EMPTY_PERCENTAGE, SHARK_BREED_TIME, FISH_BREED_TIME, STARVE_TIME
    );

    /**
     * Constructor for Wator simulation
     * @param params wator specific parameters
     * @param grid grid for the simulation
     */
    public WatorSimulation(Map<String, Double> params, Grid grid){
        super(params, grid);
    }

    /**
     * Simulation is over if all the sharks and fishes die
     * @return true if simulation is over
     */
    @Override
    public boolean isOver() {
        return myGrid.getOccupiedLocations().isEmpty();
    }

    /**
     * Advances the grid from one iteration to the next
     */
    @Override
    public void updateGrid() {
        for (Location loc : myGrid.getOccupiedLocations()){
           ((WatorCell) myGrid.get(loc)).step();
        }
    }

    /**
     * @return name of the Wator Simulation
     */
    @Override
    public String getMyName() {
        return Simulation.WATOR_SIMULATION_NAME;
    }

    /**
     * Gets the percentage fields that configuration or visualization should look for related to Wator
     * @return List of percentage fields
     */
    @Override
    public List<String> getPercentageFields() {
        return List.of(SHARK_PERCENTAGE, FISH_PERCENTAGE, EMPTY_PERCENTAGE);
    }

    /**
     * Overrides the setting of initial states in a Wator specific way
     * @param initialStates 2-D array of strings specifying states
     * @param simulationType simulation name
     * @param parameters simulation specific parameters
     */
    @Override
    public void setInitialStates(String[][] initialStates, String simulationType, Map<String, Double> parameters) {
        for (int i = 0; i < getMyGrid().getNumRows(); i++){
            for (int j = 0; j < getMyGrid().getNumCols(); j++){
                Location thisLocation = new Location(i, j);
                Cell newCell = generateWATORSpecificCell(simulationType, thisLocation, initialStates[i][j],
                        myGrid, myNextGrid, parameters);
                System.out.println(newCell + " to be inserted at "+ i + ", "+j);
                System.out.println(newCell.getMyLocation().getRow()+", "+newCell.getMyLocation().getCol());
                getMyGrid().put(newCell.getMyLocation(), newCell);
            }
        }
        getMyGrid().printGrid();
    }

    private Cell generateWATORSpecificCell(String simulationType, Location thisLocation, String state, Grid grid,
                                           Grid nextGrid, Map<String, Double> parameters) {
        if (WatorState.valueOf(state) == WatorState.FISH){
            return new WatorFish(thisLocation, grid, nextGrid, parameters);
        }
        else if (WatorState.valueOf(state) == WatorState.SHARK){
            return new WatorShark(thisLocation, grid, nextGrid, parameters);
        }
        else{
            return new EmptyCell(thisLocation);
        }
    }

    /**
     * Updates neighbors with ADJACENT as the default
     * @param styleProperties map including the neighborsType and new neighborstype
     */
    @Override
    public void updateNeighbors(Map<String, String> styleProperties){
        super.updateNeighbors(styleProperties, NeighborsDefinitions.ADJACENT);
    }
}
