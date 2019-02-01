import javafx.scene.paint.Color;

public enum SpreadingFireState implements CellState {

    FIRE("F", "FIRE", Color.RED){},
    TREE("T", "TREE", Color.GREEN){},
    EMPTY("E", "EMPTY", Color.WHITE){};


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
