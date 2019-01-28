import java.util.List;

public class SpreadingFireCell extends Cell {

    private static final int ON_FIRE = 140002;
    private static final int TREE = 140003;
    private static final int EMPTY = 140004;

    private double probCatch;

    public SpreadingFireCell(Location loc, int startingState, Grid grid){
        myCurrentState = startingState;
        myGrid = grid;
        myLocation = loc;
        myNextState = 0;
        probCatch = 1.0D;
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
        List<Location> neighborLocations = myGrid.getValidAdjacentLocations(myLocation);
        boolean nextToTreeOnFire = false;
        for (Location loc : neighborLocations){
            SpreadingFireCell cell = (SpreadingFireCell) myGrid.get(loc);
            if (cell.isOnFire()){
                nextToTreeOnFire = true;
            }
        }
        double randomNumber = Math.random();
        return nextToTreeOnFire && randomNumber <= probCatch;
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
