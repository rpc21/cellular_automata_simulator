import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatorSimulation extends Simulation {
    public static final String SHARK_PERCENTAGE = "sharkPercentage";
    public static final String FISH_PERCENTAGE = "fishPercentage";
    public static final String EMPTY_PERCENTAGE = "emptyPercentage";
    public static final String SHARK_BREED_TIME = "sharkBreedTime";
    public static final String FISH_BREED_TIME = "fishBreedTime";
    public static final String STARVE_TIME = "starveTime";
    public static final List<String> WATOR_DATA_FIELDS = List.of(
            SHARK_PERCENTAGE, FISH_PERCENTAGE, EMPTY_PERCENTAGE, SHARK_BREED_TIME, FISH_BREED_TIME, STARVE_TIME
    );

    public WatorSimulation(HashMap<String, Double> params, int rows, int cols){
        super(params, rows, cols);
        setMyGrid(new WrapAroundGrid(rows, cols));
    }

    public WatorSimulation(Map<String, Double> params, Grid grid){
        super(params, grid);
    }

    @Override
    public boolean isOver() {
        return myGrid.getOccupiedLocations().size() == 0;
    }

    @Override
    public void updateGrid() {
        for (Location loc : myGrid.getOccupiedLocations()){
           ((WatorCell) myGrid.get(loc)).step();
        }
    }

    @Override
    public String getMyName() {
        return Simulation.WATOR_SIMULATION_NAME;
    }

    @Override
    public List<String> getPercentageFields() {
        return List.of(SHARK_PERCENTAGE, FISH_PERCENTAGE, EMPTY_PERCENTAGE);
    }

    @Override
    public void setInitialStates(String[][] initialStates, String simulationType, Map<String, Double> parameters) {
        for (int i = 0; i < getMyGrid().getNumRows(); i++){
            for (int j = 0; j < getMyGrid().getNumCols(); j++){
                Location thisLocation = new Location(i, j);
                Cell newCell = generateWATORSpecificCell(simulationType, thisLocation, initialStates[i][j],
                        myGrid, myNextGrid, parameters);
                System.out.println(newCell + " to be inserted at "+ i + ", "+j);
                System.out.println(newCell.getMyLocation().getRow()+", "+newCell.getMyLocation().getCol());
                getMyGrid().put(newCell.getMyLocation(), newCell);
            }
        }
        getMyGrid().printGrid();
//        System.out.println("Initial states set");
    }

    private Cell generateWATORSpecificCell(String simulationType, Location thisLocation, String state, Grid grid,
                                           Grid nextGrid, Map<String, Double> parameters) {
        if (WatorState.valueOf(state) == WatorState.FISH){
            return new WatorFish(thisLocation, grid, nextGrid, parameters);
        }
        else if (WatorState.valueOf(state) == WatorState.SHARK){
            return new WatorShark(thisLocation, grid, nextGrid, parameters);
        }
        else{
            return new WatorEmpty(thisLocation);
        }
    }

    @Override
    public void updateNeighbors(Map<String, String> styleProperties){
        super.updateNeighbors(styleProperties, NeighborsDefinitions.ADJACENT);
    }
}
