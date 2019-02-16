import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class that manages the simulations.  Serves almost the purpose of a Controller bridging the gap between
 * the front end view (GUI) and back end model of the cells.  Manages the cells, passing updates from cells to GUI
 * and GUI to cells.  To create a new simulation, one should extend from this class.
 */
public abstract class Simulation {

    // Data fields
    public static final String DATA_TYPE = "simulation";
    public static final String FORAGE_SIMULATION_NAME = "Forage";
    public static final String GOL_SIMULATION_NAME="Game of Life";
    public static final String PERCOLATION_SIMULATION_NAME="Percolation";
    public static final String SEGREGATION_SIMULATION_NAME="Segregation";
    public static final String SPREADING_FIRE_SIMULATION_NAME="Spreading Fire";
    public static final String SUGAR_SIMULATION_NAME= "Sugar";
    public static final String WATOR_SIMULATION_NAME="Wator";
    public static final String TITLE_CREDENTIAL="title";
    public static final String AUTHOR_CREDENTIAL="author";

    public static final List<String> DATA_FIELDS = List.of(
            XMLParser.SIMULATION_TYPE_TAG_NAME,
            XMLParser.ROW_TAG_NAME,
            XMLParser.COLUMN_TAG_NAME
    );
    public static final List<String> DATA_CREDENTIALS = List.of(
            TITLE_CREDENTIAL,
            AUTHOR_CREDENTIAL
    );

    protected Grid myGrid;
    protected Grid myNextGrid;
    protected Map<String, String> credentials;
    protected Map<String, Double> myParameters;
    protected Map<String, String> stateToColor;
    protected Map<String, String> myStyleProperties;

    protected boolean simulationOver;

    /**
     * Default constructor for a Simulation
     */
    public Simulation(){
        myParameters = new HashMap<>();
        setMyGrid(new BasicGrid(10,10));
    }

    /**
     * Constructor for a Simulation - typically called by subclasses
     * @param params parameters for the simulation
     * @param grid grid for the simulation
     */
    public Simulation(Map<String, Double> params, Grid grid){
        myGrid = grid;
        myParameters = params;
    }

    /**
     * Constructor for a simulation if the grid is not already created. Defaults to empty basic grid of size rows x cols
     * @param params simulation specific parameters
     * @param rows number of rows
     * @param cols number of columns
     */
    public Simulation(Map<String, Double> params, int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
        myParameters = params;
    }

    /**
     * Method to advance the grid from one iteration to the next
     * This method is called by the gui to advance the simulation
     * Provides default implementation for simulations with simple rules
     * More complex simulations need to override this method to be specific to that simulation
     */
    public void updateGrid(){
        List<Cell> cells = myGrid.getCells();
        for (Cell cell : cells){
            cell.calculateNewState();
        }
        for (Cell cell : cells){
            cell.updateState();
        }
    }

    /**
     * Setter for the credentials of the simulation
     * @param myCredentials
     */
    public void setCredentials(Map<String, String> myCredentials){
        credentials = myCredentials;
    }

    /**
     * Getter for the credentials of the simulation
     * @return credentials
     */
    public Map<String, String> getCredentials(){
        return credentials;
    }

    /**
     * Setter for the colormap of the simulation
     * @param myColors
     */
    public void setColors(HashMap<String, String> myColors){
        stateToColor = myColors;
    }


//    public Map<String, String> getStateToColorMap(){
//        return stateToColor;
//    }

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
    }


