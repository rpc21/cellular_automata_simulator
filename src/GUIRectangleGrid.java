
public class GUIRectangleGrid extends GUIGrid {
    private double myRectangleLength;
    private double myRectangleHeight;
    private double myCols;

    public GUIRectangleGrid(int r, int c, Simulation sim) {
        super(r, c, sim);
        myCols = c;
        myRectangleLength = GUI_GRID_SIZE / r;
        myRectangleHeight = GUI_GRID_SIZE / c;
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

