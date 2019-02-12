
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;

public class GUISimulationPanel extends GUIPanel{
    public static String DEFAULT_FONT_NAME = "Copperplate";

    private static final int DEFAULT_CONTROL_OFFSET = 120;
    private static final int DEFAULT_CONTROL_SPACING = 50;
    private static final int DEFAULT_LABEL_SPACING = 20;

    private static final int FONT_SIZE = 15;
    private static final int NUM_NODES_PER_COL = 8;
    private static final int NUM_CONTROL_GROUPS_PER_COL = 4;

    private HashMap<String,Double> myParamsMap = new HashMap<String,Double>();
    private StackPane myStackPane;
    private String mySimulationName;

    public GUISimulationPanel(String mySimName){
        mySimulationName = mySimName;
        myStackPane = new StackPane();

    }
    public StackPane getGUISimulationPanel(){
        return myStackPane;
    }

    protected void addToStackPane(Text t, Control c){
        int num_vertical_spacings = (myStackPane.getChildren().size()/2) % NUM_CONTROL_GROUPS_PER_COL;
        int num_horiz_spacings = (int)(myStackPane.getChildren().size()/NUM_NODES_PER_COL);
        myStackPane.getChildren().addAll(t,c);
        t.setTranslateX(DEFAULT_CONTROL_OFFSET * num_horiz_spacings);
        c.setTranslateX(DEFAULT_CONTROL_OFFSET * num_horiz_spacings);
        t.setTranslateY(num_vertical_spacings * DEFAULT_CONTROL_SPACING);
        c.setTranslateY(num_vertical_spacings * DEFAULT_CONTROL_SPACING + DEFAULT_LABEL_SPACING);
    }

    protected Text setUpLabel(String text){
        Text myText = new Text(text);
        myText.setTextAlignment(TextAlignment.LEFT);
        myText.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, FONT_SIZE));
        return myText;
    }


    public String getName(){
        return mySimulationName;
    }


    public HashMap<String,Double> getMyParams(){
        return myParamsMap;
    }



}
