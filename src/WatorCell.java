import javafx.scene.paint.Color;

import java.util.Map;

public abstract class WatorCell extends Cell {

    protected int turnsUntilCanBreed;

    public WatorCell(){
        super();
    }

    public WatorCell(Location loc, Grid grid, Map<String, Double> parameters){
        myLocation = loc;
        myGrid = grid;
        myParameters = parameters;
        turnsUntilCanBreed = (int) (parameters.get("breedTime") + Math.random() * 3);
    }

    /**
     * Should be overridden by subclass
     */
    @Override
    public void calculateNewState() {}


    /**
     * Needs to be overridden by subclasses
     * @return
     */
    @Override
    public Color getMyColor() {
        return Color.AQUAMARINE;
    }

    public void moveTo(Location loc){
        myGrid.put(loc, this);
    }

    /**
     * Check to see if this sea creature can be eaten by other
     * sea creatures
     * @return false. Expected to be overriden by child classes
     */
    public boolean isEdible(){
        return false;
    }

    public abstract void step();
}
