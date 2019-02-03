import javafx.scene.paint.Color;

//TODO: review if this class is actually what we want or if we want a Wator.EMPTY_STATE enum / EMPTY enums for all
// classes that have the possibility of empty cells see To_Do_For_Sunday.  Holding off on comments until resolved bc
// lazy
/**
 * The WatorEmpty has a location in the grid, is white when displayed in the visualization and is able to communicate
 * that it is empty, making it a viable location for other cells to move.  The WatorEmpty has unchanging state and no
 * real state representation
 */
public class WatorEmpty extends WatorCell {

    @Override
    public void step() {}

    public WatorEmpty(Location loc){
        myLocation = loc;
        myCurrentState = WatorState.EMPTY;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void calculateNewState() {
        myNextState = null;
    }

    @Override
    public Color getMyColor() {
        return Color.WHITE;
    }

    @Override
    public String toString() {
        return "E";
    }
}
