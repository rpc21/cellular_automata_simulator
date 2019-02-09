import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Simulation {

    // Data fields
    public static final String DATA_TYPE = "simulation";
    public static final String GOL_SIMULATION_NAME="Game of Life";
    public static final String PERCOLATION_SIMULATION_NAME="Percolation";
    public static final String SEGREGATION_SIMULATION_NAME="Segregation";
    public static final String SPREADING_FIRE_SIMULATION_NAME="Spreading Fire";
    public static final String WATOR_SIMULATION_NAME="Wator";
    public static final String TITLE_CREDENTIAL="title";
    public static final String AUTHOR_CREDENTIAL="author";

    protected Grid myGrid;
    protected Grid myNextGrid;
    protected HashMap<String, String> credentials;
    protected HashMap<String, String> stateToColor;
    protected HashMap<String, Double> myParameters;
    protected HashMap<String, String> myStyleProperties;

    protected boolean simulationOver;


    public Simulation(HashMap<String, Double> params, int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
        myParameters = params;
    }

    public static final List<String> DATA_FIELDS = List.of(
            XMLParser.SIMULATION_TYPE_TAG_NAME,
            XMLParser.ROW_TAG_NAME,
            XMLParser.COLUMN_TAG_NAME
    );
    public static final List<String> DATA_CREDENTIALS = List.of(
            TITLE_CREDENTIAL,
            AUTHOR_CREDENTIAL
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
    }

    public void setCredentials(HashMap<String, String> myCredentials){
        credentials = myCredentials;
    }

    public HashMap<String, String> getCredentials(){
        return credentials;
    }

    public void setColors(HashMap<String, String> myColors){
        stateToColor = myColors;
    }

    public HashMap<String, String> getStateToColorMap(){
        return stateToColor;
    }

    /**
     * Passes the information about parameter updates from the visualization to the individual cells
     * @param parameters
     */
    public void updateNewParams(Map<String, Double> parameters){
//        for (Cell cell : myGrid.getCells()){
//            cell.setMyParameters(parameters);
//        }
        for (String key: parameters.keySet()){
            myParameters.put(key, parameters.get(key));
        }
        System.out.println("Parameters updated");
    }

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
                        myGrid, myNextGrid, parameters);
                System.out.println(newCell + " to be inserted at "+ i + ", "+j);
                System.out.println(newCell.getMyLocation().getRow()+", "+newCell.getMyLocation().getCol());
                getMyGrid().put(newCell.getMyLocation(), newCell);
            }
        }
        getMyGrid().printGrid();
        System.out.println("Initial states set");
    }

    @Deprecated
    private WatorCell generateWatorCell(Location loc, Grid grid, Grid nextGrid, HashMap<String, Double> parameters){
        double randomNumber = Math.random();
        if (randomNumber <= parameters.get(WatorSimulation.FISH_PERCENTAGE)){
            return new WatorFish(loc, grid, nextGrid, parameters);
        }
        else if (randomNumber <= parameters.get(WatorSimulation.FISH_PERCENTAGE) + parameters.get(WatorSimulation.SHARK_PERCENTAGE)){
            return new WatorShark(loc, grid, nextGrid, parameters);
        }
        else{
            return new WatorEmpty(loc);
        }
    }

    private WatorCell generateWatorCellByState(Location loc, CellState state){
        double randomNumber = Math.random();
        if (state == WatorState.FISH){
            return new WatorFish(loc, myGrid, myNextGrid, myParameters);
        }
        else if (state == WatorState.SHARK){
            return new WatorShark(loc, myGrid, myNextGrid, myParameters);
        }
        else{
            return new WatorEmpty(loc);
        }
    }

    private Cell generateSimulationSpecificCell(String simulationType, Location loc, String state, Grid grid,
                                                Grid nextGrid,
                                                HashMap<String, Double> parameters){
        switch (simulationType) {
            case GOL_SIMULATION_NAME:
                return new GOLCell(loc, GOLState.valueOf(state), grid, nextGrid);
            case SPREADING_FIRE_SIMULATION_NAME:
                return new SpreadingFireCell(loc, SpreadingFireState.valueOf(state), grid, nextGrid, parameters);
            case PERCOLATION_SIMULATION_NAME:
                return new PercolationCell(loc, PercolationState.valueOf(state), grid, nextGrid);
            case SEGREGATION_SIMULATION_NAME:
                return new SegregationCell(loc, SegregationState.valueOf(state), grid, nextGrid, parameters);
            case WATOR_SIMULATION_NAME:
                return generateWatorCellByState(loc, WatorState.valueOf(state));
        }
        return new GOLCell(loc, GOLState.valueOf(state), grid, nextGrid);
    }

    public abstract String getMyName();

    public abstract List<String> getPercentageFields();

    /**
     * This method needs to be overridden by the subclasses
     * @param location
     * @param newState
     */
    public void replaceCell(Location location, String newState){
        Cell newCell = generateSimulationSpecificCell(getMyName(), location, newState, myGrid, myNextGrid,
                myParameters);
        myGrid.put(location, newCell);
    }

    public List<String> getMyPossibleStates(){
        return myGrid.getCells().get(0).getMyCurrentState().getPossibleValues();
    }
    public HashMap<String,Double> getInitialParams(){
        return myParameters;
    }

}

