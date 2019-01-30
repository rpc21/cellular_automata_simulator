import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Abstract superclass to define the common behaviors of all possible types of Cells.  Simulation-specific cells need
 * to extend from this class and implement the method calculateNewState and override methods that a specific to that
 * simulation.
 */
public abstract class Cell {
    protected Grid myGrid;
    protected Location myLocation;
    List<Cell> possibleNeighbors;
    protected Shape myShape;
    protected int myCurrentState;
    protected int myNextState;
    protected boolean empty;
    protected Map<String, Double> myParameters;

    public abstract void calculateNewState();

    /**
     * Used by the visualizer to get the colored shape associated with cell
     * @return COLORED shape for visualizer
     */
    public Shape getMyShape(){
        return myShape;
    }

    /**
     * Get the cell's location
     * @return myLocation
     */
    public Location getMyLocation(){
        return myLocation;
    }

    /**
     * Set a Cell's location to the specified location
     * @param myLocation the Location to move to
     */
    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    /**
     * Set a new state of the cell
     * @param newState the new state of the cell
     */
    public void setNewState(int newState){
        myCurrentState = newState;
    }

//    /**
//     * implement rule of which neighbors are possible given the simulation
//     * @return
//     */
//    protected void updatePossibleNeighbors(){
//        possibleNeighbors = myGrid.getNeighbors(myLocation);
//    }

    /**
     * Changes the cell to the next state
     */
    public void updateState(){
        myCurrentState = myNextState;
        myNextState = 0;
    }

    /**
     *
     * @return
     */
    public int getMyCurrentState() {
        return myCurrentState;
    }

    public void setMyCurrentState(int myCurrentState) {
        this.myCurrentState = myCurrentState;
    }

    public int getMyNextState() {
        return myNextState;
    }

    public void setMyNextState(int myNextState) {
        this.myNextState = myNextState;
    }

    public void setMyGrid(Grid myGrid) {
        this.myGrid = myGrid;
    }

    public Grid getMyGrid() {
        return myGrid;
    }

    public void setMyParameters(Map<String, Double> myParameters) {
        this.myParameters = myParameters;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public abstract Color getMyColor();
}
