import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Initialize {
    private String myTestCase;
    private Simulation myCurrentSimulation;
    public Initialize(String testCase){
        myTestCase = testCase;
        setUpSimulation();
    }
    private void setUpSimulation() {
        HashMap<String, Integer> simulationToOffsetMap = new HashMap<>();
        simulationToOffsetMap.put("Game of Life", 1200);
        simulationToOffsetMap.put("Spreading Fire", 140002);
        simulationToOffsetMap.put("Percolation", 150002);
        simulationToOffsetMap.put("Segregation", 13700);


        File file = new File(myTestCase);
        try {
            Scanner scanner = new Scanner(file);
            String simulationType = scanner.nextLine().strip();

            int numParameters = scanner.nextInt();
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();

            // create empty array to house the states
            int[][] specifiedStates = new int[rows][cols];

            // read in the states should be specified using 0, 1, 2, etc
            for (int i = 0; i < specifiedStates.length; i++) {
                for (int j = 0; j < specifiedStates[0].length; j++) {
                    specifiedStates[i][j] = scanner.nextInt() + simulationToOffsetMap.get(simulationType);
                }
            }
            // the parameters for the simulation
            HashMap<String, Double> parameters = new HashMap<>();
            // file should write a single string of the parameter name then a double for its setting
            for (int i = 0; i < numParameters; i++) {
                parameters.put(scanner.next(), scanner.nextDouble());
            }
            myCurrentSimulation = generateSimulation(simulationType, rows, cols);
            myCurrentSimulation.setInitialStates(specifiedStates, simulationType, parameters);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Simulation generateSimulation(String simulationType, int rows, int cols){
        if (simulationType.equals("Game of Life")){
            return new GOLSimulation(rows, cols);
        }
        else if (simulationType.equals("Spreading Fire")){
            return new SpreadingFireSimulation(rows, cols);
        }
        else if (simulationType.equals("Percolation")){
            return new PercolationSimulation(rows, cols);
        }
        else
            return new GOLSimulation(rows, cols);
    }
    public Simulation getSimulation(){
        return myCurrentSimulation;
    }

}
