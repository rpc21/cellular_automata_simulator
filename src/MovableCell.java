import java.util.Map;

public abstract class MovableCell extends Cell {
    /** ADAPTED from WATORWORLD CODE
     * https://www2.cs.duke.edu/courses/spring19/compsci308/assign/02_cellsociety/nifty/scott-wator-world/SourceCode/Actor.java
     * Moves this actor to a new location. If there is another actor at the
     * given location, it is removed. <br />
     * Precondition: (1) This actor is contained in a grid (2)
     * <code>newLocation</code> is valid in the grid of this actor
     * @param newLocation the new location
     */
    protected Location myNextLocation;

    public MovableCell(Location location, CellState initialState, Grid grid, Grid nextGrid) {
        super(location, initialState, grid, nextGrid);
        this.myNextLocation = null;
    }

    public MovableCell(Location loc, CellState startingState, Grid grid, Grid nextGrid, Map<String,
                           Double> parameters) {
        super(loc, startingState, grid, nextGrid, parameters);
        this.myNextLocation = null;
    }

    public void swapLocations() {
        if (myGrid == null)
            throw new IllegalStateException("This cell is not in the grid.");
//        if (myGrid.get(myLocation) != this) {
//            throw new IllegalStateException(
//                    "The grid contains a different actor at location "
//                            + myLocation + ".");
//        }
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

    public void setMyNextLocation(Location newLoc) {
        this.myNextLocation = newLoc;
    }
}
