import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpreadingFireCell extends Cell {

    private static final int ON_FIRE = 140002;
    private static final int TREE = 140003;
    private static final int EMPTY = 140004;
    private static final int[] SPREADING_FIRE_ROW_NEIGHBORS = {-1, 1, 0, 0};
    private static final int[] SPREADING_FIRE_COL_NEIGHBORS = {0, 0, -1, 1};


    public SpreadingFireCell(Location loc, int startingState, Grid grid, HashMap<String, Double> parameters){
        myCurrentState = startingState;
        myGrid = grid;
        myLocation = loc;
        myNextState = 0;
        myParameters = parameters;
    }

    public SpreadingFireCell(Location loc, int startingState, Grid grid){
        this(loc, startingState, grid, new HashMap<>());
        myParameters.put("probCatch", 1.0D);
    }

    @Override
    public void calculateNewState() {
        if (myCurrentState == ON_FIRE || myCurrentState == EMPTY) {
            myNextState = EMPTY;
        }
        else if (catchesFire()){
            myNextState = ON_FIRE;
        }
        else {
            myNextState = TREE;
        }
    }

    private boolean catchesFire() {
        List<Location> neighborLocations = myGrid.getLocations(myLocation, SPREADING_FIRE_ROW_NEIGHBORS, SPREADING_FIRE_COL_NEIGHBORS);
        boolean nextToTreeOnFire = false;
        for (Location loc : neighborLocations){
            SpreadingFireCell cell = (SpreadingFireCell) myGrid.get(loc);
            if (cell.isOnFire()){
                nextToTreeOnFire = true;
            }
        }
        double randomNumber = Math.random();
        return nextToTreeOnFire && randomNumber <= myParameters.get("probCatch");
    }

    public boolean isOnFire() {
        return myCurrentState == ON_FIRE;
    }

    @Override
    public String toString() {
        if (myCurrentState == EMPTY){
            return "E";
        }
        else if (isOnFire()){
            return "F";
        }
        else {
            return "T";
        }
    }
}
