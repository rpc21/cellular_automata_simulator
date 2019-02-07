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

        if (myGrid.get(myLocation).isTheNest()) {
            myDirection = getDirectionWithMaxFoodPheromones(calculateForwardDirections());
        }
        myLocation = selectLocation(calculateForwardLocations());
        if (myLocation == null){
            myLocation = selectLocation(calculateNeighborLocations());
        }
        if (myLocation != null){
            dropHomePheromones();
            move()
        }
    }

    private void returnToNest() {
        if (myGrid.get(myLocation).hasFoodSource()) {
            myDirection = getDirectionWithMaxHomePheromones(calculateForwardDirections());
        }
        if (myDirection == null){
            myDirection = getDirectionWithMaxHomePheromones();
        }
        if (myDirection != null){
            dropFoodPheromones();
            move(myLocation, myDirection);
            if (myGrid.get(myLocation).isTheNest()){
                dropFoodItem();
                hasFoodItem = false;
            }
        }
    }
}
