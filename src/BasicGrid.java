import java.util.ArrayList;
import java.util.List;

public class BasicGrid implements Grid{
    private Cell[][] myGrid;
    private int myNumRows;
    private int myNumCols;

    public BasicGrid(int numRows, int numCols){
        myGrid = new Cell[numRows][numCols];
        myNumRows = numRows;
        myNumCols = numCols;
    }

    @Override
    public List<Cell> getGrid() {
        List<Cell> returnList = new ArrayList<>();
        for (int i = 0; i < myGrid.length; i++){
            for (int j = 0; j < myGrid[0].length; j++){
                returnList.add(myGrid[i][j]);
            }
        }
        return returnList;
    }

    @Override
    public int getNumRows() {
        return myGrid.length;
    }

    @Override
    public int getNumCols() {
        return myGrid[0].length;
    }

    @Override
    public boolean isValid(Location loc) {
        return loc.getCol() >= 0 && loc.getCol() < myNumCols && loc.getRow() >= 0 && loc.getRow() < myNumRows;
    }

    @Override
    public void put(Location loc, Cell obj) {

    }

    @Override
    public void remove(Location loc) {

    }

    @Override
    public Cell get(Location loc) {
        return myGrid[loc.getRow()][loc.getCol()];
    }

    @Override
    public ArrayList<Location> getOccupiedLocations() {
        return null;
    }

    @Override
    public ArrayList<Location> getValidAdjacentLocations(Location loc) {
        int[] deltaRow = {-1, 1, 0, 0};
        int[] deltaCol = {0, 0, -1, 1};
        int locRow = loc.getRow();
        int locCol = loc.getCol();
        ArrayList<Location> validLocations = new ArrayList<>();
        for (int i = 0; i<deltaCol.length; i++){
            Location locationToBeChecked = new Location(locRow + deltaRow[i], locCol + deltaCol[i]);
            if (isValid(locationToBeChecked)){
                validLocations.add(locationToBeChecked);
            }
        }
        return validLocations;
    }

    @Override
    public ArrayList<Location> getEmptyAdjacentLocations(Location loc) {
        return null;
    }

    @Override
    public ArrayList<Location> getOccupiedAdjacentLocations(Location loc) {
        return null;
    }

    @Override
    public ArrayList<Cell> getNeighbors(Location loc) {
        return null;
    }
}
