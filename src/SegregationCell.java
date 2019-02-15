import java.util.*;

public class SegregationCell extends MovableCell{

    private static int[] SEGREGATION_CELL_ROW_NEIGHBORS = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static int[] SEGREGATION_CELL_COL_NEIGHBORS = {-1, -1, -1, 0, 0, 1, 1, 1};
    private boolean toBeMoved = false;

    /**
     * Segregation cell class constructor
     * @param location position in the grid
     * @param initialState
     * @param currentGrid the grid in which the cell exists
     * @param nextGrid the grid in which the cell's next state will be placed
     * @param parameters the parameters that determine rules for the simulation (threshold for satisfaction)
     */
    public SegregationCell(Location location, SegregationState initialState, Grid currentGrid, Grid nextGrid, Map<String,
                Double> parameters){
        super(location, initialState, currentGrid, nextGrid, parameters);
//        myNeighbors = NeighborsDefinitions.BOX_NEIGHBORS;
    }

    @Deprecated
    public SegregationCell(Location location, int initialState, Grid grid, Grid nextGrid, HashMap<String,
            Double> parameters){
        this(location, SegregationState.values()[initialState], grid, nextGrid, parameters);
    }

    /**
     * updates the cell's state, used when the next iteration of the simulation is ready to be presented
     * handles swapping cells in the grid when necessary
     */
    @Override
    public void updateState(){
        if(myNextState == SegregationState.TO_BE_MOVED){
            swapLocations();
            myNextState = myCurrentState;
        }
        myCurrentState = myNextState;
        if(myCurrentState==SegregationState.EMPTY){ //reset the boolean flag for empty cells
            toBeMoved=false;
        }
    }

    /**
     * calculates what the state of the cell should be on the next iteration of the simulation
     */
    @Override
    public void calculateNewState() {
        if(isSatisfied() && myCurrentState != SegregationState.EMPTY){ myNextState=myCurrentState; }
        else if(myCurrentState == SegregationState.EMPTY){ //if empty will remain empty
            myNextState = myCurrentState;
        } else if(!isSatisfied() && myCurrentState != SegregationState.EMPTY){ //if a move is required
            myNextState = SegregationState.TO_BE_MOVED;
            myNextLocation = chooseNewLocation(findLocationsToMoveTo(myLocation));
        }
    }

    private Location chooseNewLocation(List<Location> availableLocations){
        Collections.shuffle(availableLocations);
        System.out.println("open spots:"+availableLocations.size());
        if (availableLocations.size() > 0){
            System.out.println("spots open");
            for(Location l:availableLocations){
                SegregationCell c = (SegregationCell) myGrid.get(l);
                if(!c.toBeMoved){
                    c.updateToBeMovedFlag();
                    System.out.println("assigned new location");
                    return l;
                }
            }
        }
        return myLocation;
    }

    private List<Location> findLocationsToMoveTo(Location myLocation){
        //TODO: Maybe part of this functionality could be moved to findEmptyCells method in Grid->BasicGrid? We
        // already have a getOccupiedCells method and having those two ideas in the same class makes sense in Ryan's
        // mind, alternatively, finding open cells could be a MoveableCell method because only cells that have to
        // move have to do this.  Also does moving randomly and moving only adjacently matter for how we implement
        // moveable cells? Wator vs Segregation
        List<Location> availableLocations = new ArrayList<>();
        for (int i = 0; i <myGrid.getNumRows(); i++){
            for (int j = 0; j < myGrid.getNumCols(); j++) {
                SegregationCell currentCell = (SegregationCell)myGrid.get(new Location(i,j));
                if(currentCell.myCurrentState == SegregationState.EMPTY && ! currentCell.toBeMoved){
                    availableLocations.add(currentCell.getMyLocation());
                }
            }
        }
        return availableLocations;
    }

    private void updateToBeMovedFlag(){
        toBeMoved = true;
    }

    /**
     * Used to determine if a cell needs to moved
     * @return true if the cell will remain where it is, false if not
     */
    public boolean isSatisfied(){
        List<Location> myNeighborLocations = getMyGrid().getValidNeighbors(myLocation, myNeighbors);
        double percentSame = calcPercentSimilarNeighbors(myNeighborLocations);
        return percentSame >= myParameters.get(SegregationSimulation.THRESHOLD);
    }

    private double calcPercentSimilarNeighbors(List<Location> locationList){
        double numSame = 0;
        double numOther = 0;
        for(Location l:locationList){
            SegregationCell tempCell =(SegregationCell)myGrid.get(l);
            if(tempCell.getCurrentCellState() == myCurrentState){ numSame++; }
            else if (tempCell.getCurrentCellState() != myCurrentState && tempCell.getCurrentCellState() != SegregationState.EMPTY){
                numOther++;
            }
        }
        return numSame / (numSame + numOther);
    }

}
