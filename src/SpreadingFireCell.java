import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.List;

public class SpreadingFireCell extends Cell {

    private static final int[] SPREADING_FIRE_ROW_NEIGHBORS = {-1, 1, 0, 0};
    private static final int[] SPREADING_FIRE_COL_NEIGHBORS = {0, 0, -1, 1};
    //Name of parameter -> probCatch

    public SpreadingFireCell(Location location, SpreadingFireState initialState, Grid currentGrid, Grid nextGrid, HashMap<String,
            Double> parameters){
        super(location, initialState, currentGrid, nextGrid, parameters);
    }

    public SpreadingFireCell(Location location, int startingState, Grid grid, Grid nextGrid, HashMap<String,
            Double> parameters){
        this(location, SpreadingFireState.values()[startingState], grid, nextGrid, parameters);
    }

    public SpreadingFireCell(Location loc, SpreadingFireState startingState, Grid grid, Grid nextGrid){
        this(loc, startingState, grid, nextGrid, new HashMap<>());
        myParameters.put(SpreadingFireSimulation.PROB_CATCH, 1.0D);
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
        List<Location> neighborLocations = myGrid.getValidNeighbors(myLocation, SPREADING_FIRE_ROW_NEIGHBORS, SPREADING_FIRE_COL_NEIGHBORS);
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
