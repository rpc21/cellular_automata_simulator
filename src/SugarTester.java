import java.util.HashMap;

public class SugarTester {
    public static void main(String[] args) {
//        System.out.println(NeighborsDefinitions.ADJACENT);
//        System.out.println(NeighborsDefinitions.values());
//        System.out.println(GOLState.ALIVE);

        NeighborsDefinitions nd = NeighborsDefinitions.valueOf(NeighborsDefinitions.BOX_NEIGHBORS.toString());

        HashMap<String, String> basicParameters = new HashMap<>();
        basicParameters.put("rows", "4");
        basicParameters.put("columns", "4");
        basicParameters.put("simulationType", Simulation.SUGAR_SIMULATION_NAME);

        HashMap<String, Double> simulationSpecificParameters = new HashMap<>();
        simulationSpecificParameters.put(SugarSimulation.SUGAR_AGENT_PERCENTAGE, 0.5D);
        simulationSpecificParameters.put(SugarSimulation.SUGAR_GROW_BACK_RATE, 1.0D);
        simulationSpecificParameters.put(SugarAgent.INIT_SUGAR, 2.0D);
        simulationSpecificParameters.put(SugarAgent.MAX_METABOLISM, 7.0D);
        simulationSpecificParameters.put(SugarAgent.MAX_VISION, 2.0D);

        String[][] initialStates = {{"3", "14", "1", "4"},
                {"23", "42", "14", "13"},
                {"14", "13", "74", "0"},
                {"5", "3", "5", "3"}};

        Simulation sugarTest = new SimulationFactory().generateSimulation(basicParameters, simulationSpecificParameters
                , initialStates);

        for (int i = 0; i < 10; i++){
            sugarTest.updateGrid();
            sugarTest.getMyGrid().printGrid();
            System.out.println("====================================");
        }

        for (Cell cell : sugarTest.getMyGrid().getCells()){
            System.out.println(cell.getMyCurrentState());
        }
    }
}
