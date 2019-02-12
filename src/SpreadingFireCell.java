import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpreadingFireCell extends Cell {

    public static final double PROB_CATCH_DEFAULT_VALUE = 1.0D;

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

    public boolean isOnFire() {
        return myCurrentState == SpreadingFireState.FIRE;
    }

}
