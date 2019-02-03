import java.util.List;

public class PercolationSimulation extends Simulation {

    /**
     * Constructor for the PercolationSimulation. Initializes the grid for the simulation to a BasicGrid of size rows x
     * cols
     * @param rows number of rows in the grid for the simulation
     * @param cols number of columns in the grid for the simulation
     */
    public PercolationSimulation(int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
    }

    @Override
    public void simulate(double simulationSpeed) {}

    @Override
    public boolean isOver(){
        for (Cell cell : myGrid.getCells()){
            if (cell.isEmpty() && ((PercolationCell) cell).nextToPercolatedCell()){
                return false;
            }
        }
        return true;
    }

}
