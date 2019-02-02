import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GUISimulationPanel {
    private static final int DEFAULT_CONTROL_OFFSET = 0;
    private static final int DEFAULT_CONTROL_SPACING = 40;
    private static final int DEFAULT_LABEL_SPACING = 20;
    private StackPane myStackPane;
    private Simulation mySimulation;
    public GUISimulationPanel(Simulation mySim){
        mySimulation = mySim;
        myStackPane = new StackPane();
        myStackPane.setLayoutX(DEFAULT_CONTROL_OFFSET);
    }
    public StackPane getGUISimulationPanel(){
        return myStackPane;
    }
    protected void addToStackPane(Control c){
        myStackPane.getChildren().addAll(c);
        int num_vertical_spacings = myStackPane.getChildren().size();
        c.setTranslateY(GUI.STAGE_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + num_vertical_spacings * DEFAULT_CONTROL_SPACING);
    }
    protected void addToStackPane(Text t, Control c){
        int num_vertical_spacings = myStackPane.getChildren().size();
        myStackPane.getChildren().addAll(t,c);
        t.setTranslateX(DEFAULT_LABEL_SPACING);
        c.setTranslateX(DEFAULT_LABEL_SPACING);
        t.setTranslateY(GUI.STAGE_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + num_vertical_spacings * DEFAULT_CONTROL_SPACING);
        c.setTranslateY(GUI.STAGE_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + num_vertical_spacings * DEFAULT_CONTROL_SPACING + DEFAULT_LABEL_SPACING);
    }
    //clear stackpane method maybe?

}
