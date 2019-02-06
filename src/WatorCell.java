import javafx.scene.paint.Color;

import java.util.Map;

/**
 * This class defines a WatorCell that is has WatorShark and WatorFish subclasses.  This code is adapted from Mike
 * Scott's Wa-tor Simulation source code for the Actor class modified to be in line with our Abstract Cell class
 *
 * Based on code by:
 * @author Mike Scott, University of Texas
 * Original source code can be found at:
 * https://www2.cs.duke.edu/courses/compsci308/current/assign/02_cellsociety/nifty/scott-wator-world/SourceCode/Actor.java
 *
 * Author:
 * @author Dima Fayyad, Ryan Culhane, Anna Darwish
 */
public abstract class WatorCell extends Cell {

    protected int turnsUntilCanBreed;

    public WatorCell(){
        super();
    }

    public WatorCell(Location location, Grid currentGrid, Grid nextGrid, Map<String, Double> parameters){
        myLocation = location;
        myGrid = currentGrid;
        myNextGrid = nextGrid;
        myParameters = parameters;
//        turnsUntilCanBreed = (int) (parameters.get("breedTime") + Math.random() * 3);
    }

    /**
     * Should be overridden by subclass
     */
    @Override
    public void calculateNewState() {}


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
