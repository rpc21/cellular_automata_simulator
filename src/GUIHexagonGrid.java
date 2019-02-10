import javafx.stage.Stage;

public class GUIHexagonGrid extends GUIGrid{
    private double myApothemLength;
    private double myCols;


    public GUIHexagonGrid(int r, int c, Simulation sim, Stage s){
        super(r,c,sim,s);
        myCols = c;
        myApothemLength = GUIGrid.GUI_GRID_SIZE/(double)r/2.0;
        System.out.println(myApothemLength);

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



}