//    public void stopSimulation(){
//        simulationOver = true;
//    }

    /**
     * Checks if the simulation is over
     * @return whether or not the simulation is over
     */
    public abstract boolean isOver();

    public void setMyGrid(Grid myGrid) {
        this.myGrid = myGrid;
    }

    /**
     * Getter for the simulation grid
     * @return myGrid
     */
    public Grid getMyGrid() {
        return myGrid;
    }

    /**
     * Generic implementation of how to get the initial cell states for the simulation from a 2-D string array
     * specifying states
     * @param initialStates 2-D array of strings specifying states
     * @param simulationType simulation name
     * @param parameters simulation specific parameters
     */
    public void setInitialStates(String[][] initialStates, String simulationType, Map<String, Double> parameters){
        for (int i = 0; i < getMyGrid().getNumRows(); i++){
            for (int j = 0; j < getMyGrid().getNumCols(); j++){
                Location thisLocation = new Location(i, j);
                Cell newCell = generateSimulationSpecificCell(simulationType, thisLocation, initialStates[i][j],
                        myGrid, myNextGrid, parameters);
                getMyGrid().put(newCell.getMyLocation(), newCell);
            }
        }
        getMyGrid().printGrid();
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

    /**
     * Generates a wator cell by state
     * @param loc location
     * @param state state
     * @return a new wator cell based on the state
     */
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

    /**
     * Generates a simulation specific cell
     * @param simulationType simulation name
     * @param loc location of the cell
     * @param state cell's state
     * @param grid grid for the simulation
     * @param nextGrid next grid
     * @param parameters parameters for the simulation
     * @return a simulation specific cell
     */
    private Cell generateSimulationSpecificCell(String simulationType, Location loc, String state, Grid grid,
                                                Grid nextGrid,
                                                Map<String, Double> parameters){
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
            case FORAGE_SIMULATION_NAME:
                return new ForagePatch(loc, ForageState.valueOf(state), grid, nextGrid, parameters);
            case SUGAR_SIMULATION_NAME:
                return new SugarPatch(loc, parameters, grid, state);
        }
        return new GOLCell(loc, GOLState.valueOf(state), grid, nextGrid);
    }

    /**
     * Should be overridden by the subclasses to return the name of their specific simulation
     * @return name of the simulation
     */
    public abstract String getMyName();

    /**
     * Returns the field names to look for when generating states by percentages while parsing XML
     * @return
     */
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
        updateNeighbors(myStyleProperties, NeighborsDefinitions.ADJACENT);
    }

    /**
     * Return list of the possible states a cell can have in the simulation
     * @return list of possible states a cell can take in the simulation
     */
    public List<String> getMyPossibleStates(){
        try{
            return myGrid.getCells().get(0).getCurrentCellState().getPossibleValues();
        }catch(IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("No states");
        }
    }

    /**
     * Getter for the parameters of the simulation
     * @return myParameters
     */
    public Map<String,Double> getInitialParams(){
        return myParameters;
    }

    /**
     * Updates the neighbors of the cells based on the map including neighborsType
     * @param styleProperties map including the neighborsType and new neighborstype
     */
    public void updateNeighbors(Map<String, String> styleProperties){
        this.updateNeighbors(styleProperties, NeighborsDefinitions.ADJACENT);
    }

    /**
     * Updates the neighbors based on what is included in the styleProperties map or to a default value as specified
     * by the defaultValue
     * @param styleProperties map containing style properties
     * @param defaultValue default neighbors type
     */
    public void updateNeighbors(Map<String, String> styleProperties, NeighborsDefinitions defaultValue){
        for (Cell cell : myGrid.getCells()){
            String neighbor = styleProperties.getOrDefault(XMLStyler.NEIGHBORS_TYPE_TAG_NAME, defaultValue.toString());
            cell.setMyNeighbors(NeighborsDefinitions.valueOf(neighbor.toUpperCase()));
        }
    }

    /**
     * Update the neighbors based on a String of the new neighbors type
     * @param neighborsString updated neighborsType
     */
    public void updateNeighbors(String neighborsString){
        for (Cell cell : myGrid.getCells()){
            cell.setMyNeighbors(NeighborsDefinitions.valueOf(neighborsString.toUpperCase()));
        }
    }

    /**
     * Setter for myStyleProperties
     * @param myStyleProperties map of style properties
     */
    public void setMyStyleProperties(Map<String, String> myStyleProperties) {
        this.myStyleProperties = myStyleProperties;
    }

}

