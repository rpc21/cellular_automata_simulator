import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GUIHexagonGrid extends GUIGrid{
    private Simulation mySim;
    private List<Color> possibleColors;
    private double myApothemLength;
    private int myRows;
    private int myCols;



    public GUIHexagonGrid(int r, int c, Simulation sim){
        myApothemLength = GUIGrid.GUI_GRID_SIZE/r/2;
        myRows = r;
        myCols = c;
        mySim = sim;
        possibleColors = new LinkedList<Color>(myMap.keySet());

    }
    public void makeGUIGrid(List<Cell> myCells){
        int r = 0;
        int c = 0;
        while (r <  myRows){
            while (c < myCols){
                final int  rCopy = r;
                final int  cCopy = c;
                Polygon currHexagon = new Polygon();
                Double[] myPoints;
                if (r % 2 == 0) {
                    currHexagon.getPoints().addAll(new Double[]{
                            (2*myApothemLength*c) + myApothemLength,        (4*myApothemLength/Math.sqrt(3) * r) + 0.0,
                            (2*myApothemLength*c) + 2.0 * myApothemLength, (4*myApothemLength/Math.sqrt(3) * r) + myApothemLength / Math.sqrt(3),
                            (2*myApothemLength*c) + 2.0 * myApothemLength, (4*myApothemLength/Math.sqrt(3) * r) + myApothemLength * Math.sqrt(3),
                            (2*myApothemLength*c) + myApothemLength,       (4*myApothemLength/Math.sqrt(3) * r) + myApothemLength * 4 / Math.sqrt(3),
                            (2*myApothemLength*c) + 0.0,                    (4*myApothemLength/Math.sqrt(3) * r) + Math.sqrt(3) * myApothemLength,
                            (2*myApothemLength*c) + 0.0,                    (4*myApothemLength/Math.sqrt(3) * r) + myApothemLength / Math.sqrt(3)});
                    currHexagon.setTranslateX((2.0*myApothemLength*c));
                }
                else{
                    currHexagon.getPoints().addAll(new Double[]{
                            (2*myApothemLength*c) + 2.0 * myApothemLength,        (4*myApothemLength/Math.sqrt(3) * r) + 0.0,
                            (2*myApothemLength*c) + 3.0 * myApothemLength, (4*myApothemLength/Math.sqrt(3) * r) + myApothemLength / Math.sqrt(3),
                            (2*myApothemLength*c) + 3.0 * myApothemLength, (4*myApothemLength/Math.sqrt(3) * r) + myApothemLength * Math.sqrt(3),
                            (2*myApothemLength*c) + 2.0 * myApothemLength,       (4*myApothemLength/Math.sqrt(3) * r) + myApothemLength * 4 / Math.sqrt(3),
                            (2*myApothemLength*c) + myApothemLength,                    (4*myApothemLength/Math.sqrt(3) * r) + Math.sqrt(3) * myApothemLength,
                            (2*myApothemLength*c) + myApothemLength,                    (4*myApothemLength/Math.sqrt(3) * r) + myApothemLength / Math.sqrt(3)});
                    currHexagon.setTranslateX((2*myApothemLength*(c+0.5)));
                }
                currHexagon.setTranslateY(3/Math.sqrt(3)*myApothemLength*r);
                currHexagon.setFill(myCells.get(r * myRows + c).getMyColor());
                //Cell curr = myCells.get(r * myRows + c);
                currHexagon.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event)
                    {

                        if (possibleColors.indexOf(currHexagon.getFill()) + 1 != possibleColors.size())
                            //curr.setMyCurrentState(GOLState.ALIVE);
                            currHexagon.setFill(possibleColors.get(possibleColors.indexOf(currHexagon.getFill()) + 1));
                        else
                            currHexagon.setFill(possibleColors.get(0));
                       warnSimulation(rCopy,cCopy,currHexagon.getFill());
                    }

                });
                currHexagon.setStroke(Color.BLACK);
                currHexagon.setStrokeWidth(1.0/25.0*GUI_GRID_SIZE/myRows);
                populateGUIGridHex(currHexagon,c,r);
                c = c + 1;
            }
            c = 0;
            r = r + 1;
        }
    }

    private void warnSimulation(int r, int col, Paint c){
        mySim.replaceCell(new Location(r,col),myMap.get(c));
    }


}
