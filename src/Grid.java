import java.util.ArrayList;
import java.util.List;

public interface Grid {
    //https://www2.cs.duke.edu/courses/spring19/compsci308/assign/02_cellsociety/nifty/scott-wator-world/SourceCode/Grid.java
    /**
     * Returns the number of rows in this grid.
     * @return the number of rows, or -1 if this grid is unbounded
     */
    int getNumRows();

    /**
     * Returns the number of columns in this grid.
     * @return the number of columns, or -1 if this grid is unbounded
     */
    int getNumCols();

    /**
     * Checks whether a location is valid in this grid. <br />
     * Precondition: <code>loc</code> is not <code>null</code>
     * @param loc the location to check
     * @return <code>true</code> if <code>loc</code> is valid in this grid,
     * <code>false</code> otherwise
     */
    boolean isValid(Location loc);

    /**
     * Puts an object at a given location in this grid. <br />
     * Precondition: (1) <code>loc</code> is valid in this grid (2)
     * <code>obj</code> is not <code>null</code>
     * @param loc the location at which to put the object
     * @param obj the new object to be added
     * @return the object previously at <code>loc</code> (or <code>null</code>
     * if the location was previously unoccupied)
     */
    void put(Location loc, Cell obj);

    /**
     * Removes the object at a given location from this grid. <br />
     * Precondition: <code>loc</code> is valid in this grid
     * @param loc the location of the object that is to be removed
     * @return the object that was removed (or <code>null<code> if the location
     *  is unoccupied)
     */
    void remove(Location loc);

    /**
     * Returns the object at a given location in this grid. <br />
     * Precondition: <code>loc</code> is valid in this grid
     * @param loc a location in this grid
     * @return the object at location <code>loc</code> (or <code>null<code>
     *  if the location is unoccupied)
     */
    Cell get(Location loc);

    /**
     * Gets the locations in this grid that contain objects.
     * @return an array list of all occupied locations in this grid
     */
    ArrayList<Location> getOccupiedLocations();

    /**
     * Gets the valid locations adjacent to a given location in all eight
     * compass directions (north, northeast, east, southeast, south, southwest,
     * west, and northwest). <br />
     * Precondition: <code>loc</code> is valid in this grid
     * @param loc a location in this grid
     * @return an array list of the valid locations adjacent to <code>loc</code>
     * in this grid
     */
//    ArrayList<Location> getValidAdjacentLocations(Location loc);

//    ArrayList<Location> getValidBoxLocations(Location loc);

    /**
     * Gets the valid empty locations adjacent to a given location in all eight
     * compass directions (north, northeast, east, southeast, south, southwest,
     * west, and northwest). <br />
     * Precondition: <code>loc</code> is valid in this grid
     * @param loc a location in this grid
     * @return an array list of the valid empty locations adjacent to
     * <code>loc</code> in this grid
     */
    ArrayList<Location> getEmptyAdjacentLocations(Location loc);

    /**
     * Gets the valid occupied locations adjacent to a given location in all
     * eight compass directions (north, northeast, east, southeast, south,
     * southwest, west, and northwest). <br />
     * Precondition: <code>loc</code> is valid in this grid
     * @param loc a location in this grid
     * @return an array list of the valid occupied locations adjacent to
     * <code>loc</code>Â in this grid
     */
    ArrayList<Location> getOccupiedAdjacentLocations(Location loc);

    /**
     * Gets the neighboring occupants in all eight compass directions (north,
     * northeast, east, southeast, south, southwest, west, and northwest).
     * <br />
     * Precondition: <code>loc</code> is valid in this grid
     * @param loc a location in this grid
     * @return returns an array list of the objects in the occupied locations
     * adjacent to <code>loc</code> in this grid
     */
    ArrayList<Cell> getNeighbors(Location loc);

    /**
     * Returns the a list of Cells that are in the grid while they underlying implementation of the grid remains hidden
     * @return
     */
    List<Cell> getCells();

    /**
     * Return the Location of the neighbors of loc as specified by deltaRow and deltaCol that are valid locations in
     * the grid that is being used
     * @param loc location of the cell whose neighbors you want
     * @param deltaRow the row offsets from loc
     * @param deltaCol the col offsets from loc
     * @return ArrayList of the validNeighbor Locations of loc
     */
    ArrayList<Location> getValidNeighbors(Location loc, int[] deltaRow, int[] deltaCol);

    /**
     * Prints the string representation of the grid using the toStrings defined in the cells
     * Useful for debugging the backend
     */
    void printGrid();
}
