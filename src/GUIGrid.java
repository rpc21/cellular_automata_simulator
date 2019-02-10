import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GUIGrid {
    private StackPane myStackPane;
    private int myRows;
    private int myCols;
    private GUIGridPolygon myPolygon;
    private Simulation mySim;
    private GUIGridOptions myOptions;
    private GUISimulationFactory myFactory = new GUISimulationFactory();
    private LinkedList<Color> myPossibleColors;
    protected HashMap<Color,String> myMap = new HashMap<Color,String>();
    protected HashMap<String,Color> revMap;
    public static final double GUI_GRID_SIZE = 500;

    public GUIGrid(int r, int c, Simulation sim, Stage stage, GUIGridPolygon polygon){
        myRows = r;
        myCols = c;
        mySim = sim;
        myPolygon = polygon;
        myStackPane = new StackPane();
        HashMap<String,Color> wrongOrderMap = new HashMap<>();
        wrongOrderMap.put("ALIVE", GOLState.ALIVE.getMyCellColor());
        wrongOrderMap.put("DEAD", GOLState.DEAD.getMyCellColor());
        revMap = wrongOrderMap;
        reverse(revMap);
        myPossibleColors = new LinkedList<Color>(myMap.keySet());
        myOptions = new GUIGridOptions(stage);

    }
    public void makeGUIGrid(List<Cell> myCells){
        myStackPane.getChildren().clear();
        myPolygon = myFactory.makeGUIPolygon(myOptions.getShape(),mySim);
        int r = 0, c = 0;
        while (r <  myRows){
            while (c < myCols){
                final int  rCopy = r;
                final int  cCopy = c;
                Polygon currPolygon = new Polygon();
                currPolygon.getPoints().addAll(myPolygon.getVertices(r,c));
                currPolygon.setTranslateX((myPolygon.getX(r,c)));
                currPolygon.setTranslateY(myPolygon.getY(r,c));
                currPolygon.setFill(myCells.get(r * myRows + c).getMyColor());
                currPolygon.setOnMousePressed(new EventHandler<MouseEvent>() {
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
                currPolygon.setStroke(myOptions.getStroke(currPolygon.getFill()));
                populateGUIGrid(currPolygon,c,r);
                c = c + 1;
            }
            c = 0;
            r = r + 1;
        }
    };

    public Node getGUIStyle(){
        return myOptions.getOptionsButton();
    }
    public double getHalfway(){
        return myPolygon.getHalfWay();
    }

    public StackPane getGUIGrid(){
        return myStackPane;
    }
    protected void populateGUIGrid(Shape myShape, int r, int c){
        myStackPane.getChildren().addAll(myShape);
    }

    private void reverse(HashMap<String,Color> wrong){
        for(Map.Entry<String, Color> entry : wrong.entrySet()){
            myMap.put(entry.getValue(), entry.getKey());
        }

    }
    private void warnSimulation(int r, int col, Paint c){
        mySim.replaceCell(new Location(r,col),myMap.get(c));
    }


}
