import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForageSimulation extends Simulation {

    public static final String MAX_FOOD_PHEROMONES = "maxFoodPheromones";
    public static final String MAX_HOME_PHEROMONES = "maxHomePheromones";
    public static final String NUMBER_OF_ANTS = "numberOfAnts";
    public static final List<String> FORAGE_DATA_FIELDS = List.of(MAX_FOOD_PHEROMONES, MAX_HOME_PHEROMONES,
            NUMBER_OF_ANTS);

    public ForageSimulation(){
        super();
    }

    public ForageSimulation(Map<String, Double> params, int rows, int cols){
        myParameters = params;
        myGrid = new AntGrid(rows, cols);
        myNextGrid = new AntGrid(rows, cols);
    }

    @Override
    public void updateGrid() {
        myNextGrid = new AntGrid(myGrid.getNumRows(), myGrid.getNumCols());
        copyMyGrid();
        for (Cell patch: myGrid.getCells()){
            ((ForagePatch) patch).updateState((AntGrid) myNextGrid);
        }
        myGrid = myNextGrid;
    }

    private void copyMyGrid() {
        for (Cell patch : myGrid.getCells()){
            myNextGrid.put(patch.getMyLocation(), new ForagePatch((ForagePatch) patch, (AntGrid) myNextGrid));
        }
    }

    @Override
    public boolean isOver() {
        return false;
    }

    @Override
    public String getMyName() {
        return Simulation.FORAGE_SIMULATION_NAME;
    }

    @Override
    public List<String> getPercentageFields() {
        return null;
    }
}
