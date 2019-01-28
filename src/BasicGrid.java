import java.util.ArrayList;
import java.util.List;

public class BasicGrid implements Grid{
    private Cell[][] myGrid;

    public BasicGrid(int numRows, int numCols){
        myGrid = new Cell[numRows][numCols];

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
        return false;
    }

    @Override
    public void put(Location loc, Cell obj) {

    }

    @Override
    public void remove(Location loc) {

    }

    @Override
    public void get(Location loc) {

    }

    @Override
    public ArrayList<Location> getOccupiedLocations() {
        return null;
    }

    @Override
    public ArrayList<Location> getValidAdjacentLocations(Location loc) {
        return null;
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
