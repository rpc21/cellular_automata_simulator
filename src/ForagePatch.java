import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForagePatch extends Cell{

    private List<ForageAnt> myAnts;
    private double myFoodPheromones;
    private double myHomePheromones;
//    private ForageState myCurrentState;
//    private AntGrid myCurrentGrid;
//    private AntGrid myNextGrid;
    private int foodItems;

    public ForagePatch(ForagePatch currentPatch, AntGrid nextGrid){
        super(currentPatch.getMyLocation(), currentPatch.getMyCurrentState(), currentPatch.getMyGrid(), nextGrid,
                currentPatch.myParameters);
        myFoodPheromones = currentPatch.myFoodPheromones;
        myHomePheromones = currentPatch.myHomePheromones;
        foodItems = currentPatch.foodItems;
        myAnts = new ArrayList<>();
    }

    public ForagePatch(Location location, ForageState myCurrentState, AntGrid myCurrentGrid, AntGrid myNextGrid,
                       Map<String, Double> parameters) {
        super(location, myCurrentState, myCurrentGrid, myNextGrid, parameters);
        myAnts = new ArrayList<>();
        initializeAnts();
        initializePheromones();
    }

    public void updateState(AntGrid nextGrid) {
        for (ForageAnt ant: myAnts){
            ant.setMyNextGrid(nextGrid);
            ant.act();
        }
    }

    private void initializeAnts() {
        if (myCurrentState == ForageState.NEST){
            for (int i = 0; i < (int) (double) myParameters.get(ForageSimulation.NUMBER_OF_ANTS); i++){
                myAnts.add(new ForageAnt(myLocation, generateRandomDirection(), (AntGrid) myGrid,
                        (AntGrid) myNextGrid));
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
                myFoodPheromones = myParameters.get(ForageSimulation.MAX_FOOD_PHEROMONES);
                foodItems = Integer.MAX_VALUE;
                break;
            case EMPTY:
                myHomePheromones = myParameters.get(ForageSimulation.MAX_HOME_PHEROMONES) * 0.25;
                myFoodPheromones = myParameters.get(ForageSimulation.MAX_FOOD_PHEROMONES) * 0.25;
                break;
            case NEST:
                myHomePheromones = myParameters.get(ForageSimulation.MAX_HOME_PHEROMONES);
                myFoodPheromones = 0.0D;
                break;
        }
    }

    public void topOffPheromones(ForageState homeOrFood){
        if (homeOrFood == ForageState.NEST)
            myHomePheromones = myParameters.get(ForageSimulation.MAX_HOME_PHEROMONES);
        else if (homeOrFood == ForageState.FOOD){
            myFoodPheromones = myParameters.get(ForageSimulation.MAX_FOOD_PHEROMONES);
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
        return isValidAntLocation() && myAnts.size() < myParameters.get(ForageSimulation.NUMBER_OF_ANTS) / 2;
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
}
