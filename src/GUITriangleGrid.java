import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.List;

public class GUITriangleGrid extends GUIGrid{
    private double myTriangleLength;
    private double myTriangleHeight;
    private int myRows;
    private int myCols;

    public GUITriangleGrid(int r, int c){
        myTriangleLength = GUI_GRID_SIZE/r;
        myTriangleHeight = Math.sqrt(3)/2*myTriangleLength;
        myRows = r;
        myCols = c;
    }
    public void makeGUIGrid(List<Cell> myCells){
        int r = 0;
        int c = 0;
        while (r <  myRows){
            while (c < myCols){
                Polygon currTriangle = new Polygon();
                Double[] myPoints;
                if ((r % 2 == 0 && c %2 ==0) || (r % 2 == 1 && c % 2 == 1)) //Right-side up triangle
                    currTriangle.getPoints().addAll(new Double[]{
                            myTriangleLength/2, 0.0,
                            0.0, myTriangleHeight,
                            myTriangleLength, myTriangleHeight });
                else
                    currTriangle.getPoints().addAll(new Double[]{
                            0.0, 0.0,
                            myTriangleLength, 0.0,
                            myTriangleLength/2, myTriangleHeight });
                currTriangle.setFill(myCells.get(r * myRows + c).getMyColor());
                currTriangle.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        currTriangle.setFill(Color.DARKMAGENTA);
                    }
                });
                currTriangle.setStroke(Color.BLACK);
                currTriangle.setStrokeWidth(1.0/25.0*GUI_GRID_SIZE/myRows);
                populateGUIGrid(currTriangle,c,r);
                c = c + 1;
            }
            super.getGUIGrid().setHgap(-0.5 *myTriangleLength);
            c = 0;
            r = r + 1;
        }
    }
}
