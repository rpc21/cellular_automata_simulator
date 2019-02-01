
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Control;


import java.util.Arrays;
import java.util.HashSet;

public class GUIDefaultPanel {
    private StackPane myStackPane;
    private HashSet<Control> myDefaultControls;
    private static final int DEFAULT_CONTROL_OFFSET = 600;
    public static final int DEFAULT_CONTROL_SPACING = 40;

    public GUIDefaultPanel(Button play, Button step, Slider speed, ChoiceBox<String> simChooser){
        myStackPane = new StackPane();
        myDefaultControls = new HashSet<>(Arrays.asList(play,step,speed,simChooser));
        setUpStackPane();
    }

    public StackPane getGUIDefaultPanel(){
        return myStackPane;
    }

    private void setUpStackPane(){
        int iter = 0;
        for(Control c: myDefaultControls) {
            myStackPane.getChildren().addAll(c);
            myStackPane.setLayoutX(DEFAULT_CONTROL_OFFSET);
            c.setTranslateX(0.0);
            c.setTranslateY(GUI.STAGE_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + iter * DEFAULT_CONTROL_SPACING);
            iter++;
        }


    }

}
