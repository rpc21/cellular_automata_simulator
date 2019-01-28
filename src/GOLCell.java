import java.util.List;

public class GOLCell extends Cell{

    private static final int ALIVE = 1200;
    private static final int DEAD = 1201;
    private boolean alive;
    private List<Location> myNeighborLocations;
    private Grid myGrid;
    private Location myLocation;
    private int myNextState;
    private int currentState;
    /**
     * Implement Game of Life rules
     * Box neighbors, <2 neighbors alive = DIE, 2 0r 3 live will live, more than 3 live neighbors = die
     * dead with =3 live neighbors = ALIVE
     * Need to know my state and how many ALIVE neighbors
     * @return the new state
     */

    public GOLCell(Location location, int initialState, Grid grid){
        currentState = initialState;
        myNextState = 0;
        myLocation = location;
        alive = isAlive();
        myGrid = grid;
    }

    @Override
    public void calculateNewState() {
        myNeighborLocations = myGrid.getValidBoxLocations(myLocation);
        int numAlive = calcNumLiveNeighbors(myNeighborLocations);
        if(needsToDie(numAlive)){
            myNextState = DEAD;
        }else if(needsToLive(numAlive)){
            myNextState = ALIVE;
        }
    }

    private boolean needsToDie(int numAlive){
        return (isAlive()&&numAlive<2||isAlive()&&numAlive>3);
    }
    private boolean needsToLive(int numAlive){
        return (isAlive()&&numAlive==2||isAlive()&&numAlive==3||!isAlive()&&numAlive==3);
    }

    private int calcNumLiveNeighbors(List<Location> locationList){
        int numAlive = 0;
        for(Location l:locationList){
            GOLCell tempCell =(GOLCell)myGrid.get(l);
            if(tempCell.isAlive()){ numAlive++; }
        }
        return numAlive;
    }

    public boolean isAlive() {
        if(currentState ==ALIVE){
            alive = true;
        }else{
            alive = false;
        }
        return alive;
    }
}
