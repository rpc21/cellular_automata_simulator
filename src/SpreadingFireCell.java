import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.List;

public class SpreadingFireCell extends Cell {

    private static final int[] SPREADING_FIRE_ROW_NEIGHBORS = {-1, 1, 0, 0};
    private static final int[] SPREADING_FIRE_COL_NEIGHBORS = {0, 0, -1, 1};
    //Name of parameter -> probCatch

    public SpreadingFireCell(Location loc, SpreadingFireState startingState, Grid grid, HashMap<String, Double> parameters){
        super(loc, startingState, grid, parameters);
    }

    public SpreadingFireCell(Location location, int startingState, Grid grid, HashMap<String,
            Double> parameters){
        this(location, SpreadingFireState.values()[startingState], grid, parameters);
    }

    public SpreadingFireCell(Location loc, SpreadingFireState startingState, Grid grid){
        this(loc, startingState, grid, new HashMap<>());
        myParameters.put("probCatch", 1.0D);
    }

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
        return nextToTreeOnFire && (randomNumber <= myParameters.get("probCatch"));
    }

    public boolean isOnFire() {
        return myCurrentState == SpreadingFireState.FIRE;
    }

    /**
     * Return a String of length 1 representing the state of the cell
     * @return String of length 1 representing the state of the cell as defined in SpreadingFireState Enum
     */
    @Override
    public String toString() {
        return myCurrentState.getMyShortenedName();
    }

    /**
     * Return the color to display to represent the state of this cell
     * @return Color to display for this state as defined in GOLState Enum
     */
    @Override
    public Color getMyColor() {
        return myCurrentState.getMyCellColor();
    }
}
