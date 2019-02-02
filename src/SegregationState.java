import javafx.scene.paint.Color;

/**
 * SegregationState is an Enum that defines all the possible states for a SegregationCell
 * There are four states: EMPTY, TYPE_A, TYPE_B, and TO_BE_MOVED
 */
public enum SegregationState implements CellState {

    EMPTY("E", "EMPTY",Color.WHITE){},
    TYPE_A("A", "TYPE_A", Color.RED){},
    TYPE_B("B", "TYPE_B", Color.BLUE){},
    TO_BE_MOVED("TBM", "TO_BE_MOVED", Color.ORANGE);

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    SegregationState(String shortenedName, String fullState, Color cellColor){
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

    //TODO: TBH I don't really think we use this: I think we can get rid of it
    @Override
    public String getMyFullState() {
        return myFullState;
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
