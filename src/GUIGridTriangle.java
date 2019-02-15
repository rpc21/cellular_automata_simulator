

public class GUIGridTriangle extends GUIGridPolygon{
    private double myTriangleBottomLength;
    private double myTriangleHeight;

    private static final double SIDE_TO_GRID_RATIO = 4.0/3.0;
    private static final double HEIGHT_TO_SIDE_RATIO = 21.0/40.0;

    public GUIGridTriangle(int r, int c) {
        super(r,c);
        myTriangleBottomLength = GUIGrid.GUI_GRID_SIZE/(double)r * SIDE_TO_GRID_RATIO;
        myTriangleHeight = myTriangleBottomLength * HEIGHT_TO_SIDE_RATIO;
    }

    /**
     * This getter method was necessary so GUIGridCell could determine the vertices for a triangularl cell
     * in the grid
     * @param r Current row value helps determine orientation of triangle
     * @param c Current column value helps determine orientation of triangle
     * @return Double[] vertices of triangle given height and length
     */
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
    /**
     * Given this particular shape, determines what the inner vertices of an agent shape would be
     * in the grid
     * @return Double[] of x,y coordinates of the agent
     */
    public Double[] getInnerVertices(){
        Double[] arr = {myTriangleHeight/2.0, 0.0, myTriangleHeight, myTriangleHeight/2.0,
                myTriangleHeight/2.0, myTriangleHeight, 0.0, myTriangleHeight/2.0};
        return arr;
    }
    /**
     * This getter method was necessary so GUIGridCell could determine the horizontal offset for a particular cell
     * in the grid
     * @param r Current row value helps determine location of cell in grid
     * @param c Current column value helps determine location of cell in grid
     * @return double horizontal offset of cell
     */
    public double getX(int r, int c){
        return c * 1.0 * myTriangleBottomLength/2.0;
    }
    /**
     * This getter method was necessary so GUIGridCell could determine the vertical offset for a particular cell
     * in the grid
     * @param r Current row value helps determine location of cell in grid
     * @param c Current column value helps determine location of cell in grid
     * @return double vertical offset of cell
     */
    public double getY(int r, int c){
        return r * 1.0 * myTriangleHeight;
    }

}
