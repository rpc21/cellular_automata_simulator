
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;


public class DefaultPlayButton extends DefaultControl {
    private Button myButton;
    private VisualizationGrid myVisGrid;
    private boolean isPlaying;

    private static final String MY_BUTTON_NAME = "Play";
    private static final double MY_Y_COORDINATE = 70;
    public DefaultPlayButton(VisualizationGrid myGrid){
        super(myGrid, MY_Y_COORDINATE);
        isPlaying = false;
        myVisGrid = myGrid;
        makeButton();
    }
    private void makeButton(){
        myButton = new Button(MY_BUTTON_NAME);
        myButton.setLayoutY(1* DefaultPanel.DEFAULT_CONTROL_SPACING);
        myButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isPlaying)
                    myVisGrid.getAnimation().pause();
                else {
                    myVisGrid.getAnimation().play();
                }
                isPlaying = !isPlaying;
            }
        });
    }
    public Control getMyControl(){
        return myButton;
    }



}
