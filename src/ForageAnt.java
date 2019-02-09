import java.util.*;

public class ForageAnt {

    private Location myLocation;
    private NeighborsDefinitions myDirection;
    private AntGrid myGrid;
    private boolean hasFoodItem;

    public ForageAnt(Location myLocation, NeighborsDefinitions myDirection, AntGrid myGrid) {
        this.myLocation = myLocation;
        this.myDirection = myDirection;
        this.myGrid = myGrid;
    }

    public void act(){
        if (hasFoodItem){
            returnToNest();
        }
        else{
            findFoodSource();
        }

    }

    private void findFoodSource() {
        Location myNextLocation = null;
        if (((ForagePatch) myGrid.get(myLocation)).isTheNest()) {
            myDirection = getDirectionWithMaxFoodPheromones(calculateForwardDirections());
        }
        myNextLocation = selectLocation(calculateForwardLocations());
        if (myNextLocation == null){
            myNextLocation = selectLocation(myGrid.getPossibleMoves(calculateNeighborLocations()));
        }
        if (myNextLocation != null){
            dropHomePheromones();
            move(myNextLocation);
            if (((ForagePatch) myGrid.get(myLocation)).hasFoodSource()){
                pickUpFoodItem();
                hasFoodItem = true;
            }
        }
    }

    private List<Location> calculateForwardLocations() {
        ArrayList<Location> forwardLocations = new ArrayList<>();
        for (NeighborsDefinitions neighborsDefinitions : calculateForwardDirections()){
            Location neighborLocation = new Location(myLocation.getRow() + neighborsDefinitions.getDeltaRow()[0],
                    myLocation.getCol() + neighborsDefinitions.getDeltaCol()[0]);
            forwardLocations.add(neighborLocation);
        }
        return forwardLocations;
    }

    private NeighborsDefinitions getDirectionWithMaxFoodPheromones(List<NeighborsDefinitions> directions) {
        HashMap<NeighborsDefinitions, Double> neighborToPheromoneMap = new HashMap<>();
        for (NeighborsDefinitions direction : directions){
            Location neighborLocation = new Location(myLocation.getRow() + direction.getDeltaRow()[0],
                    myLocation.getCol()+direction.getDeltaCol()[0]);
            if (((ForagePatch) myGrid.get(neighborLocation)).isValidAntLocation()) {
                neighborToPheromoneMap.put(direction, ((ForagePatch) myGrid.get(neighborLocation)).getMyFoodPheromones());
            }
        }
        if (neighborToPheromoneMap.isEmpty()){
            return myDirection;
        }
        else {
            return Collections.max(neighborToPheromoneMap.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
        }
    }



    private List<NeighborsDefinitions> calculateForwardDirections() {
        ArrayList<NeighborsDefinitions> forwardDirections = new ArrayList<>();
        NeighborsDefinitions[] directions = NeighborsDefinitions.CARDINAL_DIRECTIONS_COMPLETE;
        for (int i = 0; i < directions.length; i++){
            if (directions[i] == myDirection){
                forwardDirections.add(directions[(i-1+directions.length) % directions.length]);
                forwardDirections.add(directions[i]);
                forwardDirections.add(directions[(i+1+directions.length) % directions.length]);
            }
        }
        return forwardDirections;
    }

    private void dropHomePheromones() {
        if (myGrid.get(myLocation).isTheNest()){
            myGrid.get(myLocation).topOffPheromones();
        }
        else {
            Double max = Collections.max(myGrid.getNeighborHomePheromones(myLocation));
            double desired = max - 2.0;
            double d = desired - myGrid.get(myLocation).getHomePheromones();
            if (d > 0){
                myGrid.get(myLocation).addHomePheromones(d);
            }
        }
    }

    private void dropFoodPheromones() {
        if (myGrid.get(myLocation).hasFoodSource()){
            myGrid.get(myLocation).topOffPheromones();
        }
        else {
            Double max = Collections.max(myGrid.getNeighborFoodPheromones(myLocation));
            double desired = max - 2.0;
            double d = desired - myGrid.get(myLocation).getFoodPheromones();
            if (d > 0){
                myGrid.get(myLocation).addFoodPheromones(d);
            }
        }
    }

    private Location selectLocation(List<Location> neighborLocations){
        List<Location> possibleLocations = myGrid.getPossibleMoves(neighborLocations);
        if (possibleLocations.isEmpty()){
            return null;
        }
        else{
            Collections.shuffle(possibleLocations);
            return possibleLocations.get(0);
        }
    }


    private void returnToNest() {
        NeighborsDefinitions myNextDirection = null;
        if (((ForagePatch) myGrid.get(myLocation)).hasFoodSource()) {
            myNextDirection = getDirectionWithMaxHomePheromones(calculateForwardDirections());
        }
        if (myNextDirection == null){
            myNextDirection = getDirectionWithMaxHomePheromones();
        }
        if (myNextDirection != null){
            myDirection = myNextDirection;
            dropFoodPheromones();
            Location myNextLocation = calculateNextLocation(myLocation, myDirection);
            move(myNextLocation);
            if (myGrid.get(myLocation).isTheNest()){
                dropFoodItem();
                hasFoodItem = false;
            }
        }
    }
}
