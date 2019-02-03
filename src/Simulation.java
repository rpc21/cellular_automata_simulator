import java.util.*;

public abstract class Simulation {

    protected Grid myGrid;
    protected boolean simulationOver;
    protected CellFactory myCellFactory;
    protected HashMap<String, Double> myParameters;

    // Data fields
    public static final String DATA_TYPE = "simulation";
    public static final List<String> DATA_FIELDS = List.of(
            "simulationType",
            "rows",
            "columns"
    );

    public Simulation(HashMap<String, Double> parameters){
        this();
        myParameters = parameters;
    }

    public Simulation(){
        myCellFactory = new CellFactory();
    }

    public abstract void simulate(double simulationSpeed);

    public void updateGrid(){
        List<Cell> cells = myGrid.getCells();
        for (Cell cell : cells){
            cell.calculateNewState();
        }
        for (Cell cell : cells){
            cell.updateState();
//            System.out.println(cell.getMyCurrentState());
        }
        myGrid.printGrid();
    }

    /**
     * Passes the information about parameter updates from the visualization to the individual cells
     * @param parameters
     */
    public void updateNewParams(Map<String, Double> parameters){
        for (String param : parameters.keySet()){
            myParameters.put(param, parameters.get(param));
        }
    }

    public void stopSimulation(){
        simulationOver = true;
    }

    /**
     * Checks if the simulation is over
     * @return whether or not the simulation is over
     */
    public abstract boolean isOver();

    public void setMyGrid(Grid myGrid) {
        this.myGrid = myGrid;
    }

    public Grid getMyGrid() {
        return myGrid;
    }

    public void setInitialStates(String[][] initialStates, String simulationType, HashMap<String, Double> parameters){
        for (int i = 0; i < getMyGrid().getNumRows(); i++){
            for (int j = 0; j < getMyGrid().getNumCols(); j++){
                Location thisLocation = new Location(i, j);
                System.out.println("Creating a "+simulationType+" cell");
                Cell newCell = myCellFactory.generateSimulationSpecificCell(simulationType, thisLocation,
                        initialStates[i][j], myGrid, parameters);
                printCellInfo(i, j, newCell);
            }
        }
        getMyGrid().printGrid();
        System.out.println("Initial states set");
    }


    private void setInitialStates(int[][] initialStates, String simulationType, HashMap<String, Double> parameters) {
        for (int i = 0; i < getMyGrid().getNumRows(); i++){
            for (int j = 0; j < getMyGrid().getNumCols(); j++){
                Location thisLocation = new Location(i, j);
                System.out.println("Creating a "+simulationType+" cell");
                Cell newCell = myCellFactory.generateSimulationSpecificCell(simulationType, thisLocation,
                        initialStates[i][j],
                        myGrid, parameters);
                printCellInfo(i, j, newCell);
            }
        }
        getMyGrid().printGrid();
        System.out.println("Initial states set");
    }

    private void printCellInfo(int i, int j, Cell newCell) {
        System.out.println(newCell + " to be inserted at " + i + ", " + j);
        System.out.println(newCell.getMyLocation().getRow() + ", " + newCell.getMyLocation().getCol());
        getMyGrid().put(newCell.getMyLocation(), newCell);
    }

}
