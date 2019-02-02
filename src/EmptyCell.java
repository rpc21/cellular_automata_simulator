import javafx.scene.paint.Color;

public class EmptyCell extends Cell {

    public EmptyCell(Location loc){
        myLocation = loc;
        myCurrentState = null;
        myNextState = null;
        myParameters = null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void calculateNewState() {
        myNextState = null;
    }

    @Override
    public Color getMyColor() {
        return Color.WHITE;
    }
}
