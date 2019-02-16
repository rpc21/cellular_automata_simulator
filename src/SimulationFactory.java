import java.util.*;

public class SimulationFactory {
    public static final String THE_MAGIC_LETTER_UPON_WHICH_ALL_OUR_CODE_IS_HINGED = "P";
    public static final String SIMULATION_TYPE = "simulationType";
    public static final String ROWS = "rows";
    public static final String COLUMNS = "columns";
    public static final String EDGES = "edges";
    public static final String DEFAULT_NUMBER_OF_ROWS = "10";
    public static final String DEFAULT_NUMBER_OF_COLS = "10";
    private Random dice = new Random();

    /**
     * Generates a simulation based on the basic parameters, simulation specific parameters and initial states
     * @param basicParameters simulation name, rows, and cols
     * @param simulationSpecificParameters parameters specific to the simulation
     * @param initialStates 2-D array of initial states
     * @return new Simulation
     */
    public Simulation generateSimulation(HashMap<String, String> basicParameters, HashMap<String, Double> simulationSpecificParameters, String[][] initialStates){

        Simulation mySimulation = getSimulationWithEmptyGrid(basicParameters, simulationSpecificParameters);
        mySimulation.setInitialStates(initialStates, mySimulation.getMyName(),
                simulationSpecificParameters);
        return mySimulation;
    }

    /**
     * Generates simulation without having to specify all the initial states
     * @param basicParameters simulation type, number of rows, number of columns
     * @param simulationSpecificParameters simulation specific parameters
     * @return a new simulation
     */
    public Simulation generateSimulation(Map<String, String> basicParameters,
                                          Map<String, Double> simulationSpecificParameters){
        for (String a: basicParameters.keySet())
            System.out.println(a + basicParameters.get(a));
        Simulation mySimulation = getSimulationWithEmptyGrid(basicParameters, simulationSpecificParameters);
        String[][] initialStates = createInitialStatesFromPercentages(mySimulation, simulationSpecificParameters);
        mySimulation.setInitialStates(initialStates, mySimulation.getMyName(), simulationSpecificParameters);
        mySimulation.setMyStyleProperties(basicParameters);
        mySimulation.updateNeighbors(basicParameters);
        return mySimulation;
    }

    /**
     * Credentials also passed in
     * @param basicParameters
     * @param simulationSpecificParameters
     * @param credentials
     * @return
     */
    public Simulation generateSimulation(Map<String, String> basicParameters, Map<String, Double> simulationSpecificParameters, Map<String, String> credentials){
        Simulation myNewSimulation = generateSimulation(basicParameters, simulationSpecificParameters);
        myNewSimulation.setCredentials(credentials);
        return myNewSimulation;
    }

    /**
     * Generates a simulation based on the basic parameters, simulation specific parameters and initial states
     * @param basicParameters simulation name, rows, and cols
     * @param simulationSpecificParameters parameters specific to the simulation
     * @param initialStatesType specifies how states should be randomly generated
     * @return new Simulation
     */
    public Simulation generateSimulation(Map<String, String> basicParameters,
                                         Map<String, Double> simulationSpecificParameters, String initialStatesType){

        Simulation mySimulation = getSimulationWithEmptyGrid(basicParameters, simulationSpecificParameters);
        String[][] initialStates = createInitialStatesFromRandomPercentages(mySimulation, simulationSpecificParameters);
        mySimulation.setInitialStates(initialStates, mySimulation.getMyName(), simulationSpecificParameters);
        return mySimulation;
    }

