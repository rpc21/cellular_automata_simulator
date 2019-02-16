import java.util.Map;

/**
 * SugarAgents are used in the Sugar simulation and implement rules involved in the Sugar Simulation.
 */
public class SugarAgent {

    public static final String MAX_VISION = "maxVision";
    public static final String MAX_METABOLISM = "maxMetabolism";
    public static final String INIT_SUGAR = "initSugar";
    private static final double DEFAULT_MAX_VISION = 5.0D;
    private static final double DEFAULT_MAX_METABOLISM = 4.0D;
    private static final double DEFAULT_INTIAL_SUGAR = 2.0D;
    private int sugar;
    private int sugarMetabolism;
    private int vision;
    private Map<String, Double> myParameters;
    private Location myLocation;
    private Grid myGrid;
    private boolean shouldRemove;

    /**
     * Constructor for the sugar agents that are involved in the Sugar simulation
     * @param location location of the agent
     * @param parameters parameters for how sugar agents should behave
     * @param grid grid that is being used in the simulation
     */
    public SugarAgent(Location location, Map<String, Double> parameters, Grid grid){
        myGrid = grid;
        myLocation = location;
        myParameters = parameters;
        initializeInstanceVariables();
    }

    private void initializeInstanceVariables() {
        vision = (int) (Math.random() * myParameters.getOrDefault(MAX_VISION, DEFAULT_MAX_VISION)) + 1;
        sugarMetabolism = (int) (Math.random() * myParameters.getOrDefault(MAX_METABOLISM, DEFAULT_MAX_METABOLISM));
        sugar = (int) (double) myParameters.getOrDefault(INIT_SUGAR, DEFAULT_INTIAL_SUGAR);
        shouldRemove = false;
    }

    /**
     * Method that advances a sugar agent from one iteration to the next
     */
    public void act(){
        Location nextLocation = findMaxSugarLocation();
        moveSugarAgent(nextLocation);
        updateSugar();
    }

    private void updateSugar() {
        sugar += ((SugarPatch) myGrid.get(myLocation)).getSugar();
        ((SugarPatch) myGrid.get(myLocation)).takeSugar();
        sugar -= sugarMetabolism;
        if (sugar <= 0){
            shouldRemove = true;
        }
    }

    private void moveSugarAgent(Location nextLocation) {
        ((SugarPatch) myGrid.get(myLocation)).removeSugarAgent(this);
        myLocation = nextLocation;
        ((SugarPatch) myGrid.get(nextLocation)).addSugarAgent(this);
    }

    private Location findMaxSugarLocation() {
        int maxSugarValue = 0;
        Location maxSugarLocation = new Location(0,0);
        for (int j = 0; j <= vision; j++) {
            for (int i = 0; i < NeighborsDefinitions.ADJACENT.getDeltaCol().length; i++) {
                Location locationToCheck =
                        new Location(myLocation.getRow() + NeighborsDefinitions.ADJACENT.getDeltaRow()[i]*j,
                                myLocation.getCol() + NeighborsDefinitions.ADJACENT.getDeltaCol()[i]*j);
                if (myGrid.isValid(locationToCheck) && myGrid.get(locationToCheck).isEmpty()) {
                    if (((SugarPatch) myGrid.get(locationToCheck)).getSugar() > maxSugarValue) {
                        maxSugarValue = ((SugarPatch) myGrid.get(locationToCheck)).getSugar();
                        maxSugarLocation = locationToCheck;
                    }
                }
            }
        }
        return maxSugarLocation;
    }

    /**
     * @return true if the agent is dead and should be removed
     */
    public boolean isDead(){
        return shouldRemove;
    }

}
