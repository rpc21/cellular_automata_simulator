import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIGridOptions extends GUIPanel {

    private Button myGUIGridOptionsButton;
    private Button setGridChanges;
    private GUIGridShapeChooser myShapeChooser;
    private GUIGridBoundriesChooser myBoundryChooser;
    private GUIGridEdgeChooser myEdgeChooser;
    private GUIGridNeighborsChooser myNeighborsChooser;
    private VBox myBox;
    public GUIGridOptions(Stage myStage) {
        makeStyleButton(myStage);
        myBox = addButtons();
    }

    private void makeStyleButton(Stage primaryStage) {
        myGUIGridOptionsButton = new Button();
        myGUIGridOptionsButton.setText("Grid Design...");
        myGUIGridOptionsButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(primaryStage);
                        myBox = addButtons();
                        Scene dialogScene = new Scene(myBox, 300, 200);
                        dialog.setScene(dialogScene);
                        dialog.show();
                    }
                });
    }
    private VBox addButtons(){
        VBox myBox = new VBox(20);
        myShapeChooser = new GUIGridShapeChooser("Rectangle");
        myBoundryChooser = new GUIGridBoundriesChooser("Lined");
        myEdgeChooser = new GUIGridEdgeChooser("Finite");
        myNeighborsChooser = new GUIGridNeighborsChooser("Adjacent");
        myBox.getChildren().addAll(myShapeChooser.getDisplay());
        myBox.getChildren().addAll(myBoundryChooser.getDisplay());
        myBox.getChildren().addAll(myEdgeChooser.getDisplay());
        myBox.getChildren().addAll(myNeighborsChooser.getDisplay());
        return myBox;
    }
    public Button getOptionsButton(){
        return myGUIGridOptionsButton;
    }

    public Color getStroke(){
        return myBoundryChooser.value();
    }
}

