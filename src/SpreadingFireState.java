import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * SpreadingFireState is an Enum that defines all the possible states for a SpreadingFireCell
 * There are three states: FIRE, TREE, and EMPTY
 */
public enum SpreadingFireState implements CellState {

    FIRE("F", "FIRE", Color.RED),
    TREE("T", "TREE", Color.GREEN),
    EMPTY("E", "EMPTY", Color.WHITE);


    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    SpreadingFireState(String shortenedName, String ParamName, Color cellColor){
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
     * Return a list of the possible values that the state could take on
     * @return list of possible states
     */
    @Override
    public List<String> getPossibleValues() {
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i< values().length; i++){
            arrayList.add(values()[i].myFullState);
        }
        return arrayList;
    }
}
