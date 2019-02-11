import java.util.*;

public class SugarSimulation extends Simulation{

    public static final String SUGAR_AGENT_PERCENTAGE = "sugarAgentPercentage";
    public static final String SUGAR_GROW_BACK_RATE = "sugarGrowBackRate";
    public static final String MAX_VISION = "maxVision";
    public static final String MAX_METABOLISM = "maxMetabolism";
    public static final String INIT_SUGAR = "initSugar";
    public static final String MAX_SUGAR = "maxSugar";
    public static final String LIGHT_PATCH_PERCENTAGE = "light_patchPercentage";
    public static final String MEDIUM_LIGHT_PERCENTAGE = "medium_light_patchPercentage";
    public static final String MEDIUM_PERCENTAGE = "medium_patchPercentage";
    public static final String MEDIUM_DARK_PERCENTAGE = "medium_dark_patchPercentage";
    public static final String DARK_PERCENTAGE = "dark_patchPercentage";
    private List<SugarAgent> mySugarAgents;
    private int sugarMax;

    public static final List<String> SUGAR_DATA_FIELDS = List.of(SUGAR_AGENT_PERCENTAGE, SUGAR_GROW_BACK_RATE,
            MAX_VISION, MAX_METABOLISM, INIT_SUGAR, MAX_SUGAR, LIGHT_PATCH_PERCENTAGE, MEDIUM_LIGHT_PERCENTAGE, MEDIUM_PERCENTAGE,
            MEDIUM_DARK_PERCENTAGE, DARK_PERCENTAGE);


    public SugarSimulation(int rows, int cols, Map<String, Double> parameters){
        super(parameters, rows, cols);
        sugarMax = (int) (double) parameters.getOrDefault(MAX_SUGAR, 25.0D);
    }

    public SugarSimulation(Map<String, Double> params, Grid grid){
        super(params, grid);
        sugarMax = (int) (double) params.getOrDefault(MAX_SUGAR, 25.0D);
    }

    @Override
    public void setInitialStates(String[][] initialStates, String simulationType, Map<String, Double> parameters) {
        super.setInitialStates(initialStates, simulationType, parameters);
        initializeSugarAgents();
//        sugarMax = sugarMax();
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
        for (int i = 0; i < (int) (allCells.size() * myParameters.getOrDefault(SUGAR_AGENT_PERCENTAGE, 0.1D)); i++){
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
        super.updateNeighbors(styleProperties, NeighborsDefinitions.ADJACENT);
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
        return List.of(LIGHT_PATCH_PERCENTAGE, MEDIUM_LIGHT_PERCENTAGE, MEDIUM_PERCENTAGE,
                MEDIUM_DARK_PERCENTAGE, DARK_PERCENTAGE);
    }

    @Override
    public void replaceCell(Location location, String newState){
        int sugar = mapColorToSugar(newState);
        ((SugarPatch) myGrid.get(location)).setSugar(sugar);
    }

    private int mapColorToSugar(String newState) {
        int sugar = 0;
        if(newState.equals(SugarState.LIGHT_PATCH.toString())){
            sugar = sugarMax / 5;
        }
        else if (newState.equals(SugarState.MEDIUM_LIGHT_PATCH.toString())){
            sugar = sugarMax * 2 / 5;
        }
        else if (newState.equals(SugarState.MEDIUM_PATCH.toString())){
            sugar = sugarMax * 3 / 5;
        }
        else if (newState.equals(SugarState.MEDIUM_DARK_PATCH.toString())){
            sugar = sugarMax * 4 / 5;
        }
        else{
            sugar = sugarMax;
        }
        return sugar;
    }


}
