
public class GUIGridRectangle extends GUIGridPolygon {
    private double myRectangleLength;
    private double myRectangleHeight;

    public GUIGridRectangle(int r, int c) {
        super(r,c);
        myRectangleLength = GUIGrid.GUI_GRID_SIZE / (double)r;
        myRectangleHeight = GUIGrid.GUI_GRID_SIZE / (double)c;
    }
    /**
     * This getter method was necessary so GUIGridCell could determine the vertices for a rectangular cell
     * in the grid
     * @param r Current row value helps determine location of cell in grid for some shapes, but rectangular isn't affected by this
     * @param c Current column value helps determine location of cell in grid for some shapes, but rectangular isn't affected by this
     * @return Double[] vertices of rectangle given its height and length
     */
    public Double[] getVertices(int r, int c) {
        return new Double[]{
                0.0, 0.0,
                myRectangleLength, 0.0,
                myRectangleLength, myRectangleHeight,
                0.0, myRectangleHeight};
    }
    /**
     * This getter method was necessary so GUIGridCell could determine the horizontal offset for a particular cell
     * in the grid
     * @param r Current row value helps determine location of cell in grid
     * @param c Current column value helps determine location of cell in grid
     * @return double horizontal offset of cell
     */
    public double getX(int r, int c) {

        return (c * 1.0 * myRectangleLength);
    }
    /**
     * This getter method was necessary so GUIGridCell could determine the vertical offset for a particular cell
     * in the grid
     * @param r Current row value helps determine location of cell in grid
     * @param c Current column value helps determine location of cell in grid
     * @return double vertical offset of cell
     */
    public double getY(int r, int c) {
        return (r * 1.0 * myRectangleHeight);
    }

    /**
     * Given this particular shape, determines what the inner vertices of an agent shape would be
     * in the grid
     * @return Double[] of x,y coordinates of the agent
     */
    protected Double[] getInnerVertices(){
        Double[] arr = {myRectangleLength/2.0, 0.0, myRectangleLength, myRectangleHeight/2.0,
                myRectangleLength/2.0, myRectangleHeight, 0.0, myRectangleHeight/2.0};
        return arr;
    }


}

