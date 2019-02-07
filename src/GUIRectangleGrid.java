import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.List;

public class GUIRectangleGrid extends GUIGrid{
    private double myRectangleLength;
    private double myRectangleHeight;
    private int myRows;
    private int myCols;

    public GUIRectangleGrid(int r, int c){
        myRectangleLength = GUI_GRID_SIZE/r;
        myRectangleHeight= GUI_GRID_SIZE/c;
        myRows = r;
        myCols = c;
    }
    public void makeGUIGrid(List<Cell> myCells){
        int r = 0;
        int c = 0;
        while (r <  myRows){
            while (c < myCols){
                Shape currShape = new Rectangle(myRectangleLength,myRectangleHeight);
                currShape.setFill(myCells.get(r * myRows + c).getMyColor());
                currShape.setStroke(Color.BLACK);
                currShape.setStrokeWidth(1.0/25.0*GUI_GRID_SIZE/myRows);
                currShape.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        currShape.setFill(Color.ALICEBLUE);
                    }
                });
                populateGUIGrid(currShape,c,r);
                c = c + 1;
            }
            c = 0;
            r = r + 1;
        }
    }
}

