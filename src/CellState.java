import javafx.scene.paint.Color;

import java.util.List;

/**
 * The CellState interface is used to unify the state enums of the different cells to allow enums to be passed to
 * cell constructors and for their values to be held in instance variables in the cells.  All CellStates should have
 * a shortenedName (character representation), and a cell color that they should be able to provide to the tester or
 * visualization when asked.  This interface allows the specific state information of cells to be hidden from the
 * tester and visualization, only giving these other parts of the application the information they need to do their job.
 */
public interface CellState {

    /**
     * Return a character representation of the cell state
     * @return a String of length 1 representing the state of the cell
     */
    String getMyShortenedName();

    /**
     * Return the color to display representing the state of the cell
     * @return a Color representing the state of the cell
     */
    Color getMyCellColor();


    List<String> getPossibleValues();

}
