import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class XMLTester {

    public static void main(String[] args){

//        String testCase = "tests/SegregationTest.xml";
//        String testCase = "tests/SpreadingFireTest.xml";
//        String testCase = "tests/GOLTest.xml";
        //String testCase = "tests/PercolationTest.xml";
        String testCase = "tests/WatorTest.xml";
        //TODO: specify the path to the test case you created here

        File Testfile = new File(testCase);

        String styleTestCase = "tests/StyleTest1.XML";
        File styleFile = new File(styleTestCase);
        //var p = new XMLParser(Simulation.DATA_TYPE).getSimulation(file);
        var simulation = new setUpSimulation().setSimulation(Testfile, styleFile);
        try{
            Simulation currentSimulation = simulation;
            for (int i = 0; i < 10; i++){
                System.out.println("Iteration: "+i+"---------------------------");
                currentSimulation.updateGrid();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
