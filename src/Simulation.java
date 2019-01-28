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
}
