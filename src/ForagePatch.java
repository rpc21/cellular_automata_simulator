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
        switch (myCurrentState) {
            case OBSTACLE:
                myFoodPheromones = 0.0D;
                myHomePheromones = 0.0D;
                break;
            case FOOD:
                myHomePheromones = 0.0D;
                myFoodPheromones = myParameters.get("maxFoodPheromones");
                break;
            case EMPTY:
                myHomePheromones = myParameters.get("maxHomePheromones") * 0.25;
                myFoodPheromones = myParameters.get("maxFoodPheromones") * 0.25;
                break;
            case NEST:
                myHomePheromones = myParameters.get("maxHomePheromones");
                myFoodPheromones = 0.0D;
                break;
        }
    }

    public boolean hasFoodSource(){
        return myCurrentState == ForageState.FOOD;
    }

    public boolean isValidAntLocation(){
        return myCurrentState != ForageState.OBSTACLE;
    }

    public boolean isTheNest(){
        return myCurrentState == ForageState.NEST;
    }

    @Override
    public void calculateNewState() {

    }

    public double getMyFoodPheromones() {
        return myFoodPheromones;
    }

    public double getMyHomePheromones() {
        return myHomePheromones;
    }
}
