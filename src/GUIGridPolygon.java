import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public abstract class GUIGridPolygon {
    private static final Color MY_INNER_POLYGON_COLOR = Color.DARKMAGENTA;

    public GUIGridPolygon(int r, int c){
    }
    public abstract Double[] getVertices(int r, int c);

    protected abstract Double[] getInnerVertices();

    public abstract double getX(int r, int c);

    public abstract double getY(int r, int c);


    public Polygon getInnerPolygon(){
        Double[] arr = getInnerVertices();
        Polygon p = new Polygon();
        p.getPoints().addAll(arr);
        p.setFill(MY_INNER_POLYGON_COLOR);
        return p;
    }
}
