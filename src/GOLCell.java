import javafx.scene.paint.Color;
import java.util.List;

/**
 * The GOLCell extend the abstract Cell class and implements the rule of Game Of Life simulation.  The GOLSimulation
 * has a grid composed of GOLCells
 * Box neighbors, <2 neighbors alive = DIE, 2 0r 3 live will live, more than 3 live neighbors = die
 * dead with =3 live neighbors = ALIVE
 * Need to know my state and how many ALIVE neighbors
 */
public class GOLCell extends Cell{

    //Game of Life defines neighbors to be the 4 adjacent neighbors and 4 diagonal neighbors as specified below
    private static int[] GOL_CELL_ROW_NEIGHBORS = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static int[] GOL_CELL_COL_NEIGHBORS = {-1, -1, -1, 0, 0, 1, 1, 1};

    /**
     * Constructor for GOLCell, calls the super constructor from Cell abstract class
     * @param location location of the cell
     * @param initialState initial state of the cell - must be a GOLState (either dead or alive)
     * @param grid grid used in the simulation
     */
    public GOLCell(Location location, GOLState initialState, Grid grid){
        super(location, initialState, grid);
    }

    /**
     * Constructor for GOLCell, calls the super constructor from Cell abstract class
     * @param location location of the cell
     * @param initialState initial state represented as an int, used to convert into a GOLState (0 for DEAD 1 for ALIVE)
     * @param grid grid used in the simulation
     */
    public GOLCell(Location location, int initialState, Grid grid){
        this(location, GOLState.values()[initialState], grid);
    }

    /**
     * Implement Game of Life rules
     * Box neighbors, <2 neighbors alive = DIE, 2 0r 3 live will live, more than 3 live neighbors = die
     * dead with =3 live neighbors = ALIVE
     * Need to know my state and how many ALIVE neighbors
     * @return the new state
     */
    @Override
    public void calculateNewState() {
        List<Location> myNeighborLocations = myGrid.getValidNeighbors(myLocation, GOL_CELL_ROW_NEIGHBORS, GOL_CELL_COL_NEIGHBORS);
        int numAlive = calcNumLiveNeighbors(myNeighborLocations);
        if(needsToLive(numAlive)){
            myNextState = GOLState.ALIVE;
        }
        else{
            myNextState = GOLState.DEAD;
        }
    }

    //TODO: Decided to not use because it is just the else of needsToLive and just having these two caused us to miss
    // cases leading to NPEs, permission to delete?
    private boolean needsToDie(int numAlive){
        return ((isAlive() && numAlive < 2)|| (isAlive() && numAlive > 3));
    }

    private boolean needsToLive(int numAlive){
        return ((isAlive() && numAlive == 2) || (isAlive() && numAlive == 3) || (!isAlive() && numAlive==3));
    }

    private int calcNumLiveNeighbors(List<Location> locationList){
        int numAlive = 0;
        for(Location l:locationList){
            GOLCell tempCell =(GOLCell)getMyGrid().get(l);
            if(tempCell.isAlive()){
                numAlive++;
            }
        }
        return numAlive;
    }

    /**
     * Returns whether the cell is alive
     * @return boolean if the cell is alive
     */
    public boolean isAlive() {
        return (myCurrentState == GOLState.ALIVE);
    }

    /**
     * Return a String of length 1 representing the state of the cell
     * @return String of length 1 representing the state of the cell as defined in GOLState Enum
     */
    @Override
    public String toString() {
        return myCurrentState.getMyShortenedName();
    }

    /**
     * Return the color to display to represent the state of this cell
     * @return Color to display for this state as defined in GOLState Enum
     */
    @Override
    public Color getMyColor() {
        return myCurrentState.getMyCellColor();
    }


}

