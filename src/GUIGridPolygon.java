import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
/**
 * This class allows me to use different shapes for cells but still have the flexibility of adding multiple items to one cell.
 * This class is well designed because it provides a uniform manner to structure cells but allows them to handle the issue of
 * cells intertwining with one another. It is difficult to ensure the offsets for each shape align properly with their
 * neighbors, so each GUIGridPolygon subclass handles this issue
 */
public abstract class GUIGridPolygon {
    private static final Color MY_INNER_POLYGON_COLOR = Color.DARKMAGENTA;

    public GUIGridPolygon(int r, int c){
    }
    /**
     * This getter method was necessary so GUIGridCell could determine the vertices for a polygon's cell
     * in the grid
     * @param r Current row value helps determine location of cell in grid for some shapes
     * @param c Current column value helps determine location of cell in grid for some shapes
     * @return Double[] vertices of polygon
     */
    public abstract Double[] getVertices(int r, int c);
    /**
     * Given this particular shape, determines what the inner vertices of an agent shape would be
     * in the grid
     * @return Double[] of x,y coordinates of the agent
     */
    protected abstract Double[] getInnerVertices();
    /**
     * This getter method was necessary so GUIGridCell could determine the horizontal offset for a particular cell
     * in the grid
     * @param r Current row value helps determine location of cell in grid
     * @param c Current column value helps determine location of cell in grid
     * @return double horizontal offset of cell
     */
    public abstract double getX(int r, int c);

    /**
     * This getter method was necessary so GUIGridCell could determine the vertical offset for a particular cell
     * in the grid
     * @param r Current row value helps determine location of cell in grid
     * @param c Current column value helps determine location of cell in grid
     * @return double vertical offset of cell
     */
    public abstract double getY(int r, int c);

    /**
     * This getter method was necessary so GUIGridCell wouldn't have to handle calculating the inner polygon's vertices
     * @return inner polygon of the cell, meant to be displayed as an "agent" of the cell
     */
    public Polygon getInnerPolygon(){
        Double[] arr = getInnerVertices();
        Polygon p = new Polygon();
        p.getPoints().addAll(arr);
        p.setFill(MY_INNER_POLYGON_COLOR);
        return p;
    }
}
