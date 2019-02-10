import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SegregationSimulation extends Simulation{

    /**
     * Constructor for the SegregationSimulation. Initializes the grid for the simulation to a BasicGrid of size rows x
     * cols
     * @param rows number of rows in the grid for the simulation
     * @param cols number of columns in the grid for the simulation
     */
    public static final String RED_PERCENTAGE = "redPercentage";
    public static final String BLUE_PERCENTAGE = "bluePercentage";
    public static final String EMPTY_PERCENTAGE = "emptyPercentage";
    public static final String THRESHOLD = "threshold";

    public static final List<String> SEGREGATION_DATA_FIELDS = List.of(
            RED_PERCENTAGE, BLUE_PERCENTAGE, EMPTY_PERCENTAGE, THRESHOLD
    );

    public SegregationSimulation(HashMap<String, Double> params, int rows, int cols){
        super(params, rows, cols);
        setMyGrid(new BasicGrid(rows, cols));
    }

    public SegregationSimulation(Map<String, Double> params, Grid grid){
        super(params, grid);
    }

    @Override
    public boolean isOver() {
        for (Cell cell: myGrid.getCells()){
            if (!((SegregationCell) cell).isSatisfied()){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getMyName() {
        return Simulation.SEGREGATION_SIMULATION_NAME;
    }

    @Override
    public List<String> getPercentageFields() {
        return List.of(RED_PERCENTAGE, BLUE_PERCENTAGE, EMPTY_PERCENTAGE);
    }

    @Override
    public void updateNeighbors(Map<String, String> styleProperties){
        for (Cell cell : myGrid.getCells()){
            cell.setMyNeighbors(NeighborsDefinitions.valueOf(styleProperties.getOrDefault(XMLStyler.NEIGHBORS_TYPE_TAG_NAME,
                    NeighborsDefinitions.BOX_NEIGHBORS.toString().toUpperCase())));
        }
    }
}
