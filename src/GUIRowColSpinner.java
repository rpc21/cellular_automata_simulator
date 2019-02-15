import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GUIRowColSpinner extends GUIControlManager {

    private static final int MIN_SIZE = 3;
    private static final int MAX_SIZE = 100;
    private static final String NAME = "Rows x Cols";
    private Spinner<Integer> myRowSpinner;
    private Text myRowSpinnerLabel = new Text(NAME);

    public GUIRowColSpinner(int rows){
        myRowSpinner = setUpSpinner(MIN_SIZE,MAX_SIZE, rows);
        setUpLabel(myRowSpinnerLabel);
    }
    /**
     * This getter method was necessary to get the current row value the user had input in case the user restarts the
     * simulation
     * type
     * @return int number of rows
     */
    public int getRows(){
        return myRowSpinner.getValue();
    }
    /**
     * This getter method was necessary to add the node that allows the user to access the row x col spinner
     * @see Node
     * @return myList which is a list of nodes necessary for the user to understand and change values for the grid's number
     * of rows and columns
     */
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myRowSpinnerLabel);
        myList.add(myRowSpinner);
        return myList;
    }

}
