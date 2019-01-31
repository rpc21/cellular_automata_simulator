import java.awt.*;
import java.util.List;

public class PercolationCell extends Cell {

    private static int[] PERCOLATION_CELL_ROW_NEIGHBORS = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static int[] PERCOLATION_CELL_COL_NEIGHBORS = {-1, -1, -1, 0, 0, 1, 1,1};


    public PercolationCell(Location loc, PercolationState startingState, Grid grid){
        super(loc, startingState, grid);
    }

    @Override
    public void calculateNewState() {
        if (myCurrentState == PercolationState.PERCOLATED || myCurrentState == PercolationState.BLOCKED) {
            myNextState = myCurrentState;
        }
        else if (nextToPercolatedCell()){
            myNextState = PercolationState.PERCOLATED;
        }
        else {
            myNextState = PercolationState.OPEN;
        }
    }

    private boolean nextToPercolatedCell() {
        List<Location> neighborLocations = myGrid.getValidNeighbors(myLocation, PERCOLATION_CELL_ROW_NEIGHBORS,
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
        return myCurrentState == PercolationState.PERCOLATED;
    }

    @Override
    public String toString() {
        return myCurrentState.toString();
    }

    @Override
    public Color getMyColor() {
        return myCurrentState.getMyCellColor();
    }
}
