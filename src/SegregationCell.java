import java.util.List;

public class SegregationCell extends Cell{
    private static final int TYPEA = 13700;
    private static final int TYPEB = 13701;
    private static final int EMPTY = 13702;
    private double threshold;

    public SegregationCell(Location location, int initialState, Grid grid, double startThreshold){
        setMyCurrentState(initialState);
        setMyNextState(0);
        setMyLocation(location);
        threshold = startThreshold;
        setMyGrid(grid);
    }

    @Override
    public void calculateNewState() {
        if(isSatsified()&&myCurrentState!=EMPTY){ myNextState=myCurrentState; }
        else if(myCurrentState==EMPTY&&myNextState==0){ //if empty will remain empty
            myNextState = myCurrentState;
        }
    }

    public void findVacantCell(){
        for (int i = 0; i <myGrid.getNumRows(); i++){
            for (int j = 0; j < myGrid.getNumCols(); j++) {

            }
        }
    }

    private boolean isSatsified(){
        List<Location> myNeighborLocations = getMyGrid().getValidBoxLocations(getMyLocation());
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
