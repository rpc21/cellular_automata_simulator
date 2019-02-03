
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Control;
import javafx.scene.text.Text;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class GUIDefaultPanel {
    private StackPane myStackPane;
    private List<Node> myDefaultControls;
    private static final int STACKPANE_OFFSET = 600;
    private static final int DEFAULT_CONTROL_OFFSET = 10;
    public static final int DEFAULT_CONTROL_SPACING = 40;

    public GUIDefaultPanel(Button play, Button step, Text txt, Slider speed, ChoiceBox<String> simChooser){
        myStackPane = new StackPane();
        myDefaultControls = new ArrayList<>();
        myDefaultControls.add(play);
        myDefaultControls.add(step);
        myDefaultControls.add(txt);
        myDefaultControls.add(speed);
        myDefaultControls.add(simChooser);
        setUpStackPane();
    }

    public StackPane getGUIDefaultPanel(){
        return myStackPane;
    }

    private void setUpStackPane(){
        int iter = 0;
        for(Node c: myDefaultControls) {
            myStackPane.getChildren().addAll(c);
            myStackPane.setLayoutX(STACKPANE_OFFSET);
            c.setTranslateX(DEFAULT_CONTROL_OFFSET);
            c.setTranslateY(GUI.STAGE_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + iter * DEFAULT_CONTROL_SPACING);
            iter++;
        }
    }

}
