import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;

public class DefaultStepButton extends DefaultControl {
    private Button myButton;
    private VisualizationGrid myVisGrid;


    private static final String MY_BUTTON_NAME = "Step";
    private static final double MY_Y_COORDINATE = 100;
    public DefaultStepButton(VisualizationGrid grid){
        super(grid,MY_Y_COORDINATE);
        myVisGrid = grid;
        makeButton();
    }
    private void makeButton(){
        myButton = new Button("Step");
        myButton.setLayoutY(2* DefaultPanel.DEFAULT_CONTROL_SPACING);
        myButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myVisGrid.step();
            }
        });
    }
    public Control getMyControl(){
        return myButton;
    }

}


