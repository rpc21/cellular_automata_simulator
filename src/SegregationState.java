import java.awt.*;

public enum SegregationState implements CellState {

    EMPTY("E", "EMPTY",Color.WHITE){},
    TYPE_A("A", "TYPE_A", Color.RED){},
    TYPE_B("B", "TYPE_B", Color.BLUE){};

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    SegregationState(String shortenedName, String fullState, Color cellColor){
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
