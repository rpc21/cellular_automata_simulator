import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * WatorFish is loosely based on the Actor class reference in the credits of WatorCell.  WatorFish implements the
 * rules for a fish in the Predator-Prey simulation as specified at the link below:
 * https://en.wikipedia.org/wiki/Wa-Tor
 */
public class WatorFish extends WatorCell {

    /**
     * Constructor for a WatorFish specifies the location, the grid and the parameters
     * @param loc location of the cell
     * @param grid grid of WatorCells in which this Fish is held
     * @param parameters parameters for the fish
     */
    public WatorFish(Location loc, Grid grid, Grid nextGrid, Map<String, Double> parameters){
        super(loc, grid, nextGrid, parameters);
        myCurrentState = WatorState.FISH;
        turnsUntilCanBreed = (int) (parameters.get(WatorSimulation.FISH_BREED_TIME) + 0.0);
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
        List<Location> openSpots = myGrid.getEmptyAdjacentLocations(myLocation);
        if(openSpots.size() > 0){
            Location oldLocation = myLocation;
            Collections.shuffle(openSpots);
            moveTo(openSpots.get(0));
            if(turnsUntilCanBreed <= 0){
                WatorFish newFish = new WatorFish(oldLocation, myGrid, myNextGrid, myParameters);
                myGrid.put(newFish.getMyLocation(), newFish);
                turnsUntilCanBreed = (int) Math.floor(myParameters.get(WatorSimulation.FISH_BREED_TIME));
            }else{
                myGrid.put(myLocation, new EmptyCell(myLocation));
            }
            myLocation = openSpots.get(0);
        }
    }

    /**
     * Fish moves on its turn and does not store next state.  Uses the step function instead
     */
    @Deprecated
    public void calculateNewState() {
        super.calculateNewState();
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

}
