import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SegregationCell extends MovableCell{
    private static final int TYPEA = 13700;
    private static final int TYPEB = 13701;
    private static final int EMPTY = 13702;
    private static final int TOBEMOVED = 13703;
    private static int[] SEGREGATION_CELL_ROW_NEIGHBORS = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static int[] SEGREGATION_CELL_COL_NEIGHBORS = {-1, -1, -1, 0,0, 1, 1,1};

    private Location myNextLocation;
    private double threshold;

    public SegregationCell(Location location, int initialState, Grid grid, HashMap<String,
                Double> parameters){
        setMyCurrentState(initialState);
        setMyNextState(0);
        setMyLocation(location);
        threshold = parameters.get("threshold");
        setMyGrid(grid);
        myParameters = parameters;
    }

    @Override
    public void calculateNewState() {
        if(isSatisfied()&&myCurrentState!=EMPTY){ myNextState=myCurrentState; }
        else if(myCurrentState==EMPTY&&myNextState!=TOBEMOVED){ //if empty will remain empty
            myNextState = myCurrentState;
        } else if(!isSatisfied()&&myCurrentState!=EMPTY){ //if a move is required
            myNextState = TOBEMOVED; ///ADDCODE HERE
            myNextLocation = chooseNewLocation(findLocationsToMoveTo(myLocation));
            myGrid.get(myNextLocation).myNextState = TOBEMOVED;
        }
    }

    private Location chooseNewLocation(List<Location> availableLocations){
        Collections.shuffle(availableLocations);
        return availableLocations.get(0);
    }

    private List<Location> findLocationsToMoveTo(Location myLocation){
        List<Location> availableLocations = new ArrayList<>();
        for (int i = 0; i <myGrid.getNumRows(); i++){
            for (int j = 0; j < myGrid.getNumCols(); j++) {
                Cell currentCell = myGrid.get(new Location(i,j));
                if(currentCell.getMyCurrentState()==EMPTY&&currentCell.getMyNextState()!=TYPEA &&currentCell.getMyCurrentState()!=TYPEB){
                    availableLocations.add(currentCell.getMyLocation());
                }
            }
        }
        return availableLocations;
    }

    private boolean isSatisfied(){
        List<Location> myNeighborLocations = getMyGrid().getLocations(getMyLocation(), SEGREGATION_CELL_ROW_NEIGHBORS
                , SEGREGATION_CELL_COL_NEIGHBORS);
        double percentSame = calcPercentSimilarNeighbors(myNeighborLocations);
        if(percentSame>=threshold) return true;
        return false;
    }

    private double calcPercentSimilarNeighbors(List<Location> locationList){
        int numSame = 0;
        int numOther = 0;
        for(Location l:locationList){
            SegregationCell tempCell =(SegregationCell)getMyGrid().get(l);
            if(tempCell.getMyCurrentState()==this.getMyCurrentState()){ numSame++; }
            else if (tempCell.getMyCurrentState()!=this.getMyCurrentState()&&tempCell.getMyCurrentState()!=EMPTY){ numOther++; }
        }
        return numSame/numOther;
    }

    @Override
    public String toString() {
        if (getMyCurrentState() == EMPTY){
            return "E";
        }
        else if (getMyCurrentState() == TYPEA){
            return "A";
        }
        else {
            return "B";
        }
    }
}
