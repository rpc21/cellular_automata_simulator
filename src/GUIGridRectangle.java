import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import java.util.HashMap;

public class GUIGridRectangle extends GUIGridPolygon {
    private double myRectangleLength;
    private double myRectangleHeight;
    private int myCols;

    public GUIGridRectangle(int r, int c) {
        super(r,c);
        myCols = c;
        myRectangleLength = GUIGrid.GUI_GRID_SIZE / (double)r;
        myRectangleHeight = GUIGrid.GUI_GRID_SIZE / (double)c;
    }

    public Double[] getVertices(int r, int c) {
        return new Double[]{
                0.0, 0.0,
                myRectangleLength, 0.0,
                myRectangleLength, myRectangleHeight,
                0.0, myRectangleHeight};
    }

    public double getX(int r, int c) {

        return (c * 1.0 * myRectangleLength);
    }

    public double getY(int r, int c) {
        return (r * 1.0 * myRectangleHeight);
    }

    public double getHalfWay(){
        return myCols * myRectangleLength/2.0;
    }

    public Polygon getInnerPolygon(){
        Double[] arr = {myRectangleLength/2.0, 0.0, myRectangleLength, myRectangleHeight/2.0,
                myRectangleLength/2.0, myRectangleHeight, 0.0, myRectangleHeight/2.0};
        Polygon p = new Polygon();
        p.getPoints().addAll(arr);
        p.setFill(Color.DARKMAGENTA);
        return p;
    }


}

