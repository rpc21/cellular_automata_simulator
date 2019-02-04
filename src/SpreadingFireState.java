import javafx.scene.paint.Color;

/**
 * SpreadingFireState is an Enum that defines all the possible states for a SpreadingFireCell
 * There are three states: FIRE, TREE, and EMPTY
 */
public enum SpreadingFireState implements CellState {

    FIRE("F", "FIRE", Color.RED),
    TREE("T", "TREE", Color.GREEN),
    EMPTY("E", "EMPTY", Color.WHITE);


    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    SpreadingFireState(String shortenedName, String fullState, Color cellColor){
        myShortenedName = shortenedName;
        myFullState = fullState;
        myCellColor = cellColor;
    }

    /**
     * Return a character representation of the cell state
     * @return a String of length 1 representing the state of the cell
     */
    @Override
    public String toString() {
        return myShortenedName;
    }

    /**
     * Return a character representation of the cell state
     * @return a String of length 1 representing the state of the cell
     */
    @Override
    public String getMyShortenedName() {
        return myShortenedName;
    }

    /**
     * Return the color to display representing the state of the cell
     * @return a Color representing the state of the cell
     */
    @Override
    public Color getMyCellColor() {
        return myCellColor;
    }
}
