import javafx.scene.paint.Color;

public enum WatorState implements CellState {

    EMPTY("E", "EMPTY", Color.AQUAMARINE),
    FISH("F", "FISH", Color.ORANGE),
    SHARK("S", "SHARK", Color.LIGHTGREY);

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

   WatorState(String shortenedName, String fullState, Color cellColor){
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
