
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.List;

public class GUIGrid {
    private GridPane myGridPane = new GridPane();
    private int myRows;
    private int myCols;
    public static final int GUI_GRID_SIZE = 400;


    public GUIGrid(int r, int c){
        myRows = r;
        myCols = c;
        myGridPane.setLayoutX(CellularAutomataMain.WINDOW_SIZE/2 - GUI_GRID_SIZE/2);
        myGridPane.setLayoutY(CellularAutomataMain.WINDOW_SIZE/2 - GUI_GRID_SIZE/2);
    }


    public void makeGUIGrid(List<Cell> myCells){
        int r = 0;
        int c = 0;
        while (r <  myRows){
            while (c < myCols){
                Shape currShape = new Rectangle(GUI_GRID_SIZE/myRows,GUI_GRID_SIZE/myCols);
                currShape.setFill(myCells.get(r * myRows + c).getMyColor());
                currShape.setStroke(Color.BLACK);
                myGridPane.add(currShape, c, r);
                c = c + 1;
            }
            c = 0;
            r = r + 1;
        }
    }

    public GridPane getGUIGrid(){
        return myGridPane;
    }






}
