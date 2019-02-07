import java.util.HashMap;
import java.util.List;

public class ForagePatch extends Cell{

    private List<ForageAnt> myAnts;
    private double myFoodPheromones;
    private double myHomePheromones;
    private ForageState myCurrentState;
    private AntGrid myCurrentGrid;
    private AntGrid myNextGrid;

    public ForagePatch(Location location, ForageState myCurrentState, AntGrid myCurrentGrid, AntGrid myNextGrid,
                       HashMap<String, Double> parameters) {
        super(location, myCurrentState, myCurrentGrid, myNextGrid, parameters);
        initializePheromones();
    }

    private void initializePheromones() {
        if (myCurrentState == ForageState.OBSTACLE || myCurrentState == ForageState.){

        }
    }

    @Override
    public void calculateNewState() {

    }


}
