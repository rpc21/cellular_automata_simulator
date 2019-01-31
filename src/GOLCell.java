import java.awt.*;
import java.util.List;

public class GOLCell extends Cell{

    private static int[] GOL_CELL_ROW_NEIGHBORS = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static int[] GOL_CELL_COL_NEIGHBORS = {-1, -1, -1, 0, 0, 1, 1, 1};

    private GOLState myCurrentState;
    private GOLState myNextState;

    /**
     * Implement Game of Life rules
     * Box neighbors, <2 neighbors alive = DIE, 2 0r 3 live will live, more than 3 live neighbors = die
     * dead with =3 live neighbors = ALIVE
     * Need to know my state and how many ALIVE neighbors
     * @return the new state
     */
    public GOLCell(Location location, int initialState, Grid grid){
        myGrid = grid;
        if (initialState == 1){
            myCurrentState = GOLState.ALIVE;
        } else{
            myCurrentState = GOLState.DEAD;
        }
        myLocation=location;
    }

    @Override
    public void calculateNewState() {
        List<Location> myNeighborLocations = myGrid.getValidNeighbors(myLocation, GOL_CELL_ROW_NEIGHBORS, GOL_CELL_COL_NEIGHBORS);
        int numAlive = calcNumLiveNeighbors(myNeighborLocations);
        if(needsToDie(numAlive)){
            myNextState = GOLState.DEAD;
        }else if(needsToLive(numAlive)){
            myNextState = GOLState.ALIVE;
        }
    }

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

    public boolean isAlive() {
        return (myCurrentState == GOLState.ALIVE);
    }

    @Override
    public String toString() {
        return myCurrentState.toString();
    }

    @Override
    public Color getMyColor() {
        return myCurrentState.getMyCellColor();
    }
}
