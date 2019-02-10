import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public enum ForageState implements CellState {

    FOOD("F", "FOOD", Color.RED),
    NEST("N", "NEST", Color.BROWN),
    EMPTY("O", "OPEN", Color.LIGHTGREEN),
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
