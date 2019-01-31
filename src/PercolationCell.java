
import java.util.List;

public class PercolationCell extends Cell {


    private static final int BLOCKED = 150004;
    private static final int OPEN = 150003;
    private static final int PERCOLATED = 150002;
    private static int[] PERCOLATION_CELL_ROW_NEIGHBORS = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static int[] PERCOLATION_CELL_COL_NEIGHBORS = {-1, -1, -1, 0, 0, 1, 1,1};


    public PercolationCell(Location loc, int startingState, Grid grid){
        myCurrentState = startingState;
        myGrid = grid;
        myLocation = loc;
        myNextState = 0;
    }

    @Override
    public void calculateNewState() {
        if (myCurrentState == PERCOLATED || myCurrentState == BLOCKED) {
            myNextState = myCurrentState;
        }
        else if (nextToPercolatedCell()){
            myNextState = PERCOLATED;
        }
        else {
            myNextState = OPEN;
        }
    }

    private boolean nextToPercolatedCell() {
        List<Location> neighborLocations = myGrid.getLocations(myLocation, PERCOLATION_CELL_ROW_NEIGHBORS,
                PERCOLATION_CELL_COL_NEIGHBORS);
        for (Location loc: neighborLocations){
            PercolationCell cell = (PercolationCell) myGrid.get(loc);
            if (cell.isPercolated()){
                return true;
            }
        }
        return false;
    }

    public boolean isPercolated() {
        return myCurrentState == PERCOLATED;
    }

    @Override
    public String toString() {
        if (myCurrentState == BLOCKED){
            return "B";
        }
        else if (isPercolated()){
            return "P";
        }
        else {
            return "O";
        }
    }
}
