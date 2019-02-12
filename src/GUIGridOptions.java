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
    private GUIGridNeighborsChooser myNeighborsChooser;


    private VBox myBox;
    private Scene dialogScene;
    private Map<String,String> myInitialProperties;



    private static final int POP_UP_BOX_X = 300;
    private static final int POP_UP_BOX_Y = 200;
    private static final int V_BOX_SIZE = 20;


    public GUIGridOptions(Stage myStage, Map<String,String> initProps) {
        makeStyleButton(myStage);
        myInitialProperties = initProps;
        myBox = addButtons();
        dialogScene = new Scene(myBox, POP_UP_BOX_X, POP_UP_BOX_Y);
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
                        dialog.setScene(dialogScene);
                        dialog.show();
                    }
                });
    }
    private VBox addButtons(){
        VBox myBox = new VBox(V_BOX_SIZE);

        myShapeChooser = new GUIGridShapeChooser(myInitialProperties.get(XMLStyler.SHAPE_TYPE_TAG_NAME));
        myBoundryChooser = new GUIGridBoundriesChooser(myInitialProperties.get(XMLStyler.OUTLINE_TAG_NAME));
        myNeighborsChooser = new GUIGridNeighborsChooser(myInitialProperties.get(XMLStyler.NEIGHBORS_TYPE_TAG_NAME));

        myBox.getChildren().addAll(myShapeChooser.getDisplay());
        myBox.getChildren().addAll(myBoundryChooser.getDisplay());
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

