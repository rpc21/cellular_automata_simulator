import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * GOLState is an Enum that defines all the possible states for a GOLCell
 * There are two states: DEAD and ALIVE
 */
public enum GOLState implements CellState{
    DEAD("D", "DEAD", Color.DARKGRAY),
    ALIVE("A", "ALIVE", Color.GREEN);

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    GOLState(String shortenedName, String paramName, Color cellColor){
        myShortenedName = shortenedName;
        myFullState = paramName;
        myCellColor = cellColor;
    }

//    /**
//     * Return a character representation of the cell state
//     * @return a String of length 1 representing the state of the cell
//     */
//    @Override
//    public String toString() {
//        return myShortenedName;
//    }


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
