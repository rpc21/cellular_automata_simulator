import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.Map;
/**
 * This class allows all subclasses to define their own necessary controls and add a label if they desire. Further, this
 * class helps organize the ordering and positions of each node. This removed a lot of boiler plate code. This class is
 * designed well because it refrains from using magic numbers (which I had to do a lot of trial testing for to make all
 * of the controls appear logically on the screen). Further, it is an abstract class and ensures subclasses will return
 * some list of parameters that would be relevant for resetting the simulation. This code is also well designed because it
 * allows for the panels to add any Control, not just Spinners to the StackPane
 */
public abstract class GUISimulationPanel extends GUIPanel{
    public static String DEFAULT_FONT_NAME = "Copperplate";

    private static final int DEFAULT_CONTROL_OFFSET = 120;
    private static final int DEFAULT_CONTROL_SPACING = 50;
    private static final int DEFAULT_LABEL_SPACING = 20;

    private static final int FONT_SIZE = 15;
    private static final int NUM_NODES_PER_COL = 8;
    private static final int NUM_CONTROL_GROUPS_PER_COL = 4;

    private StackPane myStackPane;
    private String mySimulationName;


    public GUISimulationPanel(String mySimName){
        mySimulationName = mySimName;
        myStackPane = new StackPane();

    }
    /**
     * This getter method was necessary to return the simulation panel as a StackPane to be added to the root node
     * of the scene
     */
    public StackPane getGUISimulationPanel(){
        return myStackPane;
    }
    /**
     * This method helps to organize the controls and associated labels in each panel in a uniform manner, in terms of spacing and
     * offsets within the panel and the overall window
     * @param t This is the label of the control
     * @param c This is the control that needs to accompany the Control
     */

    protected void addToStackPane(Text t, Control c){
        int num_vertical_spacings = (myStackPane.getChildren().size()/2) % NUM_CONTROL_GROUPS_PER_COL;
        int num_horiz_spacings = (int)(myStackPane.getChildren().size()/NUM_NODES_PER_COL);
        myStackPane.getChildren().addAll(t,c);
        t.setTranslateX(DEFAULT_CONTROL_OFFSET * num_horiz_spacings);
        c.setTranslateX(DEFAULT_CONTROL_OFFSET * num_horiz_spacings);
        t.setTranslateY(num_vertical_spacings * DEFAULT_CONTROL_SPACING);
        c.setTranslateY(num_vertical_spacings * DEFAULT_CONTROL_SPACING + DEFAULT_LABEL_SPACING);
    }
    /**
     * This method helps to create a uniform way
     * @param text This is the string to be displayed in the label
     * @return myText This is the Text object to be displayed on the screen
     */
    protected Text setUpLabel(String text){
        Text myText = new Text(text);
        myText.setTextAlignment(TextAlignment.LEFT);
        myText.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, FONT_SIZE));
        return myText;
    }
    /**
     * This getter method is necessary because it returns the name of the simulation to be compared
     * against whatever the user input in the GUISimulationChooser. This information is passed to configuration
     * to handle whether a new Simulation needs to be loaded from the XML files or if it only needs to reset a
     * few parameters
     */

    public String getName(){
        return mySimulationName;
    }
    /**
     * This getter method is necessary because it returns the information currently input by the user in the side panel
     * (such as cell distribution, rule paramters, etc.) for configuration to handle in generating
     * a new simulation per the user's specifications
     */

    public abstract Map<String, Double> getMyParams();

}
