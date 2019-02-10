import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Map;

public class GUIGridOptions extends GUIPanel {

    private Button myGUIGridOptionsButton;
    private GUIGridShapeChooser myShapeChooser;
    private GUIGridBoundriesChooser myBoundryChooser;
    private GUIGridEdgeChooser myEdgeChooser;
    private GUIGridNeighborsChooser myNeighborsChooser;
    private VBox myBox;
    private Map<String,String> myInitialProperties;
    public GUIGridOptions(Stage myStage, Map<String,String> initProps) {
        makeStyleButton(myStage);
        myInitialProperties = initProps;
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
        myShapeChooser = new GUIGridShapeChooser(myInitialProperties.get(XMLStyler.SHAPE_TYPE_TAG_NAME));
        myBoundryChooser = new GUIGridBoundriesChooser(myInitialProperties.get(XMLStyler.SHAPE_TYPE_TAG_NAME));
        myEdgeChooser = new GUIGridEdgeChooser("Finite");
        myNeighborsChooser = new GUIGridNeighborsChooser(myInitialProperties.get(XMLStyler.NEIGHBORS_TYPE_TAG_NAME));
        myBox.getChildren().addAll(myShapeChooser.getDisplay());
        myBox.getChildren().addAll(myBoundryChooser.getDisplay());
        myBox.getChildren().addAll(myEdgeChooser.getDisplay());
        myBox.getChildren().addAll(myNeighborsChooser.getDisplay());
        return myBox;
    }
    public String getNeighbors(){ return myNeighborsChooser.getNeighbors();}
    public Button getOptionsButton(){
        return myGUIGridOptionsButton;
    }

    public String getShape(){
        return myShapeChooser.getShape();
    }

    public boolean getStroke(){
        return myBoundryChooser.determineStroke( );
    }
}

