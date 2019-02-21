import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerates the possible state in the Forage Simulation
 */
public enum ForageState implements CellState {

    FOOD("F", "FOOD", Color.RED),
    NEST("N", "NEST", Color.BROWN),
    EMPTY("E", "EMPTY", Color.LIGHTGREEN),
    OBSTACLE("O", "OBSTACLE", Color.WHITE);


    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    ForageState(String shortenedName, String fullState, Color cellColor){
        myShortenedName = shortenedName;
        myFullState = fullState;
        myCellColor = cellColor;
    }

    /**
     * @return string representation of fullState name
     */
    @Override
    public String toString(){ return myFullState;}

    /**
     * @return one letter representation of the cell state
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
