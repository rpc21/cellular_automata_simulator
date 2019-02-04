import javafx.scene.control.Control;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;

public class GUISimulationPanel extends GUIPanel{
    public static String DEFAULT_FONT_NAME = "Copperplate";


    private static final int DEFAULT_CONTROL_OFFSET = 0;
    private static final int DEFAULT_CONTROL_SPACING = 40;
    private static final int DEFAULT_LABEL_SPACING = 20;

    private HashMap<String,Double> myParamsMap = new HashMap<String,Double>();
    private StackPane myStackPane;
    private Simulation mySimulation;

    public GUISimulationPanel(Simulation mySim){
        mySimulation = mySim;
        myStackPane = new StackPane();
        myStackPane.setLayoutX(DEFAULT_CONTROL_OFFSET);
//        for (String:mySim.getParams().getKeys)

    }
    public StackPane getGUISimulationPanel(){
        return myStackPane;
    }

    protected void addToStackPane(Text t, Control c){
        int num_vertical_spacings;
        if (myStackPane.getChildren().size() % 2 == 0)
            num_vertical_spacings = myStackPane.getChildren().size();
        else
            num_vertical_spacings = myStackPane.getChildren().size() - 1;
        myStackPane.getChildren().addAll(t,c);
        t.setTranslateX(DEFAULT_LABEL_SPACING);
        c.setTranslateX(DEFAULT_LABEL_SPACING);
        t.setTranslateY(GUI.STAGE_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + num_vertical_spacings * DEFAULT_CONTROL_SPACING);
        c.setTranslateY(GUI.STAGE_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + num_vertical_spacings * DEFAULT_CONTROL_SPACING + DEFAULT_LABEL_SPACING);
    }
    protected void addToStackPane(Text label, Text val, Control c){
        addToStackPane(label,c);
        val.setTranslateX(DEFAULT_LABEL_SPACING + label.getBoundsInLocal().getWidth()/1.5);
        val.setTranslateY(label.getTranslateY());
        myStackPane.getChildren().addAll(val);
    }
    protected Text setUpLabel(String text){
        Text myText = new Text(text);
        myText.setTextAlignment(TextAlignment.LEFT);
        myText.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, 15));
        return myText;
    }

    protected void updateMyParams(String k, Double val){
        myParamsMap.put(k,val);
    }
    public HashMap<String,Double> getMyParams(){
        return myParamsMap;
    }


}
