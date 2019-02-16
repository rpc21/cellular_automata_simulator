import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class of the cells that are used in a SpreadingFire simulation.  This class implements the rules of the
 * SpreadingFire simulation
 */
public class SpreadingFireCell extends Cell {

    public static final double PROB_CATCH_DEFAULT_VALUE = 1.0D;

    /**
     * Constructor for the Spreading Fire Cell
     * @param location location of the cell
     * @param initialState initial state of the cell
     * @param currentGrid current grid
     * @param nextGrid next grid
     * @param parameters specific parameters
     */
    public SpreadingFireCell(Location location, SpreadingFireState initialState, Grid currentGrid, Grid nextGrid, Map<String,
                Double> parameters){
        super(location, initialState, currentGrid, nextGrid, parameters);
//        myNeighbors = NeighborsDefinitions.ADJACENT;
    }

    @Deprecated
    public SpreadingFireCell(Location location, int startingState, Grid grid, Grid nextGrid, HashMap<String,
            Double> parameters){
        this(location, SpreadingFireState.values()[startingState], grid, nextGrid, parameters);
    }

    @Deprecated
    public SpreadingFireCell(Location loc, SpreadingFireState startingState, Grid grid, Grid nextGrid){
        this(loc, startingState, grid, nextGrid, new HashMap<>());
        myParameters.put(SpreadingFireSimulation.PROB_CATCH, PROB_CATCH_DEFAULT_VALUE);
    }

    /**
     * Calculates the new state of the SpreadingFireCell based on the SpreadingFire rules, the state of the cell and the
     * state of the cell's neighbors
     */
    @Override
    public void calculateNewState() {
        if (myCurrentState == SpreadingFireState.FIRE || myCurrentState == SpreadingFireState.EMPTY) {
            myNextState = SpreadingFireState.EMPTY;
        }
        else if (catchesFire()){
            myNextState = SpreadingFireState.FIRE;
        }
        else {
            myNextState = SpreadingFireState.TREE;
        }
    }

    private boolean catchesFire() {
        List<Location> neighborLocations = myGrid.getValidNeighbors(myLocation, myNeighbors);
        boolean nextToTreeOnFire = false;
        for (Location loc : neighborLocations){
            SpreadingFireCell cell = (SpreadingFireCell) myGrid.get(loc);
            if (cell.isOnFire()){
                nextToTreeOnFire = true;
            }
        }
        double randomNumber = Math.random();
        return nextToTreeOnFire && (randomNumber <= myParameters.get(SpreadingFireSimulation.PROB_CATCH));
    }

    /**
     * @return true if cell is on fire
     */
    public boolean isOnFire() {
        return myCurrentState == SpreadingFireState.FIRE;
    }

}
