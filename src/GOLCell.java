import java.util.List;

public class GOLCell extends Cell{

    private static final int ALIVE = 1200;
    private static final int DEAD = 1201;
    private boolean alive;
    private List<Location> myNeighborLocations;
    private Grid myGrid;
    /**
     * Implement Game of Life rules
     * Box neighbors, <2 neighbors alive = DIE, 2 0r 3 live will live, more than 3 live neighbors = die
     * dead with =3 live neighbors = ALIVE
     * Need to know my state and how many ALIVE neighbors
     * @return the new state
     */

    public GOLCell(Location location, int initialState, Grid grid){
        setMyCurrentState(initialState);
        setMyNextState(0);
        setMyLocation(location);
        alive = isAlive();
        setMyGrid(grid);
    }

    @Override
    public void calculateNewState() {
        myNeighborLocations = getMyGrid().getValidBoxLocations(getMyLocation());
        int numAlive = calcNumLiveNeighbors(myNeighborLocations);
        if(needsToDie(numAlive)){
            setMyNextState(DEAD);
        }else if(needsToLive(numAlive)){
            setMyNextState(ALIVE);
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
            GOLCell tempCell =(GOLCell)getMyGrid().get(l);
            if(tempCell.isAlive()){ numAlive++; }
        }
        return numAlive;
    }

    public boolean isAlive() {
        if(getMyCurrentState() ==ALIVE){
            alive = true;
        }else{
            alive = false;
        }
        return alive;
    }

    @Override
    public String toString() {
        if (getMyCurrentState() == ALIVE) {
            return "A";
        }
        return  "D";
    }
}
