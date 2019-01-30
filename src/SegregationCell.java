import java.awt.*;
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
    public void updateState(){
        if(myNextState==TOBEMOVED){
            System.out.println("moving from " + myLocation.getRow()+" " + myLocation.getCol()+" to New location:"+myNextLocation.getRow() + " " + myNextLocation.getCol());
            swapLocations();
            myNextState = myCurrentState;
        }
        myCurrentState = myNextState;
        myNextState = 0;
    }

    @Override
    public void calculateNewState() {
        if(isSatisfied()&&myCurrentState!=EMPTY){ myNextState=myCurrentState; }
        else if(myCurrentState==EMPTY){ //if empty will remain empty
            myNextState = myCurrentState;
        } else if(!isSatisfied()&&myCurrentState!=EMPTY){ //if a move is required
            myNextState = TOBEMOVED;
            myNextLocation = chooseNewLocation(findLocationsToMoveTo(myLocation));
        }
    }

    private Location chooseNewLocation(List<Location> availableLocations){
        Collections.shuffle(availableLocations);
        if (availableLocations.size()>0) return availableLocations.get(1);
        return myLocation;
    }

    private List<Location> findLocationsToMoveTo(Location myLocation){
        List<Location> availableLocations = new ArrayList<>();
        for (int i = 0; i <myGrid.getNumRows(); i++){
            for (int j = 0; j < myGrid.getNumCols(); j++) {
                Cell currentCell = myGrid.get(new Location(i,j));
                if(currentCell.getMyCurrentState()==EMPTY){
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
        if(percentSame>=threshold) return true;
        return false;
    }

    private double calcPercentSimilarNeighbors(List<Location> locationList){
        double numSame = 0;
        double numOther = 0;
        for(Location l:locationList){
            SegregationCell tempCell =(SegregationCell)myGrid.get(l);
            if(tempCell.getMyCurrentState()==myCurrentState){ numSame++; }
            else if (tempCell.getMyCurrentState()!=myCurrentState&&tempCell.getMyCurrentState()!=EMPTY){ numOther++; }
        }
        return numSame/(numSame+numOther);
    }

    @Override
    public String toString() {
        if (getMyCurrentState() == EMPTY){
            return "E";
        }
        else if (getMyCurrentState() == TYPEA){
            return "A";
        }
        else if (getMyCurrentState() == TYPEB){
            return "B";
        } else if(getMyCurrentState()==TOBEMOVED){
            return "TBM";
        }
        else return "?";
    }

    @Override
    public Color getMyColor() {
        if (getMyCurrentState() == TYPEA){
            return Color.BLUE;
        }
        else if (getMyCurrentState() == TYPEB) {
            return Color.RED;
        }
        else if (getMyCurrentState() == EMPTY){
            return Color.WHITE;
        }
        return Color.GREEN;
    }


}
