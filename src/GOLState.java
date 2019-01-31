import java.awt.*;

public enum GOLState {
    DEAD("D", "DEAD", Color.DARK_GRAY){},
    ALIVE("A", "ALIVE", Color.GREEN){};

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    private GOLState(String shortenedName, String fullState, Color cellColor){
        myShortenedName = shortenedName;
        myFullState = fullState;
        myCellColor = cellColor;
    }

    @Override
    public String toString() {
        return myShortenedName;
    }

    public String getMyShortenedName() {
        return myShortenedName;
    }

    public String getMyFullState() {
        return myFullState;
    }

    public Color getMyCellColor() {
        return myCellColor;
    }
}
