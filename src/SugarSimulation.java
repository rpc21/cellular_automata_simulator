import java.util.*;

public class SugarSimulation extends Simulation{

    public static final String SUGAR_AGENT_PERCENTAGE = "sugarAgentPercentage";
    private List<SugarAgent> mySugarAgents;

    public static final List<String> SUGAR_DATA_FIELDS = List.of(SUGAR_AGENT_PERCENTAGE);


    public SugarSimulation(int rows, int cols, Map<String, Double> parameters){
        super(parameters, rows, cols);
        initializeSugarAgents();
    }

    private void initializeSugarAgents() {
        mySugarAgents = new ArrayList<>();
        List<Cell> allCells = myGrid.getCells();
        Collections.shuffle(allCells);
        for (int i = 0; i < (int) (allCells.size() * myParameters.get(SUGAR_AGENT_PERCENTAGE)); i++){
            mySugarAgents.add(new SugarAgent(allCells.get(i).getMyLocation(), myParameters, myGrid));
        }
    }

    @Override
    public void updateGrid(){
        updateSugarAgents();
        updateSugarPatches();

    }

    private void updateSugarPatches() {
        for (Cell patch: myGrid.getCells()){
            patch.calculateNewState();
        }
    }

    private void updateSugarAgents() {
        ListIterator<SugarAgent> iter = mySugarAgents.listIterator();
        while (iter.hasNext()){
            SugarAgent agent = iter.next();
            agent.act();
            if(agent.isDead()){
                iter.remove();
            }
        }
    }


    @Override
    public boolean isOver() {
        return false;
    }

    @Override
    public String getMyName() {
        return null;
    }

    @Override
    public List<String> getPercentageFields() {
        return null;
    }
}
