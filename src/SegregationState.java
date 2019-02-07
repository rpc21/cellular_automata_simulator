import javafx.scene.paint.Color;

/**
 * SegregationState is an Enum that defines all the possible states for a SegregationCell
 * There are four states: EMPTY, RED, BLUE, and TO_BE_MOVED
 */
public enum SegregationState implements CellState {

    EMPTY("E", "emptyPercentage",Color.WHITE),
    RED("A", "redPercentage", Color.RED),
    BLUE("B", "bluePercentage", Color.BLUE),
    TO_BE_MOVED("TBM", "TO_BE_MOVED", Color.ORANGE);

    private final String myShortenedName;
    private final String myParamName;
    private final Color myCellColor;

    SegregationState(String shortenedName, String ParamName, Color cellColor){
        myShortenedName = shortenedName;
        myParamName = ParamName;
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

    @Override
    public String getMyParamName() {
        return myParamName;
    }
}
