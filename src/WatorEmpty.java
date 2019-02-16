import javafx.scene.paint.Color;


/**
 * The WatorEmpty has a location in the grid, is white when displayed in the visualization and is able to communicate
 * that it is empty, making it a viable location for other cells to move.  The WatorEmpty has unchanging state and no
 * real state representation
 */
public class WatorEmpty extends WatorCell {

    /**
     * Empty cells do not advance state, they just remain the same
     */
    @Override
    public void step() {}

    /**
     * Empty cell constructor
     * @param loc
     */
    public WatorEmpty(Location loc){
        myLocation = loc;
        myCurrentState = WatorState.EMPTY;
    }

    /**
     * Empty cells are empty
     * @return true
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * Empty cells do not advance state
     */
    @Override
    public void calculateNewState() {
        myNextState = null;
    }

    /**
     *
     * @return string representation of empty cell
     */
    @Override
    public String toString() {
        return "O";
    }
}