    private String[][] createInitialStatesFromPercentages(Simulation mySimulation,
                                                          Map<String, Double> simulationSpecificParameters) {

        if (!simulationSpecificParameters.keySet().containsAll(mySimulation.getPercentageFields())){
            return createInitialStatesFromRandomPercentages(mySimulation, simulationSpecificParameters);
        }
        int rows = mySimulation.getMyGrid().getNumRows();
        int cols = mySimulation.getMyGrid().getNumCols();
        ArrayList<Location> gridLocations = getShuffledGridLocations(rows, cols);
        String[][] initialStates = new String[rows][cols];
        int index = 0;
        int currentMaxIndex = 0;
        for (String key : mySimulation.getPercentageFields()){
            currentMaxIndex += (int) Math.ceil(simulationSpecificParameters.get(key) * gridLocations.size());
            while (index < currentMaxIndex && index < gridLocations.size()){
                initialStates[gridLocations.get(index).getRow()][gridLocations.get(index).getCol()] =
                        key.split(THE_MAGIC_LETTER_UPON_WHICH_ALL_OUR_CODE_IS_HINGED)[0].toUpperCase();
                index++;
            }
        }
        while(index<gridLocations.size()){
            initialStates[gridLocations.get(index).getRow()][gridLocations.get(index).getCol()] =
                    mySimulation.getPercentageFields().get(0).split(THE_MAGIC_LETTER_UPON_WHICH_ALL_OUR_CODE_IS_HINGED)[0].toUpperCase();
            index++;
        }
        return  initialStates;

    }

    private String[][] createInitialStatesFromRandomPercentages(Simulation mySimulation, Map<String, Double> simulationSpecificParameters) {
        generateRandomStatePercentages(mySimulation, simulationSpecificParameters);
        return createInitialStatesFromPercentages(mySimulation, simulationSpecificParameters);
    }

    private Simulation getSimulationWithEmptyGrid(Map<String, String> basicParameters, Map<String, Double> simulationSpecificParameters) {
        String simulationType = basicParameters.get(SIMULATION_TYPE);
        int rows = Integer.parseInt(basicParameters.getOrDefault(ROWS, DEFAULT_NUMBER_OF_ROWS));
        int cols = Integer.parseInt(basicParameters.getOrDefault(COLUMNS, DEFAULT_NUMBER_OF_COLS));
        String gridType = basicParameters.getOrDefault(EDGES, Grid.BASIC_GRID_NAME);
        Grid grid = new GridFactory().generateGrid(gridType, rows, cols);
        return selectSimulationConstructor(simulationType, grid, simulationSpecificParameters);
    }


    private ArrayList<Location> getShuffledGridLocations(int rows, int cols) {
        ArrayList<Location> gridLocations = new ArrayList<>();
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                gridLocations.add(new Location(i, j));
            }
        }
        Collections.shuffle(gridLocations);
        return gridLocations;
    }

    private Simulation selectSimulationConstructor(String simulationType, Grid grid,
                                                   Map<String, Double> simulationSpecificParameters){
        switch (simulationType) {
            case Simulation.GOL_SIMULATION_NAME:
                return new GOLSimulation(simulationSpecificParameters, grid);
            case Simulation.SPREADING_FIRE_SIMULATION_NAME:
                return new SpreadingFireSimulation(simulationSpecificParameters, grid);
            case Simulation.PERCOLATION_SIMULATION_NAME:
                return new PercolationSimulation(simulationSpecificParameters, grid);
            case Simulation.SEGREGATION_SIMULATION_NAME:
                return new SegregationSimulation(simulationSpecificParameters, grid);
            case Simulation.WATOR_SIMULATION_NAME:
                return new WatorSimulation(simulationSpecificParameters, grid);
            case Simulation.FORAGE_SIMULATION_NAME:
                return new ForageSimulation(simulationSpecificParameters, grid);
            case Simulation.SUGAR_SIMULATION_NAME:
                return new SugarSimulation(simulationSpecificParameters, grid);
        }
        return new GOLSimulation(simulationSpecificParameters, grid);
    }


    private double getRandomInRange(double min, double max) {
        return min + (max - min) * dice.nextDouble();
    }

    private void generateRandomStatePercentages(Simulation mySimulation, Map<String, Double> additionalParams){
        double maxPercentValue=1;
        double minPercentValue=0;
        for(int i=0;i<mySimulation.getPercentageFields().size()-1;i++){
            String key = mySimulation.getPercentageFields().get(i);
            double percentState = getRandomInRange(minPercentValue, maxPercentValue);
            additionalParams.put(key, percentState);
            maxPercentValue = maxPercentValue - percentState;
        }
        String key = mySimulation.getPercentageFields().get(mySimulation.getPercentageFields().size()-1);
        double percentState = maxPercentValue;
        additionalParams.put(key, percentState);

    }
}
