import java.util.HashMap;

public class SimulationFactory {

    public Simulation generateSimulation(HashMap<String, String> basicParameters,
                              HashMap<String, Double> simulationSpecificParameters){
        String simulationType = basicParameters.get("simulationType");
        int rows = Integer.parseInt(basicParameters.get("rows"));
        int cols = Integer.parseInt(basicParameters.get("columns"));
        if (simulationType.equals("Game of Life")){
            return new GOLSimulation(rows, cols);
        }
        else if (simulationType.equals("Spreading Fire")){
            return new SpreadingFireSimulation(rows, cols);
        }
        else if (simulationType.equals("Percolation")){
            return new PercolationSimulation(rows, cols);
        }
        else if (simulationType.equals("Segregation")){ return new SegregationSimulation(rows, cols); }
        return new GOLSimulation(rows, cols);
    }
}
