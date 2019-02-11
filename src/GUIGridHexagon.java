import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.HashMap;

public class GUIGridHexagon extends GUIGridPolygon{
    private double myApothemLength;
    private double myCols;


    public GUIGridHexagon(int r, int c){
        super(r,c);
        myCols = c;
        myApothemLength = GUIGrid.GUI_GRID_SIZE/(double)r/2.0;

    }


    public Double[] getVertices(int r, int c){
        return new Double[]{
                            myApothemLength,        0.0,
                            2.0 * myApothemLength, myApothemLength / Math.sqrt(3),
                            2.0 * myApothemLength, myApothemLength * Math.sqrt(3),
                            myApothemLength,       myApothemLength * 4 / Math.sqrt(3),
                            0.0,                   Math.sqrt(3) * myApothemLength,
                            0.0,                   myApothemLength / Math.sqrt(3)};
    };

    public double getX(int r, int c){
        if (r % 2 == 1)
            return (2*myApothemLength*c);
        else
            return (2*myApothemLength*c + myApothemLength);
    }

    public double getY(int r, int c) {
        return (3*myApothemLength/Math.sqrt(3) * r );
    }

    public double getHalfWay(){
        return myCols * myApothemLength + myApothemLength/2.0;
    }

    public Polygon getInnerPolygon(){
        Double[] arr = {myApothemLength/2.0, 0.0, myApothemLength, myApothemLength/2.0,
                myApothemLength/2.0, myApothemLength, 0.0, myApothemLength/2.0};
        Polygon p = new Polygon();
        p.getPoints().addAll(arr);
        p.setFill(Color.DARKMAGENTA);
        return p;
    }

}
