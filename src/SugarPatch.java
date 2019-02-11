import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SugarPatch extends Cell{

    public static final String MAXIMUM_SUGAR_CAPACITY = "maximumSugarCapacity";
    private List<SugarAgent> mySugarAgents;
    private int amountOfSugar;
    private int maximumSugarCapacity;
    private int myGrowBackRate;
    private static int maxAmountOfSugar = 0;

    public SugarPatch(Location location, Map<String, Double> parameters, Grid grid, String state){
        myCurrentState = SugarState.valueOf(state);
        myLocation = location;
        myParameters = parameters;
        myGrid = grid;
        mySugarAgents = new ArrayList<>();
        maxAmountOfSugar = (int) (double) parameters.getOrDefault(SugarSimulation.MAX_SUGAR,25.0D);
        maximumSugarCapacity = mapStateToSugar(state);
        amountOfSugar = maximumSugarCapacity;
        myGrowBackRate = (int) (double) parameters.getOrDefault(SugarSimulation.SUGAR_GROW_BACK_RATE, 7.0D);
    }

    public String getMyCurrentState(){
        return myCurrentState.toString();
    }

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
        if (opacity <= 0.20D){
            return SugarState.LIGHT_PATCH;
        }
        else if(opacity <= 0.40D){
            return SugarState.MEDIUM_LIGHT_PATCH;
        }
        else if (opacity <= 0.60D){
            return SugarState.MEDIUM_PATCH;
        }
        else if (opacity <= 0.80D){
            return SugarState.MEDIUM_DARK_PATCH;
        }
        else{
            return SugarState.DARK_PATCH;
        }
    }

    public void addSugarAgent(SugarAgent agent){
        mySugarAgents.add(agent);
    }

    public void removeSugarAgent(SugarAgent agent){
        mySugarAgents.remove(agent);
    }

    public int getSugar(){
        return amountOfSugar;
    }

    public void setSugar(int newSugar){
        amountOfSugar = newSugar;
    }

    @Override
    public boolean isEmpty(){
        return mySugarAgents.isEmpty();
    }

    public void takeSugar(){
        amountOfSugar = 0;
    }

    @Override
    public String toString() {
        if (mySugarAgents.isEmpty()){
            return ""+amountOfSugar;
        }
        else{
            return "D";
        }
    }

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
