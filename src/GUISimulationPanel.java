import javafx.scene.control.Control;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GUISimulationPanel {
    private static final int DEFAULT_CONTROL_OFFSET = 0;
    private static final int DEFAULT_CONTROL_SPACING = 40;
    private static final int DEFAULT_LABEL_SPACING = 20;
    private StackPane myStackPane;

    private Simulation mySimulation;
    public GUISimulationPanel(Simulation mySim){
        mySimulation = mySim;
        myStackPane = new StackPane();
        myStackPane.setLayoutX(DEFAULT_CONTROL_OFFSET);
    }
    public StackPane getGUISimulationPanel(){
        return myStackPane;
    }

    protected void addToStackPane(Text t, Control c){
        int num_vertical_spacings = myStackPane.getChildren().size();
        myStackPane.getChildren().addAll(t,c);
        t.setTranslateX(DEFAULT_LABEL_SPACING);
        c.setTranslateX(DEFAULT_LABEL_SPACING);
        t.setTranslateY(GUI.STAGE_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + num_vertical_spacings * DEFAULT_CONTROL_SPACING);
        c.setTranslateY(GUI.STAGE_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + num_vertical_spacings * DEFAULT_CONTROL_SPACING + DEFAULT_LABEL_SPACING);
    }
    protected Text setUpLabel(String text){
        Text myText = new Text(text);
        myText.setTextAlignment(TextAlignment.LEFT);
        return myText;
    }
    protected Spinner<Integer> setUpSpinner(int min, int max, int init){
        Spinner<Integer> mySpinner = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, init);
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) valFactory).setAmountToStepBy(1);
        mySpinner.setValueFactory(valFactory);
        mySpinner.setEditable(true);
        mySpinner.setMaxWidth(80);
        return mySpinner;
    }
    //clear stackpane method maybe?

}
