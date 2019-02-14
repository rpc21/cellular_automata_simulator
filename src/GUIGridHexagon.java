

public class GUIGridHexagon extends GUIGridPolygon{
    private double myApothemLength;
    private double myCols;


    public GUIGridHexagon(int r, int c){
        super(r,c);
        myCols = c;
        myApothemLength = GUIGrid.GUI_GRID_SIZE/(double)r/2.0;

    }

    /**
     * This getter method was necessary so GUIGridCell could determine the vertices for a hexagonal cell
     * in the grid
     * @param r Current row value helps determine location of cell in grid for some shapes, but hexagonal isn't affected by this
     * @param c Current column value helps determine location of cell in grid for some shapes, but hexagonal isn't affected by this
     * @return Double[] vertices of hexagon given apothem length
     */
    public Double[] getVertices(int r, int c){
        return new Double[]{
                            myApothemLength,        0.0,
                            2.0 * myApothemLength, myApothemLength / Math.sqrt(3),
                            2.0 * myApothemLength, myApothemLength * Math.sqrt(3),
                            myApothemLength,       myApothemLength * 4 / Math.sqrt(3),
                            0.0,                   Math.sqrt(3) * myApothemLength,
                            0.0,                   myApothemLength / Math.sqrt(3)};
    }

    /**
     * This getter method was necessary so GUIGridCell could determine the horizontal offset for a particular cell
     * in the grid
     * @param r Current row value helps determine location of cell in grid
     * @param c Current column value helps determine location of cell in grid
     * @return double horizontal offset of cell
     */
    public double getX(int r, int c){
        if (r % 2 == 1)
            return (2*myApothemLength*c);
        else
            return (2*myApothemLength*c + myApothemLength);
    }
    /**
     * This getter method was necessary so GUIGridCell could determine the vertical offset for a particular cell
     * in the grid
     * @param r Current row value helps determine location of cell in grid
     * @param c Current column value helps determine location of cell in grid
     * @return double vertical offset of cell
     */
    public double getY(int r, int c) {
        return (3*myApothemLength/Math.sqrt(3) * r );
    }

    /**
     * Given this particular shape, determines what the inner vertices of an agent shape would be
     * in the grid
     * @return Double[] of x,y coordinates of the agent
     */
    protected Double[] getInnerVertices(){
        Double[] arr = {myApothemLength/2.0, 0.0, myApothemLength, myApothemLength/2.0,
                myApothemLength/2.0, myApothemLength, 0.0, myApothemLength/2.0};
        return arr;
    }

}
