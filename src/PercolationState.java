import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * PercolationState is an Enum that defines all the possible states for a PercolationCell
 * There are three states: PERCOLATED, OPEN and BLOCKED
 */
public enum PercolationState implements CellState{

    PERCOLATED("P", "PERCOLATED", Color.CYAN),
    OPEN("O", "OPEN", Color.WHITE),
    CLOSED("C", "CLOSED", Color.BLACK);

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    PercolationState(String shortenedName, String ParamName, Color cellColor){
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
            arrayList.add(values()[i].myFullState);
        }
        return arrayList;
    }

}
