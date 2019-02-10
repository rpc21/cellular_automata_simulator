import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SugarPatch extends Cell{

    public static final String MAXIMUM_SUGAR_CAPACITY = "maximumSugarCapacity";
    private List<SugarAgent> mySugarAgents;
    private int amountOfSugar;
    private int maximumSugarCapacity;
    private int myGrowBackRate;

    public SugarPatch(Location location, Map<String, Double> parameters, Grid grid, String state){
        myLocation = location;
        myParameters = parameters;
        myGrid = grid;
        mySugarAgents = new ArrayList<>();
        maximumSugarCapacity = Integer.parseInt(state);
        amountOfSugar = maximumSugarCapacity;
        myGrowBackRate = (int) (double) parameters.get(SugarSimulation.SUGAR_GROW_BACK_RATE);
    }



    @Override
    public void calculateNewState() {
        if (! mySugarAgents.isEmpty() && mySugarAgents.get(0).isDead()){
            mySugarAgents.remove(0);
        }
        amountOfSugar = Math.min(amountOfSugar + myGrowBackRate, maximumSugarCapacity);
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
}