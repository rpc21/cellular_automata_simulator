import javafx.scene.paint.Color;

/**
 * PercolationState is an Enum that defines all the possible states for a PercolationCell
 * There are three states: PERCOLATED, OPEN and BLOCKED
 */
public enum PercolationState implements CellState{

    PERCOLATED("P", "PERCOLATED", Color.CYAN){},
    OPEN("O", "OPEN", Color.WHITE){},
    BLOCKED("B", "BLOCKED", Color.BLACK){};

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    PercolationState(String shortenedName, String fullState, Color cellColor){
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
