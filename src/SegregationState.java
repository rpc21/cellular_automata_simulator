import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * SegregationState is an Enum that defines all the possible states for a SegregationCell
 * There are four states: EMPTY, RED, BLUE, and TO_BE_MOVED
 */
public enum SegregationState implements CellState {

    //EMPTY("O", "emptyPercentage",Color.WHITE),
    //RED("A", "redPercentage", Color.RED),
    //BLUE("B", "bluePercentage", Color.BLUE),
    EMPTY("E", "EMPTY",Color.WHITE),
    RED("R", "RED", Color.RED),
    BLUE("B", "BLUE", Color.BLUE),
    TO_BE_MOVED("TBM", "TO_BE_MOVED", Color.ORANGE);

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    SegregationState(String shortenedName, String ParamName, Color cellColor){
        myShortenedName = shortenedName;
        myFullState = ParamName;
        myCellColor = cellColor;
    }

    /**
     * Return a character representation of the cell state
     * @return a String of length 1 representing the state of the cell
     */
    @Override
    public String toString() {
        return myFullState;
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
    public List<String> getPossibleValues() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i< values().length; i++){
            if (!values()[i].myFullState.equals(SegregationState.TO_BE_MOVED.toString()))
                arrayList.add(values()[i].myFullState);
        }
        return arrayList;
    }
}
