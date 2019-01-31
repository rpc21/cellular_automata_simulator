import javafx.scene.layout.StackPane;
import javafx.scene.control.Control;
public abstract class DefaultControl {
    private double myXCoor = 0.0;
    private double myYCoor;
    private VisualizationGrid myVisGrid;

    public DefaultControl(VisualizationGrid grid, double y){
        myVisGrid = grid;
        myYCoor = y;
    }

    public abstract Control getMyControl();

    public double getMyYCoor(){
        return myYCoor;
    };
    public double getMyXCoor(){
        return myXCoor;
    }

}
