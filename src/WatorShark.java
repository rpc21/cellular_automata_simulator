import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class WatorShark extends WatorCell {

    private int turnsSinceLastAte;
    private int turnsUntilCanBreed;
    private int starveTime;

    public WatorShark(Location loc, Grid grid, Map<String, Double> parameters){
        super(loc, grid, parameters);
        myCurrentState = WatorState.SHARK;
        turnsSinceLastAte = 0;
        starveTime = (int) (parameters.get(WatorSimulation.STARVE_TIME) + 0.0);
        turnsUntilCanBreed = (int) (parameters.get(WatorSimulation.SHARK_BREED_TIME) + 0.0);
    }

    @Override
    public void step() {
        turnsUntilCanBreed--;
        turnsSinceLastAte++;

        if (turnsSinceLastAte >= starveTime){
            myGrid.remove(myLocation);
            return;
        }
        boolean ate = tryToEat();
        if (!ate){
            tryToMove();
        }
        tryToBreed();

    }

    private void tryToBreed() {
        if(turnsUntilCanBreed <= 0){
            ArrayList<Location> openSpots = myGrid.getEmptyAdjacentLocations(myLocation);
            if(openSpots.size() > 0){
                Collections.shuffle(openSpots);
                Location offspringLocation = openSpots.get(0);
                WatorShark newShark = new WatorShark(offspringLocation, myGrid, myParameters);
                myGrid.put(newShark.getMyLocation(), newShark);
                turnsUntilCanBreed = (int) Math.floor(myParameters.get(WatorSimulation.SHARK_BREED_TIME));
            }
        }
    }

    private void tryToMove() {
        ArrayList<Location> emptyLocations = myGrid.getEmptyAdjacentLocations(myLocation);
        if (emptyLocations.size() > 0){
            Collections.shuffle(emptyLocations);
            Location newLocation = emptyLocations.get(0);
            myGrid.put(newLocation, this);
            myGrid.remove(myLocation);
            myLocation = newLocation;
        }
    }

    private boolean tryToEat() {
        ArrayList<WatorCell> surroundingPrey = new ArrayList<>();
        for (Location neighbor: myGrid.getOccupiedAdjacentLocations(myLocation)){
            if (((WatorCell) myGrid.get(neighbor)).isEdible()){
                surroundingPrey.add((WatorCell) myGrid.get(neighbor));
            }
        }
        if (surroundingPrey.size() > 0){
            Collections.shuffle(surroundingPrey);
            Location newLocation = surroundingPrey.get(0).getMyLocation();
            myGrid.put(newLocation, this);
            myGrid.remove(myLocation);
            myLocation = newLocation;
            turnsSinceLastAte = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEdible() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
