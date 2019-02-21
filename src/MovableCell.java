import java.util.Map;

/** ADAPTED from WATORWORLD CODE
 * https://www2.cs.duke.edu/courses/spring19/compsci308/assign/02_cellsociety/nifty/scott-wator-world/SourceCode/Actor.java
 * Moves this cell to a new location. If there is another cell at the
 * given location, it is removed.
 * Precondition: (1) This cell is contained in a grid (2)
 * newLocation is valid in the grid
 *
 * @author Dima Fayyad
 * @author Ryan Culhane
 */
public abstract class MovableCell extends Cell {

    protected Location myNextLocation;

//    public MovableCell(Location location, CellState initialState, Grid grid, Grid nextGrid) {
//        super(location, initialState, grid, nextGrid);
//        this.myNextLocation = null;
//    }

    /**
     * Class constructor for a cell which has methods that allow it to move in the grid
     * @param loc the location of the cell in the grid
     * @param startingState the initial state of the cell
     * @param grid the grid in which the cell exists
     * @param nextGrid the next grid in which the cell's next state will be held when the next iteration of the simulation is being calculated
     * @param parameters parameters that affect the cell's rules for updating itself
     */
    public MovableCell(Location loc, CellState startingState, Grid grid, Grid nextGrid, Map<String,
                           Double> parameters) {
        super(loc, startingState, grid, nextGrid, parameters);
        this.myNextLocation = null;
    }

    /**
     * Finds swaps cell with the cell currently in its next location
     * swaps two cells in the grid
     * Used in simulations where cells are allowed to move in the grid
     */
    public void swapLocations() {
        if (myGrid == null)
            throw new IllegalStateException("This cell is not in the grid.");
        if (!myGrid.isValid(myNextLocation)) {
            throw new IllegalArgumentException("Location " + myNextLocation
                    + " is not valid.");
        }

        if (myNextLocation.equals(myLocation)) {
            return;
        }
        Cell other = myGrid.get(myNextLocation);
        if (other != null)
            myGrid.put(myLocation, other);
            other.myLocation = myLocation;
        myLocation = myNextLocation;
        myGrid.put(myLocation, this);
    }


    /**
     * Sets the next location of the cell so that when cells are swapped their next positions are available
     * @param newLoc
     */
    public void setMyNextLocation(Location newLoc) {
        this.myNextLocation = newLoc;
    }
}
