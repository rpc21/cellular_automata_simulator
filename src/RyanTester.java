import java.util.HashMap;

public class RyanTester {

    public static void main(String[] args) {
//        System.out.println(NeighborsDefinitions.ADJACENT);
//        System.out.println(NeighborsDefinitions.values());
//        System.out.println(GOLState.ALIVE);

        HashMap<String, String> basicParameters = new HashMap<>();
        basicParameters.put("rows", "4");
        basicParameters.put("columns", "4");
        basicParameters.put("simulationType", Simulation.FORAGE_SIMULATION_NAME);

        HashMap<String, Double> simulationSpecificParameters = new HashMap<>();
        simulationSpecificParameters.put(ForageSimulation.NUMBER_OF_ANTS, 20.0D);
        simulationSpecificParameters.put(ForageSimulation.MAX_FOOD_PHEROMONES, 10.0D);
        simulationSpecificParameters.put(ForageSimulation.MAX_HOME_PHEROMONES, 10.0D);

        String[][] initialStates = {{"NEST", "OBSTACLE", "EMPTY", "EMPTY"},
                {"EMPTY", "OBSTACLE", "EMPTY", "EMPTY"},
                {"EMPTY", "EMPTY", "EMPTY", "EMPTY"},
                {"FOOD", "OBSTACLE", "EMPTY", "EMPTY"}};

        Simulation antTest = new SimulationFactory().generateSimulation(basicParameters, simulationSpecificParameters
                , initialStates);

        for (int i = 0; i < 10; i++){
            antTest.updateGrid();
            antTest.getMyGrid().printGrid();
        }
    }
}
