import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIDefaultPanel extends GUIPanel {
    private StackPane myStackPane = new StackPane();
    private List<Node> myDefaultControls = new ArrayList<>();
    private HashMap<String,String> myBasicParams = new HashMap<>();

    GUIPlayButton myPlayButton;
    GUIStepButton myStepButton;
    GUIAddSimulationButton myAddSimButton;
    GUISpeedSlider mySpeedSlider;
    GUIRowColSpinner myRowSpinner;
    GUISimulationNameChooser mySimChooser;
    GUIResetButton myResetButton;

    private static final double STACKPANE_OFFSET = GUI.STAGE_SIZE/2 + GUIGrid.GUI_GRID_SIZE/2 + 30;
    private static final int DEFAULT_CONTROL_OFFSET = 10;
    public static final int DEFAULT_CONTROL_SPACING = 40;

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

    private void populateMap(int r, int c, String myName){
        myBasicParams.put(XMLParser.COLUMN_TAG_NAME, "" + r);
        myBasicParams.put(XMLParser.ROW_TAG_NAME, "" + c);
        myBasicParams.put(XMLParser.SIMULATION_TYPE_TAG_NAME, "" + myName);
    }

    public StackPane getGUIDefaultPanel(){
        return myStackPane;
    }


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

    public String getSimName(){
        return mySimChooser.getMyName();
    }

    public HashMap<String,String> getMyBasicParams(){
        myBasicParams.put(XMLParser.ROW_TAG_NAME, "" + myRowSpinner.getRows());
        myBasicParams.put(XMLParser.COLUMN_TAG_NAME, "" + myRowSpinner.getRows());
        myBasicParams.put(XMLParser.SIMULATION_TYPE_TAG_NAME, "" + mySimChooser.getMyName());
        return myBasicParams;
    }



}
