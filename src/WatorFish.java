import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WatorFish extends WatorCell {

    /**
     * Create a new Fish. Increment the total number of fish.
     */
    public WatorFish(Location loc, Grid grid, Map<String, Double> parameters){
        super(loc, grid, parameters);
    }

    /**
     * Act to move if possible and then breed if time.
     * Fish act by first checking to see if they can
     * move. If they can they move to a random location.
     * Then they check to see if it is time to breed.
     * If so then they create an offspring in the
     * location they just left.
     */
    @Override
    public void step(){
        turnsUntilCanBreed--;
        ArrayList<Location> openSpots = myGrid.getEmptyAdjacentLocations(myLocation);
        if(openSpots.size() > 0){
            Location oldLocation = myLocation;
            Collections.shuffle(openSpots);
            moveTo(openSpots.get(0));
            if(turnsUntilCanBreed <= 0){
                WatorFish newFish = new WatorFish(oldLocation, myGrid, myParameters);
                myGrid.put(newFish.getMyLocation(), newFish);
                turnsUntilCanBreed = (int) (myParameters.get("breedTime") + 0.0);
            }
        }
        myLocation = openSpots.get(0);
    }

    @Override
    public void calculateNewState() {
        super.calculateNewState();
    }

    /**
     * Return the color to display representing the state of the cell
     * @return a Color representing the state of the cell
     */
    @Override
    public Color getMyColor() {
        return Color.ORANGE;
    }

    @Override
    public boolean isEdible() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Return a character representation of the cell state
     * @return a String of length 1 representing the state of the cell
     */
    @Override
    public String toString() {
        return "F";
    }
}
