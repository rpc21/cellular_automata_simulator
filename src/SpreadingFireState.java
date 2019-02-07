import javafx.scene.paint.Color;

/**
 * SpreadingFireState is an Enum that defines all the possible states for a SpreadingFireCell
 * There are three states: FIRE, TREE, and EMPTY
 */
public enum SpreadingFireState implements CellState {

    FIRE("F", "firePercentage", Color.RED),
    TREE("T", "treePercentage", Color.GREEN),
    EMPTY("E", "emptyPercentage", Color.WHITE);


    private final String myShortenedName;
    private final String myParamName;
    private final Color myCellColor;

    SpreadingFireState(String shortenedName, String ParamName, Color cellColor){
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
