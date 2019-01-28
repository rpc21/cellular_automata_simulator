import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Cell {
    protected Grid myGrid;
    protected Location myLocation;
    List<Cell> possibleNeighbors;
    protected Shape myShape;
    protected int myCurrentState;
    protected int myNextState;

    public abstract void calculateNewState();

    /**
     * Used by the visualizer to get the colored shape associated with cell
     * @return COLORED shape for visualizer
     */
    public Shape getMyShape(){
        return myShape;
    }

    public Location getMyLocation(){
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    public void setNewState(int newState){
        myCurrentState = newState;
    }

    /**
     * implement rule of which neighbors are possible given the simulation
     * @return
     */
    protected void updatePossibleNeighbors(){
        possibleNeighbors = myGrid.getNeighbors(myLocation);
    }

    public void updateState(){
        myCurrentState = myNextState;
        myNextState = 0;
    }

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
}
