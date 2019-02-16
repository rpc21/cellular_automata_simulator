import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ForagePatch is the cell used in the Forage simulation.  Forage Patches maintain their List of ants, the food
 * and home pheromones on the patch on the state of the patch - NEST, FOOD, OBSTACLE, EMPTY
 */
public class ForagePatch extends Cell{

    private static final double DEFAULT_NUMBER_OF_ANTS = 10.0;
    private static final double MAX_PHEROMONES_DEFAULT_VALUE = 10.0;
    private List<ForageAnt> myAnts;
    private double myFoodPheromones;
    private double myHomePheromones;
    private int foodItems;

    /**
     * Constructor for the ForagePatch the is passed another ForagePatch.  Used to easily copy the state of a
     * ForagePatch from one grid to the next iteration of the grid
     * @param currentPatch
     * @param nextGrid
     */
    public ForagePatch(ForagePatch currentPatch, Grid nextGrid){
        super(currentPatch.myLocation, currentPatch.myCurrentState, currentPatch.myGrid, nextGrid,
                currentPatch.myParameters);
        myFoodPheromones = currentPatch.myFoodPheromones;
        myHomePheromones = currentPatch.myHomePheromones;
        foodItems = currentPatch.foodItems;
        myAnts = new ArrayList<>();
    }

    /**
     * Constructor for the ForagePatch.  Constructor to be used when wanting to specify a state/create a ForagePatch
     * from scratch
     * @param location location in the grid
     * @param myCurrentState current state
     * @param myCurrentGrid current grid
     * @param myNextGrid next grid
     * @param parameters parameters
     */
    public ForagePatch(Location location, ForageState myCurrentState, Grid myCurrentGrid, Grid myNextGrid,
                       Map<String, Double> parameters) {
        super(location, myCurrentState, myCurrentGrid, myNextGrid, parameters);
        myAnts = new ArrayList<>();
        initializeAnts();
        initializePheromones();
    }

    /**
     * Advances the foragepatch from one iteration to the next step of the simulation
     * @param nextGrid supplies the next grid to populate
     */
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

    /**
     * Sets the pheromones to the maximum pheromone number
     * @param homeOrFood specify if the state is food or nest
     */
    public void topOffPheromones(ForageState homeOrFood){
        if (homeOrFood == ForageState.NEST)
            myHomePheromones = myParameters.getOrDefault(ForageSimulation.MAX_HOME_PHEROMONES,MAX_PHEROMONES_DEFAULT_VALUE);
        else if (homeOrFood == ForageState.FOOD){
            myFoodPheromones = myParameters.getOrDefault(ForageSimulation.MAX_FOOD_PHEROMONES, MAX_PHEROMONES_DEFAULT_VALUE);
        }
    }

//    public void removeAntFromPatch(ForageAnt ant){
//        if (myAnts.contains(ant)){
//            myAnts.remove(ant);
//        }
//    }

    /**
     * Adds the specified ant to the patch
     * @param ant ant to add to the patch
     */
    public void addAntToPatch(ForageAnt ant){
        myAnts.add(ant);
    }

    /**
     * ForagePatch is valid place to move if it is not an obstacle
     * @return true if valid ant location and not overpopulated
     */
    public boolean isValidPlaceToMove(){
        return isValidAntLocation() && myAnts.size() < myParameters.getOrDefault(ForageSimulation.NUMBER_OF_ANTS,
                DEFAULT_NUMBER_OF_ANTS) / 2;
    }

    /**
     * If it is a Food patch it has a food source
     * @return true if food
     */
    public boolean hasFoodSource(){
        return myCurrentState == ForageState.FOOD;
    }

    /**
     * Valid ant location if not an obstacle
     * @return false if an obstacle
     */
    public boolean isValidAntLocation(){
        return myCurrentState != ForageState.OBSTACLE;
    }

    /**
     *
     * @return true if the nest
     */
    public boolean isTheNest(){
        return myCurrentState == ForageState.NEST;
    }

    /**
     * ForagePatches cannot change state
     */
    @Override
    public void calculateNewState() {
        myNextState = myCurrentState;
    }

    /**
     *
     * @return myFoodPheromones
     */
    public double getMyFoodPheromones() {
        return myFoodPheromones;
    }

    /**
     *
     * @return myHomePheromones
     */
    public double getMyHomePheromones() {
        return myHomePheromones;
    }

    /**
     * Add a specified number of home pheromones to a patch
     * @param d
     */
    public void addHomePheromones(Double d){
        myHomePheromones += d;
    }

    /**
     * Add a specified number of home pheromones to a patch
     * @param d number of food pheromones to add
     */
    public void addFoodPheromones(Double d){
        myFoodPheromones += d;
    }

    /**
     * Add a food item to the patch
     */
    public void addFoodItem(){
        foodItems += 1;
    }

    /**
     *
     * @return true if the patch has any ants
     */
    @Override
    public boolean containsAgent(){
        return !myAnts.isEmpty();
    }
}
