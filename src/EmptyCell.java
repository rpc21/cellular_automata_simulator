import javafx.scene.paint.Color;

public class EmptyCell extends WatorCell {

    @Override
    public void step() {}

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

    @Override
    public String toString() {
        return "E";
    }
}
