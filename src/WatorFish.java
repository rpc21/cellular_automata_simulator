import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//TODO: Attribte sources
public class WatorFish extends WatorCell {

    /**
     * Constructor for a WatorFish specifies the location, the grid and the parameters
     * @param loc location of the cell
     * @param grid grid of WatorCells in which this Fish is held
     * @param parameters parameters for the fish
     */
    public WatorFish(Location loc, Grid grid, Map<String, Double> parameters){
        super(loc, grid, parameters);
        myCurrentState = WatorState.FISH;
    }

    /**
     * Act to move if possible and then breed if time.
     * Fish act by first checking to see if they can
     * move. If they can they move to an adjacent location.
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
        return myCurrentState.getMyCellColor();
    }

    /**
     * WatorFish are edible
     * @return true
     */
    @Override
    public boolean isEdible() {
        return true;
    }

    /**
     * Since there is a fish, the cell is not empty
     * @return false
     */
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
        return myCurrentState.getMyShortenedName();
    }
}
