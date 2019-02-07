import javafx.scene.paint.Color;

/**
 * GOLState is an Enum that defines all the possible states for a GOLCell
 * There are two states: DEAD and ALIVE
 */
public enum GOLState implements CellState{
    DEAD("D", "deadPercentage", Color.DARKGRAY),
    ALIVE("A", "alivePercentage", Color.GREEN);

    private final String myShortenedName;
    private final String myParamName;
    private final Color myCellColor;

    GOLState(String shortenedName, String paramName, Color cellColor){
        myShortenedName = shortenedName;
        myParamName = paramName;
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
