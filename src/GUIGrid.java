
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;

public class GUIGrid {
    private StackPane myStackPane;
    private int myRows;
    private int myCols;
    private GUIGridCell myGUICell;
    private GUIGridOptions myOptions;

    public static final double GUI_GRID_SIZE = 500;

    public GUIGrid(int r, int c, Stage stage, GUIGridCell cell, Map<String,String> initProps){
        myRows = r;
        myCols = c;
        myGUICell = cell;
        myStackPane = new StackPane();
        myOptions = new GUIGridOptions(stage, initProps);

    }
    public void makeGUIGrid(List<Cell> myCells){
        myStackPane.getChildren().clear();
        int r = 0, c = 0;
        while (r <  myRows){
            while (c < myCols){
                String currState = myCells.get(r * myRows + c).getMyCurrentState().toString();
                boolean hasBorder = myOptions.getStroke();
                String polygon = myOptions.getShape();
                StackPane currStackPane = myGUICell.setUpCell(r,c, currState, hasBorder, polygon);
                populateGUIGrid(currStackPane);
                c = c + 1;
            }
            c = 0;
            r = r + 1;
        }
    };
    private void populateGUIGrid(StackPane cell){
        myStackPane.getChildren().addAll(cell);
    }

    public Node getGUIStyle(){
        return myOptions.getOptionsButton();
    }
    public StackPane getGUIGrid(){
        return myStackPane;
    }
    public double getHalfway(){
        return myGUICell.getHalfWay();
    }




}
