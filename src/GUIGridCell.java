import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GUIGridCell {

    private HashMap<Paint,String> myPaintToStateMap;
    private HashMap<String,Paint> myStateToPaintMap;
    private List<Paint> myPossibleColors;
    private Simulation mySim;
    private GUIGridPolygon myPolygon;
    private GUISimulationFactory myFactory = new GUISimulationFactory();

    public GUIGridCell(HashMap<String,Paint> myMap, Simulation sim, GUIGridPolygon shape){
        myStateToPaintMap = myMap;
        myPaintToStateMap = reverse(myMap);
        myPossibleColors = new ArrayList<>(myMap.values());
        mySim = sim;
        myPolygon = shape;
    }


    public double getHalfWay(){
        return myPolygon.getHalfWay();
    }


    public StackPane setUpCell(int r, int c, String state, boolean border, String shape){
        Polygon p = new Polygon();
        myPolygon = myFactory.makeGUIPolygon(mySim.getMyGrid().getNumRows(),mySim.getMyGrid().getNumCols(),shape);
        p.getPoints().addAll(myPolygon.getVertices(r,c));
        p.setFill(myStateToPaintMap.get(state));
        if (border)
            p.setStroke(Color.BLACK);
        else
            p.setStroke(p.getFill());
        StackPane sp = new StackPane();

        sp.getChildren().addAll(p);
        sp.setTranslateX(myPolygon.getX(r,c));
        sp.setTranslateY(myPolygon.getY(r,c));

        setUpColorSwitch(r,c,p,sp);
        return sp;
    }


    protected void setUpColorSwitch(int r, int c, Polygon currPolygon,StackPane currStack){
        final int rCopy = r;
        final int cCopy = c;
        currStack.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (myPossibleColors.indexOf(currPolygon.getFill()) + 1 != myPossibleColors.size())
                    currPolygon.setFill(myPossibleColors.get(myPossibleColors.indexOf(currPolygon.getFill()) + 1));
                else
                    currPolygon.setFill(myPossibleColors.get(0));
                warnSimulation(rCopy,cCopy,currPolygon.getFill());
            }
        });
    }


    private void warnSimulation(int r, int c, Paint color){
        mySim.replaceCell(new Location(r,c),myPaintToStateMap.get(color));
    }

    private HashMap<Paint,String> reverse(HashMap<String,Paint> wrong){
        HashMap<Paint,String> tempMap = new HashMap<>();
        for(Map.Entry<String, Paint> entry : wrong.entrySet()){
            tempMap.put(entry.getValue(), entry.getKey());
        }
        return tempMap;

    }
}
