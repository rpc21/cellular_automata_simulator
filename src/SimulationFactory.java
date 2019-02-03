import java.util.HashMap;

public class SimulationFactory {

    public Simulation generateSimulation(HashMap<String, String> basicParameters, HashMap<String, Double> simulationSpecificParameters){
        String simulationType = basicParameters.get("simulationType");
        int rows = Integer.parseInt(basicParameters.get("rows"));
        int cols = Integer.parseInt(basicParameters.get("columns"));
        Simulation mySimulation = selectSimulationConstructor(simulationType, rows, cols, simulationSpecificParameters);

        return mySimulation;
    }

    private Simulation selectSimulationConstructor(String simulationType, int rows, int cols, HashMap<String, Double> simulationSpecificParameters){
        if (simulationType.equals(Simulation.GOL_SIMULATION_NAME)){
            return new GOLSimulation(simulationSpecificParameters, rows, cols);
        }
        else if (simulationType.equals(Simulation.SPREADING_FIRE_SIMULATION_NAME)){
            return new SpreadingFireSimulation(simulationSpecificParameters, rows, cols);
        }
        else if (simulationType.equals(Simulation.PERCOLATION_SIMULATION_NAME)){
            return new PercolationSimulation(simulationSpecificParameters, rows, cols);
        }
        else if (simulationType.equals(Simulation.SEGREGATION_SIMULATION_NAME)){
            return new SegregationSimulation(simulationSpecificParameters, rows, cols);
        }
        else if (simulationType.equals(Simulation.WATOR_SIMULATION_NAME)){
            return new WatorSimulation(simulationSpecificParameters, rows, cols);
        }
        return new GOLSimulation(simulationSpecificParameters, rows, cols);
    }
}
