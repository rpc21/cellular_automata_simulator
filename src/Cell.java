import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Cell {
    private Grid myGrid;
    private Location myLocation;
    List<Cell> possibleNeighbors;
    private Shape myShape;
    private int myCurrentState;

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


}
