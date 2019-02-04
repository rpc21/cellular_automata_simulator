
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIDefaultPanel extends GUIPanel {
    private Button myPlayButton;
    private Button myStepButton;
    private Slider mySpeedSlider;
    private Text mySpeedLabel;
    private ChoiceBox<String> myChoiceBox;
    private StackPane myStackPane = new StackPane();
    private List<Node> myDefaultControls = new ArrayList<>();
    private Spinner<Integer> myRowSpinner;
    private Text myRowsLabel = new Text("Rows: ");
    private Spinner<Integer> myColSpinner;
    private Text myColsLabel = new Text("Cols: ");
    private Button myResetButton;

    private GUIGridStep myStepFunction;
    private Timeline myAnimation;
    private KeyFrame myFrame;
    private String myName;
    private int myRows;
    private int myCols;
    private HashMap<String,String> myBasicParams = new HashMap<>();

    private static final double STACKPANE_OFFSET = GUI.STAGE_SIZE/2 + GUIGrid.GUI_GRID_SIZE/1.5;
    private static final int DEFAULT_CONTROL_OFFSET = 10;
    private static final int FRAMES_PER_SECOND = 60;
    public static final int DEFAULT_CONTROL_SPACING = 40;

    public GUIDefaultPanel(GUIGridStep step, Timeline timeline, KeyFrame frame, String simName, int rows, int cols){
        myStepFunction = step;
        myAnimation = timeline;
        myFrame = frame;
        myName = simName;
        myRows = rows;
        myCols = cols;
        populateMap();
        makeControls();
        setUpStackPane();
    }

    private void populateMap(){
        myBasicParams.put(XMLParser.COLUMN_TAG_VIS, "" + myRows);
        myBasicParams.put(XMLParser.ROW_TAG_VIS, "" + myCols);
        myBasicParams.put(XMLParser.SIMULATION_TYPE_TAG_NAME, "" + myName);
    }

    public StackPane getGUIDefaultPanel(){
        return myStackPane;
    }

    private void makeControls(){
        makePlayButton();
        makeStepButton();
        makeSpeedSlider();
        makeRowsAndColsSetters(myRows,myCols);
        makeSimulationDropDownMenu();
        setUpResetButton();
    }

    private void makePlayButton() {
        myPlayButton = new Button("Play");
        myPlayButton.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, 15));
        myPlayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (myAnimation.getStatus() == Animation.Status.RUNNING)
                    myAnimation.pause();
                else {
                    myAnimation.play();
                }
            }
        });
        myDefaultControls.add(myPlayButton);
    }
    private void makeStepButton(){
        myStepButton = new Button("Step");
        myStepButton.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, 15));
        myStepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myAnimation.pause();
                myStepFunction.guiGridStep();
            }
        });
        myDefaultControls.add(myStepButton);
    }

    private void makeSpeedSlider(){
        mySpeedLabel = new Text("Animation Speed");
        mySpeedLabel.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, 15));
        mySpeedSlider = new Slider(-17000,-3000,-10000);
        mySpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                mySpeedSlider.setValue(mySpeedSlider.getValue());
                resetAnimation(mySpeedSlider.getValue() * -1/FRAMES_PER_SECOND,myAnimation.getStatus() == Animation.Status.RUNNING);
            }
        });
        myDefaultControls.add(mySpeedLabel);
        myDefaultControls.add(mySpeedSlider);
    }

    private void resetAnimation(double duration, boolean isPlaying){
        myAnimation.stop();
        myFrame = new KeyFrame(Duration.millis(duration), e -> myStepFunction.guiGridStep());
        myAnimation.getKeyFrames().setAll(myFrame);
    }

    private void makeSimulationDropDownMenu(){
        myChoiceBox = new ChoiceBox<>();
        myChoiceBox.getItems().addAll(Simulation.GOL_SIMULATION_NAME, Simulation.SPREADING_FIRE_SIMULATION_NAME,
                Simulation.PERCOLATION_SIMULATION_NAME, Simulation.SEGREGATION_SIMULATION_NAME, Simulation.WATOR_SIMULATION_NAME);
        myChoiceBox.setValue(myName);
        myChoiceBox.setStyle("-fx-font: 15px \"Copperplate\";");
        myChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myChoiceBox.setValue(myChoiceBox.getItems().get((Integer) number2));
                System.out.println(myChoiceBox.getValue());
            }
        });
        myDefaultControls.add(myChoiceBox);

    }
    public String getSimName(){
        return myChoiceBox.getValue();
    }

    public HashMap<String,String> getMyBasicParams(){
        System.out.println(myRowSpinner.getValue() + " " + myColSpinner.getValue());
        myBasicParams.put(XMLParser.ROW_TAG_VIS, "" + myRowSpinner.getValue());
        myBasicParams.put(XMLParser.COLUMN_TAG_VIS, "" + myColSpinner.getValue());
        myBasicParams.put(XMLParser.SIMULATION_TYPE_TAG_NAME, "" + myChoiceBox.getValue());
        return myBasicParams;
    }
    private void setUpStackPane(){
        int iter = 0;
        for(Node c: myDefaultControls) {
            myStackPane.getChildren().addAll(c);
            myStackPane.setLayoutX(STACKPANE_OFFSET);
            c.setTranslateX(DEFAULT_CONTROL_OFFSET);
            c.setTranslateY(GUI.STAGE_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + iter * DEFAULT_CONTROL_SPACING);
            iter++;
        }
    }
    public int getMyRows(){
        return myRowSpinner.getValue();
    }
    private void makeRowsAndColsSetters(int rows, int cols){
        myRowSpinner = setUpSpinner(3,50, rows);
        myRowSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myBasicParams.remove(XMLParser.ROW_TAG_VIS);
                myBasicParams.put(XMLParser.ROW_TAG_VIS,"" + myRowSpinner.getValue());
                for (String a: myBasicParams.keySet())
                    System.out.println(a + " " + myBasicParams.get(a));
                updateCurrentMap(XMLParser.ROW_TAG_VIS,"" + myRowSpinner.getValue());
                setNeedsToReset();
            }
        });
        myColSpinner = setUpSpinner(3,50,cols);
        myColSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                myBasicParams.remove(XMLParser.COLUMN_TAG_VIS);
                myBasicParams.put(XMLParser.COLUMN_TAG_VIS,"" + myColSpinner.getValue());

                setNeedsToReset();
            }
        });
        myRowsLabel.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, 15));
        myColsLabel.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, 15));
        myDefaultControls.add(myRowsLabel);
        myDefaultControls.add(myRowSpinner);
        myDefaultControls.add(myColsLabel);
        myDefaultControls.add(myColSpinner);
    }
    private void setUpResetButton(){
        myResetButton = new Button("Reset");
        myResetButton.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, 15));
        myResetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setResetClicked();
                myStepFunction.guiGridStep();
            }
        });
        myDefaultControls.add(myResetButton);
    }

    private void updateCurrentMap(String a, String b){
        myBasicParams.put(a,b);
    }



}
