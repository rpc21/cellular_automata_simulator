
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class GUIControlManager extends GUIPanel {

    private static String DEFAULT_FONT_NAME = "Copperplate";
    private static int CONTROL_TEXT_SIZE = 15;

    public abstract List<Node> getDisplay();
    protected void setUpButton(Button b, String name, EventHandler<MouseEvent> value){
        b.setText(name);
        b.setFont(Font.font(DEFAULT_FONT_NAME, CONTROL_TEXT_SIZE));
        b.setOnMouseClicked(value);
    }
    protected void setUpChoiceBox(ChoiceBox<Object> cb, Object initVal, List<Object> possibleChoices){
        cb.getItems().addAll(possibleChoices);
        cb.setValue(initVal);
        cb.setStyle("-fx-font: " + CONTROL_TEXT_SIZE + "px \"" + DEFAULT_FONT_NAME + "\";");
        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                cb.setValue(cb.getItems().get((Integer)number2));
            }
        });
    }
    protected void setUpSlider(Slider s, Double initVal,Double lowVal, Double maxVal, EventHandler<MouseEvent> value){
        s.setValue(initVal);
        s.setMin(lowVal);
        s.setMax(maxVal);
        s.setOnMouseDragReleased(value);
    }
    protected void setUpLabel(Text t){
        t.setFont(Font.font(DEFAULT_FONT_NAME, CONTROL_TEXT_SIZE));
    }



}
