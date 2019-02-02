import java.util.ArrayList;
import java.util.List;

/**
 * The BasicGrid implements the Grid interface, implementing the functionality that all grids should have. In the
 * BasicGrid, cells cannot wrap around to the other side of the grid.  The BasicGrid has an underlying structure of a
 * 2-D array of Cell objects
 */
public class BasicGrid implements Grid{

    // ADJACENT_COL and ADJACENT_ROW work together to specify the four adjacent neighbors to a grid location
    public static final int[] ADJACENT_COL = {0, 0, -1, 1};
    public static final int[] ADJACENT_ROW = {-1, 1, 0, 0};

    protected Cell[][] myGrid;
    protected int myNumRows;
    protected int myNumCols;

    /**
     * Constructor for a basic grid initializes an empty 2-D array of Cells of size as specified by the numRows and
     * numCols parameters passed to the constructor
     * @param numRows is the number of rows in the grid to be created
     * @param numCols is the number of columns in the grid to be created
     */
    public BasicGrid(int numRows, int numCols){
        myGrid = new Cell[numRows][numCols];
        myNumRows = numRows;
        myNumCols = numCols;
    }

    /**
     * Get all the cells in the grid in List so it is easy to iterate over the grid.  This method is used to access
     * the grid and hides the underlying implementation of the grid (2-D array) from other classes
     * @return a List of all the Cells in the grid
     */
    @Override
    public List<Cell> getCells() {
        List<Cell> returnList = new ArrayList<>();
        for (int i = 0; i < myGrid.length; i++){
            for (int j = 0; j < myGrid[0].length; j++){
                returnList.add(myGrid[i][j]);
            }
        }
        return returnList;
    }

    /**
     * Getter for the number of rows in the grid
     * @return the number of rows in the grid
     */
    @Override
    public int getNumRows() {
        return myGrid.length;
    }

    /**
     * Getter for the number of columns in the grid
     * @return the number of columns in the grid
     */
    @Override
    public int getNumCols() {
        return myGrid[0].length;
    }

    /**
     * Checks whether a location is valid in this grid.  For a BasicGrid the location must be within the bounds of
     * the number of rows and number of columns in the grid
     * @param loc the location to check
     * @return true if the location is valid in the grid and false if it is not
     */
    @Override
    public boolean isValid(Location loc) {
        return loc.getCol() >= 0 && loc.getCol() < myNumCols && loc.getRow() >= 0 && loc.getRow() < myNumRows;
    }

    /**
     * Puts a Cell into a specified location in the grid
     * @param loc the location at which to put the object
     * @param obj the new object to be added
     */
    @Override
    public void put(Location loc, Cell obj) {
        if (isValid(loc)){
            myGrid[loc.getRow()][loc.getCol()] = obj;
        }
        else{
            //TODO: Throw exception that tried to access an invalid location
        }
    }

    /**
     *
     * @param loc the location of the object that is to be removed
     */
    @Override
    public void remove(Location loc) {
        if (isValid(loc)) {
            myGrid[loc.getRow()][loc.getCol()] = new EmptyCell(loc);
        }
        else {
            //TODO: Throw exception that tried to access an invalid location
        }
    }

    /**
     * Get the Cell object in the specified location in the grid
     * @param loc a location in this grid
     * @return Cell at the Location loc
     */
    @Override
    public Cell get(Location loc) {
        return myGrid[loc.getRow()][loc.getCol()];
        //TODO: Throw exception that tried to access an invalid location
    }

    /**
     * A list of the locations currently occupied by cells
     * Does NOT return empty cells
     * @return a list of the locations currently occupied by cells
     */
    @Override
    public ArrayList<Location> getOccupiedLocations() {
        ArrayList<Location> occupiedLocations = new ArrayList<>();
        for (Cell cell : getCells()){
            if (!cell.isEmpty()){
                occupiedLocations.add(cell.getMyLocation());
            }
        }
        return occupiedLocations;
    }

    /**
     * Returns the valid neighbors of a cell as determined by the rules of that simulation
     * Cells on the borders have fewer neighbors since a BasicGrid does not wrap around
     * @param loc the location of the cell whose neighbors you want
     * @param deltaRow used with deltaCol to specify the relative positions of possible neighbors
     * @param deltaCol used with deltaRow to specify the relative positions of possible neighbors
     * @return an ArrayList of the locations of valid neighbors
     */
    public ArrayList<Location> getValidNeighbors(Location loc, int[] deltaRow, int[] deltaCol) {
        int locRow = loc.getRow();
        int locCol = loc.getCol();
        ArrayList<Location> validLocations = new ArrayList<>();
        for (int i = 0; i < deltaCol.length; i++) {
            Location locationToBeChecked = new Location(locRow + deltaRow[i], locCol + deltaCol[i]);
            if (isValid(locationToBeChecked)) {
                validLocations.add(locationToBeChecked);
            }
        }
        return validLocations;
    }

    /**
     * Get the adjacent locations to a specified location where the cell is marked as empty
     * @param loc a location in this grid
     * @return ArrayList of empty adjacent locations
     */
    @Override
    public ArrayList<Location> getEmptyAdjacentLocations(Location loc) {
        //TODO: Throw exception that tried to access an invalid location
        ArrayList<Location> emptyAdjacentLocations = new ArrayList<>();
        for (Location location : getValidNeighbors(loc, ADJACENT_ROW, ADJACENT_COL)){
            if (get(location).isEmpty()){
                emptyAdjacentLocations.add(location);
            }
        }
        return emptyAdjacentLocations;
    }

    /**
     * Get the adjacent locations to a specified location where the cell is not marked as empty
     * @param loc a location in this grid
     * @return ArrayList of occupied adjacent locations
     */
    @Override
    public ArrayList<Location> getOccupiedAdjacentLocations(Location loc) {
        //TODO: Throw exception that tried to access an invalid location
        ArrayList<Location> emptyAdjacentLocations = new ArrayList<>();
        for (Location l : getValidNeighbors(loc, ADJACENT_ROW, ADJACENT_COL)){
            if (!get(l).isEmpty()){
                emptyAdjacentLocations.add(l);
            }
        }
        return emptyAdjacentLocations;
    }

    /**
     * This method is currently unused???
     * @param loc a location in this grid
     * @return null
     */
    @Override
    public ArrayList<Cell> getNeighbors(Location loc) {
        return null;
    }

    /**
     * Prints the grid row by row using the toStrings specified by each of the cells
     */
    @Override
    public void printGrid() {
        for(int i = 0; i < myGrid.length; i++){
            StringBuilder rowOfCells = new StringBuilder();
            for(int j = 0; j < myGrid[0].length; j++){
                rowOfCells.append(myGrid[i][j]);
            }
            System.out.println(rowOfCells);
        }
    }
}
