import java.util.ArrayList;
import java.util.List;

public class AntGrid extends BasicGrid{

    public AntGrid(int numRows, int numCols){
        super(numRows, numCols);
    }

    public Location getFoodSourceLocation() {
        return null;
    }

    public List<Location> getPossibleMoves(List<Location> neighborLocations) {
        ArrayList<Location> possibleLocations = new ArrayList<>();
        for (Location loc : neighborLocations){
            if (((ForagePatch) get(loc)).isValidAntLocation()){
                possibleLocations.add(loc);
            }
        }
        return possibleLocations;
    }
}
