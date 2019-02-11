import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;
import java.util.List;

public class GUISimulationPanel extends GUIPanel{
    public static String DEFAULT_FONT_NAME = "Copperplate";


    private static final int DEFAULT_CONTROL_OFFSET = 120;
    private static final int DEFAULT_CONTROL_SPACING = 50;
    private static final int DEFAULT_LABEL_SPACING = 20;

    private HashMap<String,Double> myParamsMap = new HashMap<String,Double>();
    private StackPane myStackPane;
    private Simulation mySimulation;

    public GUISimulationPanel(Simulation mySim){
        mySimulation = mySim;
        myStackPane = new StackPane();

    }
    public StackPane getGUISimulationPanel(){
        return myStackPane;
    }

    protected void addToStackPane(Text t, Control c){
        int num_vertical_spacings = (myStackPane.getChildren().size()/2) % 4;
        int num_horiz_spacings = (int)(myStackPane.getChildren().size()/8);
        myStackPane.getChildren().addAll(t,c);
        t.setTranslateX(DEFAULT_CONTROL_OFFSET * num_horiz_spacings);
        c.setTranslateX(DEFAULT_CONTROL_OFFSET * num_horiz_spacings);
        t.setTranslateY(num_vertical_spacings * DEFAULT_CONTROL_SPACING);
        c.setTranslateY(num_vertical_spacings * DEFAULT_CONTROL_SPACING + DEFAULT_LABEL_SPACING);
    }

    protected Text setUpLabel(String text){
        Text myText = new Text(text);
        myText.setTextAlignment(TextAlignment.LEFT);
        myText.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, 15));
        return myText;
    }

    protected double getCurrentCount(Color col){
        int count = 0;
        for (Cell c: mySimulation.getMyGrid().getCells())
            if (c.getMyColor().equals(col))
                count++;
        return 1.0 * count/mySimulation.getMyGrid().getCells().size();
    }
    public String getName(){
        return mySimulation.getMyName();
    }


    public HashMap<String,Double> getMyParams(){
        return myParamsMap;
    }



}
