import java.util.*;

public class SimulationFactory {
    private Random dice = new Random();

    public Simulation generateSimulation(HashMap<String, String> basicParameters, HashMap<String, Double> simulationSpecificParameters, String[][] initialStates){

        Simulation mySimulation = getSimulationWithEmptyGrid(basicParameters, simulationSpecificParameters);
        mySimulation.setInitialStates(initialStates, mySimulation.getMyName(),
                simulationSpecificParameters);
        return mySimulation;
    }

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

    public Simulation generateSimulation(Map<String, String> basicParameters,
                                         Map<String, Double> simulationSpecificParameters, String InitialStatesType){

        Simulation mySimulation = getSimulationWithEmptyGrid(basicParameters, simulationSpecificParameters);
        String[][] initialStates = createInitialStatesFromRandomPercentages(mySimulation, simulationSpecificParameters);
        mySimulation.setInitialStates(initialStates, mySimulation.getMyName(), simulationSpecificParameters);
        return mySimulation;
    }

    private String[][] createInitialStatesFromPercentages(Simulation mySimulation,
                                                          Map<String, Double> simulationSpecificParameters) {

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
                        key.split("P")[0].toUpperCase();
                index++;
            }
        }
        //TODO: Make sure we process the entire list, add default cells to the simulations
        return  initialStates;

    }

    private String[][] createInitialStatesFromRandomPercentages(Simulation mySimulation, Map<String, Double> simulationSpecificParameters) {
        generateRandomStatePercentages(mySimulation, simulationSpecificParameters);
        return createInitialStatesFromPercentages(mySimulation, simulationSpecificParameters);
    }

    private Simulation getSimulationWithEmptyGrid(Map<String, String> basicParameters, Map<String, Double> simulationSpecificParameters) {
        String simulationType = basicParameters.get("simulationType");
        int rows = Integer.parseInt(basicParameters.getOrDefault("rows", "10"));
        int cols = Integer.parseInt(basicParameters.getOrDefault("columns", "10"));
        String gridType = basicParameters.getOrDefault("gridType", Grid.BASIC_GRID_NAME);
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


    public double getRandomInRange(double min, double max) {
        return min + (max - min) * dice.nextDouble();
    }

    private void generateRandomStatePercentages(Simulation mySimulation, Map<String, Double> additionalParams){
        double maxPercentValue=1;
        double minPercentValue=0;
        for (String key : mySimulation.getPercentageFields()){
            double percentState = getRandomInRange(minPercentValue, maxPercentValue);
            additionalParams.put(key, percentState);
            maxPercentValue = maxPercentValue - percentState;
        }
    }
}
