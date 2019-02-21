import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerates the possible state that a SugarPatch can take
 */
public enum SugarState implements CellState{
    LIGHT_PATCH("LP", "LIGHT_PATCH", Color.WHITE),
    MEDIUM_LIGHT_PATCH("ML", "MEDIUM_LIGHT_PATCH", Color.LIGHTGREEN),
    MEDIUM_PATCH("MP", "MEDIUM_PATCH", Color.GREEN),
    MEDIUM_DARK_PATCH("MD", "MEDIUM_DARK_PATCH", Color.DARKGREEN),
    DARK_PATCH("DP", "DARK_PATCH", Color.BLACK);

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    SugarState(String shortenedName, String paramName, Color cellColor){
        myShortenedName = shortenedName;
        myFullState = paramName;
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
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i< values().length; i++){
            arrayList.add(values()[i].myFullState);
        }
        return arrayList;
    }
}
