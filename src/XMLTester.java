import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class XMLTester {

    public static void main(String[] args){

//        String testCase = "tests/GOLTest1.txt";
        //      String testCase = "tests/SpreadingFire1.txt";
//        String testCase = "tests/PercolationTest1.txt";
        String testCase = "tests/GOLXML.xml";
        //TODO: specify the path to the test case you created here

        File file = new File(testCase);
        var p = new XMLParser(Simulation.DATA_TYPE).getSimulation(file);
        try{
            Simulation currentSimulation = p;
            for (int i = 0; i < 10; i++){
                System.out.println("Iteration: "+i+"---------------------------");
                currentSimulation.updateGrid();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
