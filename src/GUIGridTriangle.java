

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
    public Double[] getInnerVertices(){
        Double[] arr = {myTriangleHeight/2.0, 0.0, myTriangleHeight, myTriangleHeight/2.0,
                myTriangleHeight/2.0, myTriangleHeight, 0.0, myTriangleHeight/2.0};
        return arr;
    }

    public double getX(int r, int c){
        return c * 1.0 * myTriangleBottomLength/2.0;
    }

    public double getY(int r, int c){
        return r * 1.0 * myTriangleHeight;
    }

}
