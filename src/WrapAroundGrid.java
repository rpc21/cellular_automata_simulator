import java.util.ArrayList;

public class WrapAroundGrid extends BasicGrid {

    public WrapAroundGrid(int rows, int cols){
        super(rows, cols);
    }

    @Override
    public ArrayList<Location> getValidNeighbors(Location loc, int[] deltaRow, int[] deltaCol) {
        ArrayList<Location> validNeighbors = new ArrayList<>();
        for (int i = 0; i<deltaCol.length; i++){
            int rowToCheck = (loc.getRow() + deltaRow[i] + myNumRows) % myNumRows;
            int colToCheck = (loc.getCol() + deltaCol[i] + myNumCols) % myNumCols;
            validNeighbors.add(new Location(rowToCheck, colToCheck));
        }
        return validNeighbors;
    }
}
