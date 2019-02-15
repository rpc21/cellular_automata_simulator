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
/**
 * This class abstracts information concerning how the appearance of an actual cell is displayed on the GUIGrid.
 * This is a good design for this general feature because there is only one place that needs to necessarily access Simulation
 * information. Further, this abstraction provides flexibility in the way cells appear because some simulations require
 * that a particular cell have more than one item in it.
 */

public class GUIGridCell {

    private Map<Paint,String> myPaintToStateMap;
    private Map<String,Paint> myStateToPaintMap;
    private List<Paint> myPossibleColors;
    private Simulation mySim;
    private GUIGridPolygon myPolygon;
    private GUISimulationFactory myFactory = new GUISimulationFactory();

    public GUIGridCell(Map<String,Paint> myMap, Simulation sim, GUIGridPolygon shape){
        myStateToPaintMap = myMap;
        myPaintToStateMap = reverse(myMap);
        myPossibleColors = new ArrayList<>(myMap.values());
        mySim = sim;
        myPolygon = shape;
    }

    /**
     * This method is called from GUIGrid and sets up the cell given a particular set of guidelines for its display
     * @param r Current row value helps determine orientation of particular polygons and position
     * @param c Current column value helps determine orientation of particular polygons and position
     * @param border used to determine whether or not the grid lines should be visible in this particular simulation
     * @param state state is used to map to color of cell
     * @return StackPane of cell, which may involve a shape or a shape and an agent
     * @see GUIGridPolygon
     * @see GUISimulationFactory
     */
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

    /**
     * Adds functionality to cells so that user can cycle through various states by clicking on the cell
     * @param r Current row value helps identify the particular shape
     * @param c Current column value helps identify the particular shape
     * @param currPolygon used to add EventHandler
     * @param currStack state is used to map to color of cell
     * @return StackPane of cell, which may involve a shape or a shape and an agent
     * @see GUIGridPolygon
     * @see GUISimulationFactory
     */
    private void setUpColorSwitch(int r, int c, Polygon currPolygon,StackPane currStack){
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

    /**
     * This method references a Simulation method to replace the current cell at the current r, c location
     * when the user changes it by clicking on the cell
     * @param r Current row value helps determine location of cell in Simulation's grid
     * @param c Current column value helps determine location of cell in Simulation's grid
     * @param color Get new state value of the cell
     * @return StackPane of cell, which may involve a shape or a shape and an agent
     * @see GUIGridPolygon
     * @see GUISimulationFactory
     */
    private void warnSimulation(int r, int c, Paint color){
        mySim.replaceCell(new Location(r,c),myPaintToStateMap.get(color));
    }

    /**
     * Reverses state to color map for ease of identifying the current state to communicate back to simulation
     * @return map of colors to states
     */
    private Map<Paint,String> reverse(Map<String,Paint> wrong){
        Map<Paint,String> tempMap = new HashMap<>();
        for(Map.Entry<String, Paint> entry : wrong.entrySet()){
            tempMap.put(entry.getValue(), entry.getKey());
        }
        return tempMap;

    }
    /**
     * This method adds an "agent" to the cell (for example, an ant). Uses Polygon object to help create an inner shape
     * that fits into usual cell
     * @param sp current stackpane of the polygon for the cell
     * @see GUIGridPolygon
     */
    public void addAgent(StackPane sp){
        Polygon innerPolygon = myPolygon.getInnerPolygon();
        sp.getChildren().addAll(innerPolygon);
    }
}
