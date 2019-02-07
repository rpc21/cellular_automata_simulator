import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ForageAnt {

    private Location myLocation;
    private NeighborsDefinitions myDirection;
    private AntGrid myGrid;
    private boolean hasFoodItem;


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
        if (myGrid.get(myLocation).isTheNest()) {
            myDirection = getDirectionWithMaxFoodPheromones(calculateForwardDirections());
        }
        myNextLocation = selectLocation(calculateForwardLocations());
        if (myNextLocation == null){
            myNextLocation = selectLocation(calculateNeighborLocations());
        }
        if (myNextLocation != null){
            dropHomePheromones();
            move(myNextLocation);
            if (myGrid.get(myLocation).hasFoodSource()){
                pickUpFoodItem();
                hasFoodItem = true;
            }
        }
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
        if (myGrid.get(myLocation).hasFoodSource()) {
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
