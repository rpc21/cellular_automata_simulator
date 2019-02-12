
public class GUIGridRectangle extends GUIGridPolygon {
    private double myRectangleLength;
    private double myRectangleHeight;

    public GUIGridRectangle(int r, int c) {
        super(r,c);
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


    protected Double[] getInnerVertices(){
        Double[] arr = {myRectangleLength/2.0, 0.0, myRectangleLength, myRectangleHeight/2.0,
                myRectangleLength/2.0, myRectangleHeight, 0.0, myRectangleHeight/2.0};
        return arr;
    }


}

