
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.List;

public class GUIGrid {
    private GridPane myGridPane = new GridPane();
    private int myRows;
    private int myCols;
    public static final double GUI_GRID_SIZE = 400;


    public GUIGrid(int r, int c){
        myRows = r;
        myCols = c;
        myGridPane.setTranslateX(CellularAutomataMain.WINDOW_SIZE/2.0 - GUI_GRID_SIZE/2.0);
        myGridPane.setTranslateY(CellularAutomataMain.WINDOW_SIZE/2.0 - GUI_GRID_SIZE/2.0);
        myGridPane.setMinSize(GUI_GRID_SIZE,GUI_GRID_SIZE);
        myGridPane.setMaxSize(GUI_GRID_SIZE,GUI_GRID_SIZE);
    }


    public void makeGUIGrid(List<Cell> myCells){
        int r = 0;
        int c = 0;
        while (r <  myRows){
            while (c < myCols){
                Shape currShape = new Rectangle(GUI_GRID_SIZE*1.0/myRows,GUI_GRID_SIZE * 1.0/myCols);
                currShape.setFill(myCells.get(r * myRows + c).getMyColor());
                currShape.setStroke(Color.BLACK);
                currShape.setStrokeWidth(1.0/25.0*GUI_GRID_SIZE/myRows);
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
