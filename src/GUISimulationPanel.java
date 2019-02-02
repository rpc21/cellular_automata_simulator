import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;

public class GUISimulationPanel {
    private static final int DEFAULT_CONTROL_OFFSET = 50;
    private static final int DEFAULT_CONTROL_SPACING = 40;
    private StackPane myStackPane;
    private Simulation mySimulation;
    public GUISimulationPanel(Simulation mySim){
        mySimulation = mySim;
        myStackPane = new StackPane();
    }
    public StackPane getGUISimulationPanel(){
        return myStackPane;
    }
    protected void addToStackPane(Control c){
        myStackPane.getChildren().addAll(c);
        int num_vertical_spacings = myStackPane.getChildren().size();
        myStackPane.setLayoutX(DEFAULT_CONTROL_OFFSET);
        c.setTranslateY(GUI.STAGE_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + num_vertical_spacings * DEFAULT_CONTROL_SPACING);
    }
    //clear stackpane method maybe?

}
