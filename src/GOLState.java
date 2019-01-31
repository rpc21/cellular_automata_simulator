import java.awt.*;

public enum GOLState implements CellState{
    DEAD("D", "DEAD", Color.DARK_GRAY){},
    ALIVE("A", "ALIVE", Color.GREEN){};

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    GOLState(String shortenedName, String fullState, Color cellColor){
        myShortenedName = shortenedName;
        myFullState = fullState;
        myCellColor = cellColor;
    }

    @Override
    public String toString() {
        return myShortenedName;
    }

    @Override
    public String getMyShortenedName() {
        return myShortenedName;
    }

    @Override
    public String getMyFullState() {
        return myFullState;
    }

    @Override
    public Color getMyCellColor() {
        return myCellColor;
    }
}
