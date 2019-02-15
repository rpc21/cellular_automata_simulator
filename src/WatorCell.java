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

    /**
     * Default constructor
     */
    public WatorCell(){
        super();
    }

    /**
     * Constructor for WatorCells
     * @param location location
     * @param currentGrid current grid
     * @param nextGrid next grid
     * @param parameters parameters specific to Wator simulation
     */
    public WatorCell(Location location, Grid currentGrid, Grid nextGrid, Map<String, Double> parameters){
        myLocation = location;
        myGrid = currentGrid;
        myNextGrid = nextGrid;
        myParameters = parameters;
    }

    /**
     * Should be overridden by subclass
     */
    @Override
    public void calculateNewState() {}


    /**
     * Allows wator cells to move locations
     * @param loc next location
     */
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

    /**
     * Method in which wator cells should implement their rules about how to advance from one iteration to the next
     */
    public abstract void step();

    /**
     * Update the neighbors of the Wator Cells
     * @param styleProperties style properties
     */
    public void updateNeighbors(Map<String, String> styleProperties){
        for (Cell cell : myGrid.getCells()){
            cell.setMyNeighbors(NeighborsDefinitions.valueOf(styleProperties.getOrDefault(XMLStyler.NEIGHBORS_TYPE_TAG_NAME,
                    NeighborsDefinitions.ADJACENT.toString())));
        }
    }
}
