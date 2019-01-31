import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SegregationCell extends MovableCell{

    private static int[] SEGREGATION_CELL_ROW_NEIGHBORS = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static int[] SEGREGATION_CELL_COL_NEIGHBORS = {-1, -1, -1, 0, 0, 1, 1, 1};
    private double threshold;

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
        myNextState = null;
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
        if (availableLocations.size() > 0) return availableLocations.get(0);
        return myLocation;
    }

    private List<Location> findLocationsToMoveTo(Location myLocation){
        List<Location> availableLocations = new ArrayList<>();
        for (int i = 0; i <myGrid.getNumRows(); i++){
            for (int j = 0; j < myGrid.getNumCols(); j++) {
                Cell currentCell = myGrid.get(new Location(i,j));
                if(myCurrentState == SegregationState.EMPTY){
                    availableLocations.add(currentCell.getMyLocation());
                }
            }
        }
        return availableLocations;
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
