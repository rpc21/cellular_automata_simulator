import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;

public class GUIGrid {
    private StackPane myStackPane;
    private int myRows;
    private int myCols;
    private GUIGridCell myGUICell;
    private GUIGridOptions myOptions;

    public static final double GUI_GRID_SIZE = 500;

    public GUIGrid(int r, int c, Stage stage, GUIGridCell cell, Map<String,String> initProps){
        myRows = r;
        myCols = c;
        myGUICell = cell;
        myStackPane = new StackPane();
        myOptions = new GUIGridOptions(stage, initProps);

    }
    /**
     * This method sets up the display of the grid by looping over the cells and using the current state of GUIOptions
     * to inform GUIGridCell to appropriately display itself
     * type
     * @see GUIGridCell
     * @see GUIGridOptions
     */
    public void makeGUIGrid(List<Cell> myCells){
        myStackPane.getChildren().clear();
        int r = 0, c = 0;
        while (r <  myRows){
            while (c < myCols){
                String currState = myCells.get(r * myRows + c).getMyCurrentState();
                boolean hasBorder = myOptions.getStroke();
                String polygon = myOptions.getShape();
                StackPane currStackPane = myGUICell.setUpCell(r,c, currState, hasBorder, polygon);
                if (myCells.get(r*myRows + c).containsAgent())
                    myGUICell.addAgent(currStackPane);
                populateGUIGrid(currStackPane);
                c = c + 1;
            }
            c = 0;
            r = r + 1;
        }
    }
    /**
     * The cell is a stackpane to allow for multiple layers to be added and generalize how it may appear. This stackpane is
     * layered on another stackpane that is used to display the entire grid to the user in the window
     * type
     * @see StackPane
     */
    private void populateGUIGrid(StackPane cell){
        myStackPane.getChildren().addAll(cell);
    }
    /**
     * This getter method was necessary to pass back to simulation when it has to restart and change the rules for which neighbors
     * will determine the cells' next states
     * type
     * @see GUIGridOptions
     * @see GUIGridNeighborsChooser
     * @return String name of the neighbors type the user wants to simulate
     */
    public String getNeighbors(){ return myOptions.getNeighbors();}
    /**
     * This getter method was necessary to add the node that allows the user to access a list of visual/simulation rules
     * to the root of the scene in GUI
     * type
     * @see GUIGridOptions
     * @return Node options button
     */
    public Node getGUIStyle(){
        return myOptions.getOptionsButton();
    }
    /**
     * This getter method was necessary to add the stackpane of cells to be displayed
     * type
     * @return StackPane to grid display of cells
     */
    public StackPane getGUIGrid(){
        return myStackPane;
    }





}
