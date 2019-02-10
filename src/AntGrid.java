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
            if (isValid(loc) && ((ForagePatch) get(loc)).isValidAntLocation()){
                possibleLocations.add(loc);
            }
        }
        return possibleLocations;
    }

    public List<Double> getNeighborHomePheromones(Location location){
        List<Double> neighborHomePheromones = new ArrayList<>();
        for (Location loc : getValidNeighbors(location, NeighborsDefinitions.BOX_NEIGHBORS)){
            neighborHomePheromones.add(((ForagePatch) get(loc)).getMyHomePheromones());
        }
        return neighborHomePheromones;
    }

    public List<Double> getNeighborFoodPheromones(Location location){
        List<Double> neighborFoodPheromones = new ArrayList<>();
        for (Location loc : getValidNeighbors(location, NeighborsDefinitions.BOX_NEIGHBORS)){
            neighborFoodPheromones.add(((ForagePatch) get(loc)).getMyFoodPheromones());
        }
        return neighborFoodPheromones;
    }
}
