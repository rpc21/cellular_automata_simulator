import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

public class GUIGridTriangle extends GUIGridPolygon{
    private double myTriangleBottomLength;
    private double myTriangleHeight;
    private double myCols;

    private static final double SIDE_TO_GRID_RATIO = 4.0/3.0;
    private static final double HEIGHT_TO_SIDE_RATIO = 21.0/40.0;

    public GUIGridTriangle(int r, int c) {
        super(r,c);
        myCols = c;
        myTriangleBottomLength = GUIGrid.GUI_GRID_SIZE/(double)r * SIDE_TO_GRID_RATIO;
        myTriangleHeight = myTriangleBottomLength * HEIGHT_TO_SIDE_RATIO;
    }


    public Double[] getVertices(int r, int c) {
        if ((r % 2 == 0 && c %2 ==0) || (r % 2 == 1 && c % 2 == 1))
            return new Double[]{
                    myTriangleBottomLength/2.0, 0.0,
                    0.0, myTriangleHeight,
                    myTriangleBottomLength, myTriangleHeight};
        else
            return new Double[]{
                0.0, 0.0,
                myTriangleBottomLength, 0.0,
                myTriangleBottomLength/2, myTriangleHeight };

    }

    public double getX(int r, int c){
        return c * 1.0 * myTriangleBottomLength/2.0;
    }

    public double getY(int r, int c){
        return r * 1.0 * myTriangleHeight;
    }

    public double getHalfWay(){
        return myCols * myTriangleBottomLength/2.0 * 1.0/SIDE_TO_GRID_RATIO;
    }

    public Polygon getPolygon(int r, int c){
        Polygon p = new Polygon();
        p.getPoints().addAll(getVertices(r,c));
        return p;
    }

}
