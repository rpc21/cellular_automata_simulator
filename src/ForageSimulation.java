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


    public ForageSimulation(Map<String, Double> params, Grid grid){
        super(params, grid);
    }

    @Override
    public void updateGrid() {
        myNextGrid = new GridFactory().generateGrid(myGrid);
        copyMyGrid();
        for (Cell patch: myGrid.getCells()){
            ((ForagePatch) patch).updateState(myNextGrid);
        }
        myGrid = myNextGrid;
    }

    private void copyMyGrid() {
        for (Cell patch : myGrid.getCells()){
            myNextGrid.put(patch.getMyLocation(), new ForagePatch((ForagePatch) patch, myNextGrid));
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

    @Override
    public void updateNeighbors(Map<String, String> styleProperties){
        super.updateNeighbors(styleProperties, NeighborsDefinitions.ADJACENT);
    }
}
