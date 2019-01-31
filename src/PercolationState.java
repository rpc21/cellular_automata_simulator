import java.awt.*;

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
