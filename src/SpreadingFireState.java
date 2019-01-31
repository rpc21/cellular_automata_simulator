import java.awt.*;

public enum SpreadingFireState implements CellState {

    TREE("T", "TREE", Color.GREEN){},
    EMPTY("E", "EMPTY", Color.WHITE){},
    FIRE("F", "FIRE", Color.RED){};

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    SpreadingFireState(String shortenedName, String fullState, Color cellColor){
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
