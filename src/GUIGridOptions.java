import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
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

    private static final String BUTTON_NAME = "Grid Design...";

    public GUIGridOptions(Stage myStage, Map<String,String> initProps) {
        makeStyleButton(myStage);
        myInitialProperties = initProps;
        myBox = addButtons();
        dialogScene = new Scene(myBox, POP_UP_BOX_X, POP_UP_BOX_Y);
    }


    private void makeStyleButton(Stage primaryStage) {
        myGUIGridOptionsButton = new Button();
        myGUIGridOptionsButton.setText(BUTTON_NAME);
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
    /**
     * Sets up options pop up box to display shape, boundary, and neighbor selections
     * type
     * @see GUIGridShapeChooser
     * @see GUIGridNeighborsChooser
     * @see GUIGridBoundriesChooser
     * @return VBox root of smaller pop up window
     */
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
    /**
     * This getter method was necessary to pass back to simulation when it has to restart and change the rules for which neighbors
     * will determine the cells' next states
     * type
     * @see GUIGridNeighborsChooser
     * @return String name of the neighbors type the user wants to simulate
     */
    public String getNeighbors(){ return myNeighborsChooser.getNeighbors();}

    /**
     * This getter method was necessary to add the node that allows the user to access a list of visual/simulation rules
     * to the root of the scene in GUI
     * type
     * @see GUIGridOptions
     * @return Node options button
     */
    public Node getOptionsButton(){
        return myGUIGridOptionsButton;
    }

    /**
     * This getter method was necessary to get the most recent shape input by the user to draw the current model
     * type
     * @see GUIGridShapeChooser
     * @return String name of the shape
     */
    public String getShape(){
        return myShapeChooser.getShape();
    }
    /**
     * This getter method was necessary to get the most recent boundaries choice input by the user to draw the current cells
     * with or without an outline
     * @see GUIGridBoundriesChooser
     * @return boolean to determine whether or not outline should display
     */
    public boolean getStroke(){
        return myBoundryChooser.determineStroke( );
    }
}

