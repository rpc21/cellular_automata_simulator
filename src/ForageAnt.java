import java.util.*;

public class ForageAnt {

    private Location myLocation;
    private NeighborsDefinitions myDirection;
    private Grid myGrid;
    private Grid myNextGrid;
    private boolean hasFoodItem;

    public ForageAnt(Location myLocation, NeighborsDefinitions myDirection, Grid myGrid, Grid myNextGrid) {
        this.myLocation = myLocation;
        this.myDirection = myDirection;
        this.myGrid = myGrid;
        this.myNextGrid = myNextGrid;
    }

    public void act(){
        if (hasFoodItem){
            returnToNest();
        }
        else{
            findFoodSource();
        }

    }

    private List<Location> calculateNeighborLocations() {
        return myGrid.getValidNeighbors(myLocation, NeighborsDefinitions.BOX_NEIGHBORS);
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

            if (myGrid.isValid(neighborLocation) && ((ForagePatch) myGrid.get(neighborLocation)).isValidAntLocation()) {
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
        if (((ForagePatch) myGrid.get(myLocation)).isTheNest()){
            ((ForagePatch) myGrid.get(myLocation)).topOffPheromones(ForageState.NEST);
        }
        else {
//            Double max = Collections.max(getNeighborHomePheromones(myLocation));
//            double desired = max - 2.0;
//            double d = desired - ((ForagePatch) myGrid.get(myLocation)).getMyHomePheromones();
//            if (d > 0){
            ((ForagePatch) myNextGrid.get(myLocation)).addHomePheromones(1.0D);
//            }
        }
    }

    private void dropFoodPheromones() {
        if (((ForagePatch) myNextGrid.get(myLocation)).hasFoodSource()){
            ((ForagePatch) myNextGrid.get(myLocation)).topOffPheromones(ForageState.FOOD);
        }
        else {
//            Double max = Collections.max(getNeighborFoodPheromones(myLocation));
//            double desired = max - 2.0;
//            double d = desired - ((ForagePatch) myNextGrid.get(myLocation)).getMyFoodPheromones();
//            if (d > 0){
                ((ForagePatch) myNextGrid.get(myLocation)).addFoodPheromones(1.0D);
//            }
        }
    }

    private Location selectLocationOnFood(List<Location> neighborLocations){
        Comparator<Location> locationComparator = new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                return (int) (((ForagePatch) myGrid.get(o2)).getMyFoodPheromones() - ((ForagePatch) myGrid.get(o1)).getMyFoodPheromones());
            }
        };
        return getNextLocation(neighborLocations, locationComparator);
    }

    private Location selectLocationOnHome(List<Location> neighborLocations){
        Comparator<Location> locationComparator = new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                return (int) (((ForagePatch) myGrid.get(o2)).getMyHomePheromones() - ((ForagePatch) myGrid.get(o1)).getMyHomePheromones());
            }
        };
        return getNextLocation(neighborLocations, locationComparator);
    }

    private Location getNextLocation(List<Location> neighborLocations, Comparator<Location> locationComparator) {
        Collections.shuffle(neighborLocations);
        PriorityQueue<Location> possibleLocations = new PriorityQueue<>(locationComparator);
        possibleLocations.addAll(getPossibleMoves(neighborLocations));
        if (possibleLocations.isEmpty()) {
            return null;
        } else {
            return implementRandomChoice(possibleLocations);
        }
    }

    private Location implementRandomChoice(PriorityQueue<Location> possibleLocations) {
        double randomNumber = Math.random();
        if (possibleLocations.size() >= 3 && randomNumber <= 0.20D){
            possibleLocations.poll();
            possibleLocations.poll();
            return  possibleLocations.poll();
        }
        else if (possibleLocations.size() >= 2 && randomNumber <= 0.5){
            possibleLocations.poll();
            return possibleLocations.poll();
        }
        else{
            return possibleLocations.poll();
        }
    }

    private void findFoodSource() {
        Location myNextLocation = null;
        if (((ForagePatch) myGrid.get(myLocation)).isTheNest()) {
            myDirection = getDirectionWithMaxFoodPheromones(calculateForwardDirections());
        }
        myNextLocation = selectLocationOnFood(calculateNeighborLocations());
        if (myNextLocation == null){
            myNextLocation = selectLocationOnFood(getPossibleMoves(calculateNeighborLocations()));
        }
        if (myNextLocation != null){
            dropHomePheromones();
            move(myNextLocation);
            if (((ForagePatch) myGrid.get(myLocation)).hasFoodSource()){
                hasFoodItem = true;
            }
        }
    }

    private void returnToNest() {
        Location myNextLocation;
        if (((ForagePatch) myGrid.get(myLocation)).hasFoodSource()) {
            myDirection = getDirectionWithMaxHomePheromones(calculateForwardDirections());
        }
        myNextLocation = selectLocationOnHome(calculateNeighborLocations());
        if (myNextLocation == null){
            myNextLocation = selectLocationOnHome(getPossibleMoves(calculateNeighborLocations()));
        }
        if (myNextLocation != null){
            dropFoodPheromones();
            move(myNextLocation);
            if (((ForagePatch) myGrid.get(myLocation)).isTheNest()){
                ((ForagePatch) myGrid.get(myLocation)).addFoodItem();
                hasFoodItem = false;
            }
        }
    }

    private void move(Location myNextLocation) {
        if (myGrid.isValid(myNextLocation) && ((ForagePatch) myNextGrid.get(myNextLocation)).isValidPlaceToMove()){
            ((ForagePatch) myNextGrid.get(myNextLocation)).addAntToPatch(this);
            myLocation = myNextLocation;
        }
        else{
            ((ForagePatch) myNextGrid.get(myLocation)).addAntToPatch(this);
        }
    }


    private Location calculateNextLocation(Location myLocation, NeighborsDefinitions myDirection) {
        return  new Location(myLocation.getRow() + myDirection.getDeltaRow()[0],
                myLocation.getCol() + myDirection.getDeltaCol()[0]);
    }


    private NeighborsDefinitions getDirectionWithMaxHomePheromones(List<NeighborsDefinitions> directions) {
        HashMap<NeighborsDefinitions, Double> neighborToPheromoneMap = new HashMap<>();
        for (NeighborsDefinitions direction : directions){
            Location neighborLocation = new Location(myLocation.getRow() + direction.getDeltaRow()[0],
                    myLocation.getCol()+direction.getDeltaCol()[0]);
            if (myGrid.isValid(neighborLocation) && ((ForagePatch) myGrid.get(neighborLocation)).isValidAntLocation()) {
                neighborToPheromoneMap.put(direction,
                        ((ForagePatch) myGrid.get(neighborLocation)).getMyHomePheromones());
            }
        }
        if (neighborToPheromoneMap.isEmpty()){
            return myDirection;
        }
        else {
            return Collections.max(neighborToPheromoneMap.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
        }
    }

    private List<Location> getPossibleMoves(List<Location> neighborLocations) {
        ArrayList<Location> possibleLocations = new ArrayList<>();
        for (Location loc : neighborLocations){
            if (myGrid.isValid(loc) && ((ForagePatch) myGrid.get(loc)).isValidAntLocation()){
                possibleLocations.add(loc);
            }
        }
        return possibleLocations;
    }

    private List<Double> getNeighborHomePheromones(Location location){
        List<Double> neighborHomePheromones = new ArrayList<>();
        for (Location loc : myGrid.getValidNeighbors(location, NeighborsDefinitions.BOX_NEIGHBORS)){
            neighborHomePheromones.add(((ForagePatch) myGrid.get(loc)).getMyHomePheromones());
        }
        return neighborHomePheromones;
    }

    private List<Double> getNeighborFoodPheromones(Location location){
        List<Double> neighborFoodPheromones = new ArrayList<>();
        for (Location loc : myGrid.getValidNeighbors(location, NeighborsDefinitions.BOX_NEIGHBORS)){
            neighborFoodPheromones.add(((ForagePatch) myGrid.get(loc)).getMyFoodPheromones());
        }
        return neighborFoodPheromones;
    }

    public void setMyNextGrid(Grid myNextGrid) {
        this.myNextGrid = myNextGrid;
    }
}
