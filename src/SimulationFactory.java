import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SimulationFactory {

    public Simulation generateSimulation(HashMap<String, String> basicParameters, HashMap<String, Double> simulationSpecificParameters, String[][] initialStates){

        Simulation mySimulation = getSimulationWithEmptyGrid(basicParameters, simulationSpecificParameters);
        mySimulation.setInitialStates(initialStates, mySimulation.getMyName(),
                simulationSpecificParameters);

        return mySimulation;
    }

    public Simulation generateSimulation(HashMap<String, String> basicParameters,
                                         HashMap<String, Double> simulationSpecificParameters){
        for (String a: basicParameters.keySet())
            System.out.println(a + basicParameters.get(a));
        Simulation mySimulation = getSimulationWithEmptyGrid(basicParameters, simulationSpecificParameters);
        String[][] initialStates = createInitialStatesFromPercentages(mySimulation, simulationSpecificParameters);
        mySimulation.setInitialStates(initialStates, mySimulation.getMyName(), simulationSpecificParameters);
        return mySimulation;
    }

    private String[][] createInitialStatesFromPercentages(Simulation mySimulation, HashMap<String, Double> simulationSpecificParameters) {

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

    private Simulation getSimulationWithEmptyGrid(HashMap<String, String> basicParameters, HashMap<String, Double> simulationSpecificParameters) {
        String simulationType = basicParameters.get("simulationType");
        int rows = Integer.parseInt(basicParameters.get("rows"));
        int cols = Integer.parseInt(basicParameters.get("columns"));
        return selectSimulationConstructor(simulationType, rows, cols, simulationSpecificParameters);
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

    private Simulation selectSimulationConstructor(String simulationType, int rows, int cols, HashMap<String, Double> simulationSpecificParameters){
        switch (simulationType) {
            case Simulation.GOL_SIMULATION_NAME:
                return new GOLSimulation(simulationSpecificParameters, rows, cols);
            case Simulation.SPREADING_FIRE_SIMULATION_NAME:
                return new SpreadingFireSimulation(simulationSpecificParameters, rows, cols);
            case Simulation.PERCOLATION_SIMULATION_NAME:
                return new PercolationSimulation(simulationSpecificParameters, rows, cols);
            case Simulation.SEGREGATION_SIMULATION_NAME:
                return new SegregationSimulation(simulationSpecificParameters, rows, cols);
            case Simulation.WATOR_SIMULATION_NAME:
                return new WatorSimulation(simulationSpecificParameters, rows, cols);
        }
        return new GOLSimulation(simulationSpecificParameters, rows, cols);
    }
}
