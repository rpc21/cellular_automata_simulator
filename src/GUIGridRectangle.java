import javafx.stage.Stage;

public class GUIGridRectangle extends GUIGridPolygon {
    private double myRectangleLength;
    private double myRectangleHeight;
    private double myCols;

    public GUIGridRectangle(int r, int c) {
        super(r, c);
        myCols = c;
        myRectangleLength = GUIGrid.GUI_GRID_SIZE / r;
        myRectangleHeight = GUIGrid.GUI_GRID_SIZE / c;
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

}

