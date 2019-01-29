import java.util.HashSet;
import java.util.List;

public abstract class Simulation {

    private Grid myGrid;
    private boolean simulationOver;

    public abstract void simulate(double simulationSpeed);

    public void updateGrid(){
        List<Cell> cells = myGrid.getGrid();
        for (Cell cell : cells){
            cell.calculateNewState();
        }
        for (Cell cell : cells){
            cell.updateState();
        }
        myGrid.printGrid();
    }

    public abstract void updateNewParams(List<Integer> paramList);

    public void stopSimulation(){
        simulationOver = true;
    }

    public abstract boolean checkIfDone();

    public void setMyGrid(Grid myGrid) {
        this.myGrid = myGrid;
    }

    public Grid getMyGrid() {
        return myGrid;
    }

    public void setInitialStates(int[][] initialStates, String simulationType){
        for (int i = 0; i < getMyGrid().getNumRows(); i++){
            for (int j = 0; j < getMyGrid().getNumCols(); j++){
                Location thisLocation = new Location(i, j);
                System.out.println("Creating a "+simulationType+" cell");
                Cell newCell = generateSimulationSpecificCell(simulationType, thisLocation, initialStates[i][j], myGrid);
                System.out.println(newCell + " to be inserted at "+ i + ", "+j);
                System.out.println(newCell.getMyLocation().getRow()+", "+newCell.getMyLocation().getCol());
                getMyGrid().put(newCell.getMyLocation(), newCell);
            }
        }
        getMyGrid().printGrid();
        System.out.println("Initial states set");
    }

    private Cell generateSimulationSpecificCell(String simulationType, Location loc, int state, Grid grid){
        if (simulationType.equals("Game of Life")){
            return new GOLCell(loc, state, grid);
        }
        else if (simulationType.equals("Spreading Fire")){
            return new SpreadingFireCell(loc, state, grid);
        }
        else if (simulationType.equals("Percolation")){
            return new PercolationCell(loc, state, grid);
        }
        else if (simulationType.equals("Segregation")){
            return new SegregationCell(loc, state, grid, 0.3D);
        }
        return new GOLCell(loc, state, grid);
    }
}
