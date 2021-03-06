import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerates the possibl e states for wator cells
 */
public enum WatorState implements CellState {

    EMPTY("E", "EMPTY", Color.AQUAMARINE),
    FISH("F", "FISH", Color.ORANGE),
    SHARK("S", "SHARK", Color.LIGHTGREY);

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    WatorState(String shortenedName, String fullState, Color cellColor){
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
     * Return the possible states of a Wator cell as Strings
     * @return the possible states of a Wator cell as Strings
     */
    @Override
    public List<String> getPossibleValues() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i< values().length; i++){
            arrayList.add(values()[i].myFullState);
        }
        return arrayList;
    }
}
