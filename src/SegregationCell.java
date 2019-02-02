import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SegregationCell extends MovableCell{

    private static int[] SEGREGATION_CELL_ROW_NEIGHBORS = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static int[] SEGREGATION_CELL_COL_NEIGHBORS = {-1, -1, -1, 0, 0, 1, 1, 1};
    private double threshold;
    private boolean toBeMoved = false;

    public SegregationCell(Location location, SegregationState initialState, Grid grid, HashMap<String,
            Double> parameters){
        super(location, initialState, grid, parameters);
        threshold = parameters.get("threshold");
    }

    public SegregationCell(Location location, int initialState, Grid grid, HashMap<String,
            Double> parameters){
        this(location, SegregationState.values()[initialState], grid, parameters);
    }

    @Override
    public void updateState(){
        if(myNextState == SegregationState.TO_BE_MOVED){
            System.out.println("moving from " + myLocation.getRow()+" " + myLocation.getCol()+" to New location:"+myNextLocation.getRow() + " " + myNextLocation.getCol());
            swapLocations();
            myNextState = myCurrentState;
        }
        myCurrentState = myNextState;
        if(myCurrentState==SegregationState.EMPTY){ //reset the boolean flag for empty cells
            toBeMoved=false;
        }
    }

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

    public boolean isSatisfied(){
        List<Location> myNeighborLocations = getMyGrid().getValidNeighbors(myLocation, SEGREGATION_CELL_ROW_NEIGHBORS
                , SEGREGATION_CELL_COL_NEIGHBORS);
        double percentSame = calcPercentSimilarNeighbors(myNeighborLocations);
        return percentSame >= threshold;
    }

    private double calcPercentSimilarNeighbors(List<Location> locationList){
        double numSame = 0;
        double numOther = 0;
        for(Location l:locationList){
            SegregationCell tempCell =(SegregationCell)myGrid.get(l);
            if(tempCell.getMyCurrentState() == myCurrentState){ numSame++; }
            else if (tempCell.getMyCurrentState() != myCurrentState && tempCell.getMyCurrentState() != SegregationState.EMPTY){
                numOther++;
            }
        }
        return numSame / (numSame + numOther);
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
