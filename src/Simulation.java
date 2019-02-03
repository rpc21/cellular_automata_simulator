import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

public abstract class Simulation {

    public static final String GOL_SIMULATION_NAME="Game of Life";
    public static final String PERCOLATION_SIMULATION_NAME="Percolation";
    public static final String SEGREGATION_SIMULATION_NAME="Segregation";
    public static final String SPREADING_FIRE_SIMULATION_NAME="Spreading Fire";
    public static final String WATOR_SIMULATION_NAME="Wator";
    protected Grid myGrid;
    protected boolean simulationOver;
    protected HashMap<String, String> credentials;
    protected HashMap<String, Double> myParameters;
    // Data fields
    public static final String DATA_TYPE = "simulation";

    public Simulation(HashMap<String, Double> params, int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
        myParameters=params;
    }

    public static final List<String> DATA_FIELDS = List.of(
            "simulationType",
            "rows",
            "columns"
    );
    public static final List<String> DATA_CREDENTIALS = List.of(
            "title",
            "author"
    );


    public abstract void simulate(double simulationSpeed);

    public void updateGrid(){
        List<Cell> cells = myGrid.getCells();
        for (Cell cell : cells){
            cell.calculateNewState();
        }
        for (Cell cell : cells){
            cell.updateState();
        }
        myGrid.printGrid();
    }

    public void setCredentials(HashMap<String, String> myCredentials){
        credentials = myCredentials;
    }

    public HashMap<String, String> getCredentials(){
        return credentials;
    }

    /**
     * Passes the information about parameter updates from the visualization to the individual cells
     * @param parameters
     */
    public void updateNewParams(Map<String, Double> parameters){
        for (Cell cell : myGrid.getCells()){
            cell.setMyParameters(parameters);
        }
    };

    public void stopSimulation(){
        simulationOver = true;
    }

    /**
     * Checks if the simulation is over
     * @return whether or not the simulation is over
     */
    public abstract boolean isOver();

    public void setMyGrid(Grid myGrid) {
        this.myGrid = myGrid;
    }

    public Grid getMyGrid() {
        return myGrid;
    }

    public void setInitialStates(String[][] initialStates, String simulationType, HashMap<String, Double> parameters){
        for (int i = 0; i < getMyGrid().getNumRows(); i++){
            for (int j = 0; j < getMyGrid().getNumCols(); j++){
                Location thisLocation = new Location(i, j);
                System.out.println("Creating a "+simulationType+" cell");
                Cell newCell = generateSimulationSpecificCell(simulationType, thisLocation, initialStates[i][j],
                        myGrid, parameters);
                System.out.println(newCell + " to be inserted at "+ i + ", "+j);
                System.out.println(newCell.getMyLocation().getRow()+", "+newCell.getMyLocation().getCol());
                getMyGrid().put(newCell.getMyLocation(), newCell);
            }
        }
        getMyGrid().printGrid();
        System.out.println("Initial states set");
    }

    public void setInitialStates(int[][] initialStates, String simulationType, HashMap<String, Double> parameters){
        for (int i = 0; i < getMyGrid().getNumRows(); i++){
            for (int j = 0; j < getMyGrid().getNumCols(); j++){
                Location thisLocation = new Location(i, j);
                System.out.println("Creating a "+simulationType+" cell");
                Cell newCell = generateSimulationSpecificCell(simulationType, thisLocation, initialStates[i][j],
                        myGrid, parameters);
                System.out.println(newCell + " to be inserted at "+ i + ", "+j);
                System.out.println(newCell.getMyLocation().getRow()+", "+newCell.getMyLocation().getCol());
                getMyGrid().put(newCell.getMyLocation(), newCell);
            }
        }
        getMyGrid().printGrid();
        System.out.println("Initial states set");
    }

    private Cell generateSimulationSpecificCell(String simulationType, Location loc, int state, Grid grid,
                                                HashMap<String, Double> parameters){
        if (simulationType.equals("Game of Life")){
            return new GOLCell(loc, state, grid);
        }
        else if (simulationType.equals("Spreading Fire")){
            return new SpreadingFireCell(loc, state, grid, parameters);
        }
        else if (simulationType.equals("Percolation")){
            return new PercolationCell(loc, state, grid);
        }
        else if (simulationType.equals("Segregation")){
            return new SegregationCell(loc, state, grid, parameters);
        }
        else if (simulationType.equals("Wator")){
            return generateWatorCell(loc, grid, parameters);
        }
        return new GOLCell(loc, state, grid);
    }

    private WatorCell generateWatorCell(Location loc, Grid grid, HashMap<String, Double> parameters){
        double randomNumber = Math.random();
        if (randomNumber <= parameters.get("fishPercentage")){
            return new WatorFish(loc, grid, parameters);
        }
        else if (randomNumber <= parameters.get("fishPercentage") + parameters.get("sharkPercentage")){
            return new WatorShark(loc, grid, parameters);
        }
        else{
            return new WatorEmpty(loc);
        }
    }

    private Cell generateSimulationSpecificCell(String simulationType, Location loc, String state, Grid grid,
                                                HashMap<String, Double> parameters){
        if (simulationType.equals("Game of Life")){
            return new GOLCell(loc, GOLState.valueOf(state), grid);
        }
        else if (simulationType.equals("Spreading Fire")){
            return new SpreadingFireCell(loc, SpreadingFireState.valueOf(state), grid, parameters);
        }
        else if (simulationType.equals("Percolation")){
            return new PercolationCell(loc, PercolationState.valueOf(state), grid);
        }
        else if (simulationType.equals("Segregation")){
            return new SegregationCell(loc, SegregationState.valueOf(state), grid, parameters);
        }
        else if (simulationType.equals("Wator")) {
            return generateWatorCell(loc, grid, parameters);
        }
        return new GOLCell(loc, GOLState.valueOf(state), grid);
    }

    public abstract String getMyName();

}
