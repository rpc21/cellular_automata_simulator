import javafx.scene.paint.Color;

public enum WatorState implements CellState {

    EMPTY("E", "emptyPercentage", Color.AQUAMARINE),
    FISH("F", "fishPercentage", Color.ORANGE),
    SHARK("S", "sharkPercentage", Color.LIGHTGREY);

    private final String myShortenedName;
    private final String myParamName;
    private final Color myCellColor;

   WatorState(String shortenedName, String ParamName, Color cellColor){
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
