import java.util.List;

public class SpreadingFireCell extends Cell {

    private static final int ON_FIRE = 140002;
    private static final int TREE = 140003;
    private static final int EMPTY = 140004;

    private int myCurrentState;
    private int myNextState;
    private Location myLocation;
    private Grid myGrid;
    private double probCatch;

    public SpreadingFireCell(Location loc, int startingState, Grid grid){
        setMyGrid(grid);
        setMyLocation(loc);
        setMyCurrentState(startingState);
        setMyNextState(0);
        probCatch = 1.0D;
    }

    @Override
    public void calculateNewState() {
        if (getMyCurrentState() == ON_FIRE || getMyCurrentState() == EMPTY) {
            setMyNextState(EMPTY);
        }
        else if (catchesFire()){
            setMyNextState(ON_FIRE);
        }
        else {
            setMyNextState(TREE);
        }
    }

    private boolean catchesFire() {
        List<Location> neighborLocations = getMyGrid().getValidAdjacentLocations(getMyLocation());
        boolean nextToTreeOnFire = false;
        for (Location loc : neighborLocations){
            SpreadingFireCell cell = (SpreadingFireCell) getMyGrid().get(loc);
            if (cell.isOnFire()){
                nextToTreeOnFire = true;
            }
        }
        double randomNumber = Math.random();
        return nextToTreeOnFire && randomNumber <= probCatch;
    }

    public boolean isOnFire() {
        return getMyCurrentState() == ON_FIRE;
    }

    @Override
    public String toString() {
        if (getMyCurrentState() == EMPTY){
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
