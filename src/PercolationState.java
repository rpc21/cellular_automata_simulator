import javafx.scene.paint.Color;

/**
 * PercolationState is an Enum that defines all the possible states for a PercolationCell
 * There are three states: PERCOLATED, OPEN and BLOCKED
 */
public enum PercolationState implements CellState{

    PERCOLATED("P", "PERCOLATED", Color.CYAN),
    OPEN("O", "OPEN", Color.WHITE),
    CLOSED("C", "CLOSED", Color.BLACK);

    private final String myShortenedName;
    private final String myParamName;
    private final Color myCellColor;

    PercolationState(String shortenedName, String ParamName, Color cellColor){
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
