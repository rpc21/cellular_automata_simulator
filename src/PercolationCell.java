import javafx.scene.paint.Color;
import java.util.List;

/**
 * PercolationCell extends Cell and can be used in the PercolationSimulation.  Percolation cells change from OPEN to
 * PERCOLATED when a cell is open and one of its eight neighbors is percolated.  CLOSED cells cannot become open or
 * percolated.  The PercolationCell implements these rules for the Percolation Simulation and can communicate to the
 * Visualization what color to display for the cell based on the cell's state
 */
public class PercolationCell extends Cell {

    //Percolation cells take the state of their 4 diagonal neighbors and 4 adjacent neighbors when calculating new state
    private static int[] PERCOLATION_CELL_ROW_NEIGHBORS = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static int[] PERCOLATION_CELL_COL_NEIGHBORS = {-1, -1, -1, 0, 0, 1, 1,1};


    /**
     * Constructor for PercolationCell taking in a PercolationState as a CellState
     * @param loc location of the cell in the grid
     * @param startingState the starting state of the cell (can be PERCOLATED, OPEN or CLOSED
     * @param grid grid used for the simulation (that will contain the cell
     */
    public PercolationCell(Location loc, PercolationState startingState, Grid grid){
        super(loc, startingState, grid);
    }

    /**
     * Constructor for PercolationCell taking in an int that gets converted to a PercolationState
     * @param loc location of the cell in the grid
     * @param startingState an int corresponding to the desired PercolationState
     * @param grid grid used for the simulation (that will contain the cell)
     */
    public PercolationCell(Location loc, int startingState, Grid grid){
        this(loc, PercolationState.values()[startingState], grid);
    }

    /**
     * Calculates the new state of the PercolationCell based on the Percolation rules, the state of the cell and the
     * state of the cell's neighbors
     */
    @Override
    public void calculateNewState() {
        if (myCurrentState == PercolationState.PERCOLATED || myCurrentState == PercolationState.CLOSED) {
            myNextState = myCurrentState;
        }
        else if (nextToPercolatedCell()){
            myNextState = PercolationState.PERCOLATED;
        }
        else {
            myNextState = PercolationState.OPEN;
        }
    }

    /**
     * Checks if a cell is next to a percolated cell
     * Useful for determining when to stop the simulation
     * @return true if next to a percolated cell, false otherwise
     */
    public boolean nextToPercolatedCell() {
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

    /**
     * Useful method to use with getEmptyLocations for Grids to allow you to easily get all the open locations in the
     * Percolation simulation.  Used to check if simulation is over
     * @return true if the cell is marked as OPEN
     */
    public boolean isEmpty(){
        return myCurrentState == PercolationState.OPEN;
    }

    /**
     * Returns if the cell is percolated
     * @return if the cell is percolated
     */
    private boolean isPercolated() {
        return myCurrentState == PercolationState.PERCOLATED;
    }

    /**
     * Return a String of length 1 representing the state of the cell
     * @return String of length 1 representing the state of the cell as defined in PercolationState Enum
     */
    @Override
    public String toString() {
        return myCurrentState.toString();
    }

    /**
     * Return the color to display to represent the state of this cell
     * @return Color to display for this state as defined in PercolationState Enum
     */
    @Override
    public Color getMyColor() {
        return myCurrentState.getMyCellColor();
    }
}
