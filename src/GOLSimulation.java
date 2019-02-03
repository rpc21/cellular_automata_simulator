import java.util.List;

/**
 * GOLSimulation handles the GameOfLife simulation, communicating between the individual cells and the Visualization
 */
public class GOLSimulation extends Simulation {

    /**
     * Constructor for the GOLSimulation. Initializes the grid for the simulation to a BasicGrid of size rows x cols
     * @param rows number of rows in the grid for the simulation
     * @param cols number of columns in the grid for the simulation
     */
    public GOLSimulation(int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
    }

    @Override
    public void simulate(double simulationSpeed) {

    }

    /**
     * Checks if the simulation is over
     * @return whether or not the simulation is over
     */
    @Override
    public boolean isOver() {
        for (Cell cell: myGrid.getCells()){
            if (((GOLCell) cell).isAlive()){
                return false;
            }
        }
        return true;
    }

}
