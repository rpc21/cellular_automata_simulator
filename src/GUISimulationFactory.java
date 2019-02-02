public class GUISimulationFactory {
    public String makeXMLFileName(String newSim){
        String testCase;
        switch(newSim){
            case "Game of Life":
                testCase = "tests/GOLTest.xml";
                break;
            case "Spreading Fire":
                testCase = "tests/SpreadingFireTest.xml";
                break;
            case "Percolation":
                testCase = "tests/PercolationTest.xml";
                break;
            case "Segregation":
                testCase = "tests/SegregationTest.xml";
                break;
            default:
                testCase = "tests/GOLTest.xml";
                break;
        }
        return testCase;
    }
    public GUISimulationPanel makeSimulationPanel(String newSim, Simulation mySim){
        GUISimulationPanel mySimPanel;
        switch(newSim){
            case "Game of Life":
                mySimPanel = new GUIGameOfLifePanel(mySim);
                break;
            case "Spreading Fire":
                mySimPanel = new GUISpreadingFirePanel(mySim);
                break;
            case "Percolation":
                mySimPanel = new GUIPercolationPanel(mySim);
                break;
            case "Segregation":
                mySimPanel = new GUISegregationPanel(mySim);
                break;
            default:
                mySimPanel = new GUIGameOfLifePanel(mySim);
                break;
        }
        return mySimPanel;
    }
}
