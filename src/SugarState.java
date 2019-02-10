import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public enum SugarState implements CellState{
    PATCH("P", "PATCH", Color.ORANGE);

    private final String myShortenedName;
    private final String myFullState;
    private final Color myCellColor;

    SugarState(String shortenedName, String paramName, Color cellColor){
        myShortenedName = shortenedName;
        myFullState = paramName;
        myCellColor = cellColor;
    }

    @Override
    public String getMyShortenedName() {
        return myShortenedName;
    }

    @Override
    public Color getMyCellColor() {
        return Color.ORANGE;
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