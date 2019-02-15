import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.*;

public class GUIDefaultPanel extends GUIPanel {
    private StackPane myStackPane = new StackPane();
    private Collection<Node> myDefaultControls = new ArrayList<>();
    private Map<String,String> myBasicParams = new HashMap<>();

    GUIPlayButton myPlayButton;
    GUIStepButton myStepButton;
    GUIAddSimulationButton myAddSimButton;
    GUISpeedSlider mySpeedSlider;
    GUIRowColSpinner myRowSpinner;
    GUISimulationNameChooser mySimChooser;
    GUIResetButton myResetButton;

    private static final double STACKPANE_OFFSET = GUI.STAGE_SIZE/2 + GUIGrid.GUI_GRID_SIZE/2 + 30;
    private static final int DEFAULT_CONTROL_OFFSET = 10;
    private static final int DEFAULT_CONTROL_SPACING = 40;

    public GUIDefaultPanel(GUIGridStep step, GUIReset reset, GUIAddSimulation add, GUIRemoveSimulation remove, Timeline timeline,
                           KeyFrame frame, String simName, int rows, int cols){

        myPlayButton = new GUIPlayButton(timeline);
        myStepButton = new GUIStepButton(timeline,step);
        myAddSimButton = new GUIAddSimulationButton(add,remove);
        mySpeedSlider = new GUISpeedSlider(timeline,frame,step);
        myRowSpinner = new GUIRowColSpinner(rows);
        mySimChooser = new GUISimulationNameChooser(simName);
        myResetButton = new GUIResetButton(timeline,reset);

        myDefaultControls.addAll(myPlayButton.getDisplay());
        myDefaultControls.addAll(myStepButton.getDisplay());
        myDefaultControls.addAll(myAddSimButton.getDisplay());
        myDefaultControls.addAll(mySpeedSlider.getDisplay());
        myDefaultControls.addAll(myRowSpinner.getDisplay());
        myDefaultControls.addAll(mySimChooser.getDisplay());
        myDefaultControls.addAll(myResetButton.getDisplay());
        populateMap(rows,cols, simName);
        arrangeControls();
    }
    /**
     * These are the bare-minimum basic parameters the backend needs to begin a new simulation - rows, columns, simulation.
     * There is a getter method for this map, this is simply to guarantee each key value has an initial value if it does not change
     * throughout the simulation
     * type
     * @see EventHandler < MouseEvent >
     */
    private void populateMap(int r, int c, String myName){
        myBasicParams.put(XMLParser.COLUMN_TAG_NAME, "" + r);
        myBasicParams.put(XMLParser.ROW_TAG_NAME, "" + c);
        myBasicParams.put(XMLParser.SIMULATION_TYPE_TAG_NAME, "" + myName);
    }

    public StackPane getGUIDefaultPanel(){
        return myStackPane;
    }

    /**
     * This method appropriately spaces out controls and their corresponding labels in the default panel
     * type
     * @see StackPane
     */
    private void arrangeControls(){
        int iter = 0;
        for(Node c: myDefaultControls) {
            myStackPane.getChildren().addAll(c);
            myStackPane.setLayoutX(STACKPANE_OFFSET);
            c.setTranslateX(DEFAULT_CONTROL_OFFSET);
            c.setTranslateY(iter * DEFAULT_CONTROL_SPACING);
            iter++;
        }
    }
    /**
     * This method returns the current simulation name. This getter method is necessary because the simulation only restarts
     * when the user prompts the GUI to, so the GUIManager needs a way of determining which type of restart it needs to do (i.e.
     * whether the whole simulation changed, or if only a parameter changed)
     * type
     * @see Simulation
     */
    public String getSimName(){
        return mySimChooser.getMyName();
    }

    /**
     * This getter method returns the bare-minimum parameters the backend needs to begin a new simulation using the simulation
     * factory - rows, columns, simulation type
     * type
     * @see Simulation
     */
    public Map<String,String> getMyBasicParams(){
        myBasicParams.put(XMLParser.ROW_TAG_NAME, "" + myRowSpinner.getRows());
        myBasicParams.put(XMLParser.COLUMN_TAG_NAME, "" + myRowSpinner.getRows());
        myBasicParams.put(XMLParser.SIMULATION_TYPE_TAG_NAME, "" + mySimChooser.getMyName());
        return myBasicParams;
    }



}
