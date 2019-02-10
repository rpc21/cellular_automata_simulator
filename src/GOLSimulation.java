import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GOLSimulation handles the GameOfLife simulation, communicating between the individual cells and the Visualization
 */
public class GOLSimulation extends Simulation {
    public static final String DEAD_PERCENTAGE = "deadPercentage";
    public static final String ALIVE_PERCENTAGE = "alivePercentage";
    public static final List<String> GOL_DATA_FIELDS = List.of(
            DEAD_PERCENTAGE, ALIVE_PERCENTAGE
    );


    /**
     * Constructor for the GOLSimulation. Initializes the grid for the simulation to a BasicGrid of size rows x cols
     * @param rows number of rows in the grid for the simulation
     * @param cols number of columns in the grid for the simulation
     */
    public GOLSimulation(HashMap<String, Double> params, int rows, int cols){
        super(params, rows, cols);
        setMyGrid(new BasicGrid(rows, cols));
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

    @Override
    public String getMyName() {
        return Simulation.GOL_SIMULATION_NAME;
    }

    @Override
    public List<String> getPercentageFields() {
        return List.of(DEAD_PERCENTAGE, ALIVE_PERCENTAGE);
    }
}
