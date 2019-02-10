import java.util.*;

public class SugarSimulation extends Simulation{

    public static final String SUGAR_AGENT_PERCENTAGE = "sugarAgentPercentage";
    public static final String SUGAR_GROW_BACK_RATE = "sugarGrowBackRate";
    public static final String MAX_VISION = "maxVision";
    public static final String MAX_METABOLISM = "maxMetabolism";
    public static final String INIT_SUGAR = "initSugar";
    private List<SugarAgent> mySugarAgents;
    private int sugarMax;

    public static final List<String> SUGAR_DATA_FIELDS = List.of(SUGAR_AGENT_PERCENTAGE, SUGAR_GROW_BACK_RATE, MAX_VISION, MAX_METABOLISM, INIT_SUGAR);


    public SugarSimulation(int rows, int cols, Map<String, Double> parameters){
        super(parameters, rows, cols);
    }

    public SugarSimulation(Map<String, Double> params, Grid grid){
        super(params, grid);
    }

    @Override
    public void setInitialStates(String[][] initialStates, String simulationType, HashMap<String, Double> parameters) {
        super.setInitialStates(initialStates, simulationType, parameters);
        initializeSugarAgents();
        sugarMax = sugarMax();
    }

    private int sugarMax(){
        int currMax = 0;
        for (Cell cell : myGrid.getCells()){
            currMax = Math.max(((SugarPatch) cell).getSugar(), currMax);
        }
        return currMax;

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
    public void updateNeighbors(Map<String, String> styleProperties){
        for (Cell cell : myGrid.getCells()){
            cell.setMyNeighbors(NeighborsDefinitions.valueOf(styleProperties.getOrDefault(XMLStyler.NEIGHBORS_TYPE_TAG_NAME,
                    NeighborsDefinitions.ADJACENT.toString())));
        }
    }


    @Override
    public boolean isOver() {
        return false;
    }

    @Override
    public String getMyName() {
        return Simulation.SUGAR_SIMULATION_NAME;
    }

    @Override
    public List<String> getPercentageFields() {
        return null;
    }
}
