import java.util.List;

public class SpreadingFireSimulation extends Simulation {

    /**
     * Constructor for the SpreadingFireSimulation. Initializes the grid for the simulation to a BasicGrid of size
     * rows x cols
     * @param rows number of rows in the grid for the simulation
     * @param cols number of columns in the grid for the simulation
     */

    public static final String PROB_CATCH = "probCatch";

    public SpreadingFireSimulation(int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
    }

    @Override
    public void simulate(double simulationSpeed) {

    }

    /**
     * If there are any fires, there is at least one more round
     * @return whether or not the simulation is over
     */
    @Override
    public boolean isOver() {
        for (Cell cell: myGrid.getCells()){
            if (((SpreadingFireCell) cell).isOnFire()){
                return false;
            }
        }
        return true;
    }
}
