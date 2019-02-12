
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public abstract class GUIControlManager extends GUIPanel {

    protected static String DEFAULT_FONT_NAME = "Copperplate";
    protected static int CONTROL_TEXT_SIZE = 15;

    public abstract List<Node> getDisplay();

    protected void setUpButton(Button b, String name, EventHandler<MouseEvent> value){
        b.setText(name);
        b.setFont(Font.font(DEFAULT_FONT_NAME, CONTROL_TEXT_SIZE));
        b.setOnMouseClicked(value);
    }

    protected void setUpSlider(Slider s, Double initVal,Double lowVal, Double maxVal, EventHandler<MouseEvent> value){
        s.setValue(initVal);
        s.setMin(lowVal);
        s.setMax(maxVal);
        s.setOnMouseClicked(value);
    }
    protected void setUpLabel(Text t){
        t.setFont(Font.font(DEFAULT_FONT_NAME, CONTROL_TEXT_SIZE));
    }



}
