import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The SugarPatch is the extension of Cell used in the Sugar simulation.  SugarPatches are able to have sugar agents
 * on them and their colors (darkness) are determined by the amount of sugar on the patch relative to the maximum
 * amount of sugar allowed on any patch
 */
public class SugarPatch extends Cell{

    public static final String MAXIMUM_SUGAR_CAPACITY = "maximumSugarCapacity";
    public static final double MAX_SUGAR_DEFAULT_VALUE = 25.0D;
    private static final double GROW_BACK_RATE_DEFAULT = 7.0D;
    private static final double LIGHT_OPACITY_CUTOFF = 0.20D;
    private static final double MEDIUM_LIGHT_OPACITY_CUTOFF = 0.40D;
    private static final double MEDIUM_OPACITY_CUTOFF = 0.60D;
    private static final double MEDIUM_DARK_OPACITY_CUTOFF = 0.80D;
    private List<SugarAgent> mySugarAgents;
    private int amountOfSugar;
    private int maximumSugarCapacity;
    private int myGrowBackRate;

    //static because all the patches have the same global max amount of sugar that is used to determine opacity cutoffs
    private static int maxAmountOfSugar = 0;

    /**
     * SugarPatch constructor, works the same as other cell constructors
     * @param location location of the sugar patch
     * @param parameters sugar simulation specific parameters
     * @param grid grid that the patch is in
     * @param state starting state based on darkness which gets translated to amount of sugar
     */
    public SugarPatch(Location location, Map<String, Double> parameters, Grid grid, String state){
        myCurrentState = SugarState.valueOf(state);
        myLocation = location;
        myParameters = parameters;
        myGrid = grid;
        mySugarAgents = new ArrayList<>();
        maxAmountOfSugar = (int) (double) parameters.getOrDefault(SugarSimulation.MAX_SUGAR, MAX_SUGAR_DEFAULT_VALUE);
        maximumSugarCapacity = mapStateToSugar(state);
        amountOfSugar = maximumSugarCapacity;
        myGrowBackRate = (int) (double) parameters.getOrDefault(SugarSimulation.SUGAR_GROW_BACK_RATE, GROW_BACK_RATE_DEFAULT);
    }

    /**
     * Returns the currentState of the cell as a String
     * @return current state of the cell as a string
     */
    public String getMyCurrentState(){
        return myCurrentState.toString();
    }

    /**
     * Calculates the new state of the cell based on the sugar agents, current sugar and the sugar cutoffs
     */
    @Override
    public void calculateNewState() {
        if (! mySugarAgents.isEmpty() && mySugarAgents.get(0).isDead()){
            mySugarAgents.remove(0);
        }
        amountOfSugar = Math.min(amountOfSugar + myGrowBackRate, maximumSugarCapacity);
        myCurrentState = assignStateBasedOnSugar();
    }

    private CellState assignStateBasedOnSugar() {
        double opacity = amountOfSugar * 1.0 / maxAmountOfSugar;
        if (opacity <= LIGHT_OPACITY_CUTOFF){
            return SugarState.LIGHT_PATCH;
        }
        else if(opacity <= MEDIUM_LIGHT_OPACITY_CUTOFF){
            return SugarState.MEDIUM_LIGHT_PATCH;
        }
        else if (opacity <= MEDIUM_OPACITY_CUTOFF){
            return SugarState.MEDIUM_PATCH;
        }
        else if (opacity <= MEDIUM_DARK_OPACITY_CUTOFF){
            return SugarState.MEDIUM_DARK_PATCH;
        }
        else{
            return SugarState.DARK_PATCH;
        }
    }

    /**
     * Add a sugar agent to the cell
     * @param agent to be added
     */
    public void addSugarAgent(SugarAgent agent){
        mySugarAgents.add(agent);
    }

    /**
     * Remove a sugar agent from the cell
     * @param agent to be removed
     */
    public void removeSugarAgent(SugarAgent agent){
        mySugarAgents.remove(agent);
    }

    /**
     * Return amount of sugar on the cell
     * @return amountOfSugar
     */
    public int getSugar(){
        return amountOfSugar;
    }

    /**
     * Set the amount of sugar on the cell
     * @param newSugar sugar to be put on the cell
     */
    public void setSugar(int newSugar){
        amountOfSugar = newSugar;
    }

    /**
     * Checks if the cell doesn't contain any agents
     * @return true if there are no sugar agents on the cell
     */
    @Override
    public boolean isEmpty(){
        return mySugarAgents.isEmpty();
    }

    /**
     * set amountOfSugar to 0
     */
    public void takeSugar(){
        amountOfSugar = 0;
    }

    /**
     * @return String representation of the cell
     */
    @Override
    public String toString() {
        if (mySugarAgents.isEmpty()){
            return ""+amountOfSugar;
        }
        else{
            return "D";
        }
    }

    /**
     * Checks if cell contains an agent
     * @return true if cell contains an agent
     */
    @Override
    public boolean containsAgent(){
        return !mySugarAgents.isEmpty();
    }

    private int mapStateToSugar(String newState) {
        if(newState.equals(SugarState.LIGHT_PATCH.toString())){
            maximumSugarCapacity = maxAmountOfSugar / 5;
        }
        else if (newState.equals(SugarState.MEDIUM_LIGHT_PATCH.toString())){
            maximumSugarCapacity = maxAmountOfSugar * 2 / 5;
        }
        else if (newState.equals(SugarState.MEDIUM_PATCH.toString())){
            maximumSugarCapacity = maxAmountOfSugar * 3 / 5;
        }
        else if (newState.equals(SugarState.MEDIUM_DARK_PATCH.toString())){
            maximumSugarCapacity = maxAmountOfSugar * 4 / 5;
        }
        else{
            maximumSugarCapacity = maxAmountOfSugar;
        }
        return maximumSugarCapacity;
    }
}
