import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Tester {


    public static void main(String[] args){

        HashMap<String, Integer> simulationToOffsetMap = new HashMap<>();
        simulationToOffsetMap.put("Game of Life", 1200);
        simulationToOffsetMap.put("Spreading Fire", 140002);
        simulationToOffsetMap.put("Percolation", 150002);
        simulationToOffsetMap.put("Segregation", 13700);

//        String testCase = "tests/GOLTest1.txt";
//        String testCase = "tests/SpreadingFire1.txt";
        String testCase = "tests/PercolationTest1.txt";
        //TODO: specify the path to the test case you created here

        File file = new File(testCase);
        // Initialize scanner to read in txt file
        try{
            Scanner scanner = new Scanner(file);

            // Read in the simulation type as a string - should be on its own line
            // Possible options: Game of Life, Spreading Fire, Percolation
            String simulationType = scanner.nextLine().strip();

            // next line contains three integers: the number of parameters to be specified,
            // number of rows for the grid, number of columns for the grid
            int numParameters = scanner.nextInt();
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();

            // create empty array to house the states
            int[][] specifiedStates = new int[rows][cols];

            // read in the states should be specified using 0, 1, 2, etc
            for (int i = 0; i < specifiedStates.length; i++){
                for (int j = 0; j < specifiedStates[0].length; j++){
                    specifiedStates[i][j] = scanner.nextInt() + simulationToOffsetMap.get(simulationType);
                }
            }

            // the parameters for the simulation
            HashMap<String, Double> parameters = new HashMap<>();

            // file should write a single string of the parameter name then a double for its setting
            for (int i = 0; i < numParameters; i++){
                parameters.put(scanner.next(), scanner.nextDouble());
            }

            Simulation currentSimulation = generateSimulation(simulationType, rows, cols);

            currentSimulation.setInitialStates(specifiedStates, simulationType, parameters);

            for (int i = 0; i < 10; i++){
                System.out.println("Iteration: "+i+"---------------------------");
                currentSimulation.updateGrid();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static Simulation generateSimulation(String simulationType, int rows, int cols){
        if (simulationType.equals("Game of Life")){
            return new GOLSimulation(rows, cols);
        }
        else if (simulationType.equals("Spreading Fire")){
            return new SpreadingFireSimulation(rows, cols);
        }
        else if (simulationType.equals("Percolation")){
            return new PercolationSimulation(rows, cols);
        }
        //Todo: when SegregationSimulation is ready, uncomment these three lines
//        else if (simulationType.equals("Segregation")){
//            return new SegregationSimulation(rows, cols);
//        }
        return new GOLSimulation(rows, cols);
    }
}
