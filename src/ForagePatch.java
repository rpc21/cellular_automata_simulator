import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForagePatch extends Cell{

    public static final double DEFAULT_NUMBER_OF_ANTS = 10.0;
    public static final double MAX_PHEROMONES_DEFAULT_VALUE = 10.0;
    private List<ForageAnt> myAnts;
    private double myFoodPheromones;
    private double myHomePheromones;
    private int foodItems;

    public ForagePatch(ForagePatch currentPatch, Grid nextGrid){
        super(currentPatch.myLocation, currentPatch.myCurrentState, currentPatch.myGrid, nextGrid,
                currentPatch.myParameters);
        myFoodPheromones = currentPatch.myFoodPheromones;
        myHomePheromones = currentPatch.myHomePheromones;
        foodItems = currentPatch.foodItems;
        myAnts = new ArrayList<>();
    }

    public ForagePatch(Location location, ForageState myCurrentState, Grid myCurrentGrid, Grid myNextGrid,
                       Map<String, Double> parameters) {
        super(location, myCurrentState, myCurrentGrid, myNextGrid, parameters);
        myAnts = new ArrayList<>();
        initializeAnts();
        initializePheromones();
    }

    public void updateState(Grid nextGrid) {
        for (ForageAnt ant: myAnts){
            ant.setMyNextGrid(nextGrid);
            ant.act();
        }
    }

    private void initializeAnts() {
        if (myCurrentState == ForageState.NEST){
            for (int i = 0; i < (int) (double) myParameters.getOrDefault(ForageSimulation.NUMBER_OF_ANTS, DEFAULT_NUMBER_OF_ANTS); i++){
                myAnts.add(new ForageAnt(myLocation, generateRandomDirection(), myGrid, myNextGrid));
            }
        }
    }

    private NeighborsDefinitions generateRandomDirection() {
        int randomIndex = (int) (Math.random() * NeighborsDefinitions.CARDINAL_DIRECTIONS_COMPLETE.length);
        return  NeighborsDefinitions.CARDINAL_DIRECTIONS_COMPLETE[randomIndex];
    }

    private void initializePheromones() {
        foodItems = 0;
        System.out.println(myCurrentState);
        switch ((ForageState) myCurrentState) {
            case OBSTACLE:
                myFoodPheromones = 0.0D;
                myHomePheromones = 0.0D;
                break;
            case FOOD:
                myHomePheromones = 0.0D;
                myFoodPheromones = myParameters.getOrDefault(ForageSimulation.MAX_FOOD_PHEROMONES, MAX_PHEROMONES_DEFAULT_VALUE);
                foodItems = Integer.MAX_VALUE;
                break;
            case EMPTY:
                myHomePheromones = myParameters.getOrDefault(ForageSimulation.MAX_HOME_PHEROMONES, 0.0) * 0.25;
                myFoodPheromones = myParameters.getOrDefault(ForageSimulation.MAX_FOOD_PHEROMONES, 0.0) * 0.25;
                break;
            case NEST:
                myHomePheromones = myParameters.getOrDefault(ForageSimulation.MAX_HOME_PHEROMONES, MAX_PHEROMONES_DEFAULT_VALUE);
                myFoodPheromones = 0.0D;
                break;
        }
    }

    public void topOffPheromones(ForageState homeOrFood){
        if (homeOrFood == ForageState.NEST)
            myHomePheromones = myParameters.getOrDefault(ForageSimulation.MAX_HOME_PHEROMONES,MAX_PHEROMONES_DEFAULT_VALUE);
        else if (homeOrFood == ForageState.FOOD){
            myFoodPheromones = myParameters.getOrDefault(ForageSimulation.MAX_FOOD_PHEROMONES, MAX_PHEROMONES_DEFAULT_VALUE);
        }
    }

    public void removeAntFromPatch(ForageAnt ant){
        if (myAnts.contains(ant)){
            myAnts.remove(ant);
        }
    }

    public void addAntToPatch(ForageAnt ant){
        myAnts.add(ant);
    }

    public boolean isValidPlaceToMove(){
        return isValidAntLocation() && myAnts.size() < myParameters.getOrDefault(ForageSimulation.NUMBER_OF_ANTS,
                DEFAULT_NUMBER_OF_ANTS) / 2;
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

    public void addHomePheromones(Double d){
        myHomePheromones += d;
    }

    public void addFoodPheromones(Double d){
        myFoodPheromones += d;
    }

    public void addFoodItem(){
        foodItems += 1;
    }

    @Override
    public boolean containsAgent(){
        return !myAnts.isEmpty();
    }
}
