import java.util.*;

/**
 * Simulation class specific to the Sugar Simulation
 */
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
    public static final double SUGAR_AGENT_DEFAULT_PERCENTAGE = 0.1D;
    private List<SugarAgent> mySugarAgents;
    private int sugarMax;

    public static final List<String> SUGAR_DATA_FIELDS = List.of(SUGAR_AGENT_PERCENTAGE, SUGAR_GROW_BACK_RATE,
            MAX_VISION, MAX_METABOLISM, INIT_SUGAR, MAX_SUGAR, LIGHT_PATCH_PERCENTAGE, MEDIUM_LIGHT_PERCENTAGE, MEDIUM_PERCENTAGE,
            MEDIUM_DARK_PERCENTAGE, DARK_PERCENTAGE);

    /**
     * Constructor for the SugarSimulation
     * @param params parameters specific to the Sugar Simulation
     * @param grid grid for the simulation
     */
    public SugarSimulation(Map<String, Double> params, Grid grid){
        super(params, grid);
        sugarMax = (int) (double) params.getOrDefault(MAX_SUGAR, SugarPatch.MAX_SUGAR_DEFAULT_VALUE);
    }

    /**
     * Override setInitialStates to also initialize the agents
     * @param initialStates 2-D array of strings specifying states
     * @param simulationType simulation name
     * @param parameters simulation specific parameters
     */
    @Override
    public void setInitialStates(String[][] initialStates, String simulationType, Map<String, Double> parameters) {
        super.setInitialStates(initialStates, simulationType, parameters);
        initializeSugarAgents();
    }

    private void initializeSugarAgents() {
        mySugarAgents = new ArrayList<>();
        List<Cell> allCells = myGrid.getCells();
        Collections.shuffle(allCells);
        for (int i = 0; i < (int) (allCells.size() * myParameters.getOrDefault(SUGAR_AGENT_PERCENTAGE, SUGAR_AGENT_DEFAULT_PERCENTAGE)); i++){
            mySugarAgents.add(new SugarAgent(allCells.get(i).getMyLocation(), myParameters, myGrid));
        }
    }

    /**
     * Override updateGrid to just update the SugarAgents and update the SugarPatches
     */
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

    /**
     * Override update neighbors to use ADJACENT neighbors as the default
     * @param styleProperties map including the neighborsType and new neighborstype
     */
    @Override
    public void updateNeighbors(Map<String, String> styleProperties){
        super.updateNeighbors(styleProperties, NeighborsDefinitions.ADJACENT);
    }


    /**
     * Overrides the isOver method to be true if all sugar agents are dead
     * @return true if all the sugar agents are dead
     */
    @Override
    public boolean isOver() {
        return mySugarAgents.isEmpty();
    }

    /**
     * Return the Sugar Simulation name
     * @return Sugar Simulation name
     */
    @Override
    public String getMyName() {
        return Simulation.SUGAR_SIMULATION_NAME;
    }

    /**
     * Return percentageFields to look for when creating a new simulation
     * @return
     */
    @Override
    public List<String> getPercentageFields() {
        return List.of(LIGHT_PATCH_PERCENTAGE, MEDIUM_LIGHT_PERCENTAGE, MEDIUM_PERCENTAGE,
                MEDIUM_DARK_PERCENTAGE, DARK_PERCENTAGE);
    }

    /**
     * Used to replace the cell when it is clicked on by the front end
     * @param location location of the cell to be replaced
     * @param newState new state of the cell
     */
    @Override
    public void replaceCell(Location location, String newState){
        myGrid.put(location, new SugarPatch(location, myParameters, myGrid, newState));
    }
}
