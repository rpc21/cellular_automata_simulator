import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumberates the possible state in the Forage Simulation
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
    public String toString(){ return myFullState;}
    @Override
    public String getMyShortenedName() {
        return myShortenedName;
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
