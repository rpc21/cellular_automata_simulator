import javafx.scene.shape.Polygon;

public abstract class GUIGridPolygon {

    public GUIGridPolygon(int r, int c){
    }
    public abstract Double[] getVertices(int r, int c);

    public abstract double getX(int r, int c);

    public abstract double getY(int r, int c);

    public abstract double getHalfWay();

    public abstract Polygon getInnerPolygon();
}
