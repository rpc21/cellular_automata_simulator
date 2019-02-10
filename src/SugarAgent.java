import java.util.Map;

public class SugarAgent {

    public static final String MAX_VISION = "maxVision";
    public static final String MAX_METABOLISM = "maxMetabolism";
    public static final String INIT_SUGAR = "initSugar";
    private int sugar;
    private int sugarMetabolism;
    private int vision;
    private Map<String, Double> myParameters;
    private Location myLocation;
    private Grid myGrid;
    private boolean shouldRemove;

    public SugarAgent(Location location, Map<String, Double> parameters, Grid grid){
        myGrid = grid;
        myLocation = location;
        myParameters = parameters;
        initializeInstanceVariables();
    }

    private void initializeInstanceVariables() {
        vision = (int) (Math.random() * myParameters.get(MAX_VISION)) + 1;
        sugarMetabolism = (int) (Math.random() * myParameters.get(MAX_METABOLISM));
        sugar = (int) (double) myParameters.get(INIT_SUGAR);
        shouldRemove = false;
    }

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

    public boolean isDead(){
        return shouldRemove;
    }

}
