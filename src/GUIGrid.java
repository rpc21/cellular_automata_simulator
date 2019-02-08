import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GUIGrid {
    private GridPane myGridPane = new GridPane();
    private StackPane myStackPane = new StackPane();
    protected HashMap<Color,String> myMap;
    protected HashMap<String,Color> revMap;
    public static final int GUI_GRID_SIZE = 400;

    public GUIGrid(){
        myGridPane.setLayoutX(CellularAutomataMain.WINDOW_SIZE/2 - GUI_GRID_SIZE/2);
        myGridPane.setLayoutY(CellularAutomataMain.WINDOW_SIZE/2 - GUI_GRID_SIZE/2);
        //getter function for String to Color Map
        HashMap<String,Color> wrongOrderMap = new HashMap<>();
        wrongOrderMap.put("ALIVE", GOLState.ALIVE.getMyCellColor());
        wrongOrderMap.put("DEAD", GOLState.DEAD.getMyCellColor());
        revMap = wrongOrderMap;
        myMap = new HashMap<>();
        reverse(wrongOrderMap);
    }
    public abstract void makeGUIGrid(List<Cell> myCells);

    protected void populateGUIGrid(Shape myShape, int r, int c){
        myGridPane.add(myShape,r,c);
    }

    public GridPane getGUIGrid(){
        return myGridPane;
    }
    public StackPane getGUIHexGrid(){
        myStackPane.setTranslateX(CellularAutomataMain.WINDOW_SIZE/2 - GUI_GRID_SIZE/2);
        myStackPane.setTranslateY(CellularAutomataMain.WINDOW_SIZE/2 - GUI_GRID_SIZE/2);
        return myStackPane;
    }
    protected void populateGUIGridHex(Shape myShape, int r, int c){
        myStackPane.getChildren().addAll(myShape);
    }
    private void reverse(HashMap<String,Color> wrong){
        for(Map.Entry<String, Color> entry : wrong.entrySet()){
            myMap.put(entry.getValue(), entry.getKey());
        }

    }

}
