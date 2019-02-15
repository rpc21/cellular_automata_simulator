import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Collection;

public abstract class GUIControlManager extends GUIPanel {

    protected static String DEFAULT_FONT_NAME = "Copperplate";
    protected static int CONTROL_TEXT_SIZE = 15;

    public abstract Collection<Node> getDisplay();

    /**
     * Sets up a Button based upon standardized font and font size, along with the buttons associated mouse-click event
     * @see EventHandler<MouseEvent>
     */
    protected void setUpButton(Button b, String name, EventHandler<MouseEvent> value){
        b.setText(name);
        b.setFont(Font.font(DEFAULT_FONT_NAME, CONTROL_TEXT_SIZE));
        b.setOnMouseClicked(value);
    }
    /**
     * Sets up a Slider based upon specific range of values and on-mouse event
     * @see EventHandler<MouseEvent>
     */
    protected void setUpSlider(Slider s, Double initVal,Double lowVal, Double maxVal, EventHandler<MouseEvent> value){
        s.setValue(initVal);
        s.setMin(lowVal);
        s.setMax(maxVal);
        s.setOnMouseClicked(value);
    }
    /**
     * Sets up Text label for some control item
     * @see EventHandler<MouseEvent>
     */
    protected void setUpLabel(Text t){
        t.setFont(Font.font(DEFAULT_FONT_NAME, CONTROL_TEXT_SIZE));
    }



}
