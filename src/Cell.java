import javafx.scene.paint.Color;

import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract superclass to define the common behaviors of all possible types of Cells.  Simulation-specific cells need
 * to extend from this class and implement the method calculateNewState and override methods that a specific to that
 * simulation.
 */
public abstract class Cell {
    protected Grid myGrid;
    protected Grid myNextGrid;
    protected Location myLocation;
    protected Shape myShape; //TODO: Is this actually handled by visualization?
    protected CellState myCurrentState;
    protected CellState myNextState;
    protected Map<String, Double> myParameters;
    protected NeighborsDefinitions myNeighbors;


    /**
     * Dummy default constructor, DO NOT USE
     * Abstract Cell class should never actually be instantiated
     */
    public Cell() {}

    /**
     * Super constructor used for common idioms in sub-classes: used to avoid duplicate code
     * @param location is the location of the cell
     * @param initialState is the cell's initial state (needs to implement CellState interface or be null)
     * @param currentGrid is the grid that the cell is a part of
     */
    public Cell(Location location, CellState initialState, Grid currentGrid, Grid nextGrid){
        myGrid = currentGrid;
        myCurrentState = initialState;
        myNextGrid = nextGrid;
        myNextState = null;
        myLocation=location;
    }

    /**
     * Super constructor used for common cell idioms in sub-classes: used to avoid duplicate code
     * @param location is the location of the cell
     * @param initialState is the cell's initial state (needs to implement CellState interface or be null)
     * @param currentGrid is the grid that the cell is a part of
     * @param parameters is a HashMap<String, Double> that specifies the simulation specific parameters
     */
    public Cell(Location location, CellState initialState, Grid currentGrid, Grid nextGrid,
                Map<String, Double> parameters){
        this(location, initialState, currentGrid, nextGrid);
        myParameters = parameters;
    }


    /**
     * Method that takes into account the cell's current state and the current state of its neighbors to calculate
     * and store the next state.  Must be implemented by subclasses.  Called by Simulation when updating the grid
     */
    public abstract void calculateNewState();

    /**
     * Used by the visualizer to get the colored shape associated with cell
     * @return COLORED shape for visualizer
     */
    public Shape getMyShape(){
        return myShape;
    }

    /**
     * Get the cell's location
     * @return myLocation
     */
    public Location getMyLocation(){
        return myLocation;
    }

    /**
     * Set a Cell's location to the specified location
     * @param myLocation the Location to move to
     */
    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    /**
     * Set a new state of the cell
     * @param newState the new state of the cell
     */
    public void setNewState(CellState newState){
        myCurrentState = newState;
    }

//    /**
//     * implement rule of which neighbors are possible given the simulation
//     * @return
//     */
//    protected void updatePossibleNeighbors(){
//        possibleNeighbors = myGrid.getNeighbors(myLocation);
//    }

    /**
     * Changes the cell to the next state
     */
    public void updateState(){
        myCurrentState = myNextState;
        myNextState = null;
    }

    /**
     * Getter for the current state of the cell
     * @return the current state of the cell
     */
    public String getMyCurrentState() {
        return myCurrentState.toString();
    }

    public CellState getCurrentCellState(){
        return myCurrentState;
    }

    //TODO: Determine if we need this
    public void setMyCurrentState(CellState myCurrentState) {
        this.myCurrentState = myCurrentState;
    }

    //TODO: Determine if we need this
    public CellState getMyNextState() {
        return myNextState;
    }

    //TODO: Determine if we need this
    public void setMyNextState(CellState myNextState) {
        this.myNextState = myNextState;
    }

    /**
     * Setter for my Grid if you want to change the grid to an entirely new grid
     * @param myGrid new Grid of Cell objects
     */
    public void setMyGrid(Grid myGrid) {
        this.myGrid = myGrid;
    }

    /**
     * Getter for myGrid
     * @return myGrid as an object that implements the grid interface
     */
    public Grid getMyGrid() {
        return myGrid;
    }

    /**
     * Setter for myParameters. Will update the the parameters specified in 'updates' in the myParameters map of the
     * cell
     * @param updates is a map of parameters to be changed and their new values
     */
    public void setMyParameters(Map<String, Double> updates) {

        for (String parameter : updates.keySet()){
            myParameters.put(parameter, updates.get(parameter));
        }
    }

    /**
     * Returns the boolean value of the empty instance variable
     * Cells that are always empty or always full should override this method to always return true or false
     * @return if this cell is empty or not
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * Get the color that the cell should display which depends on cell type and state
     * Needs to be implemented by simulation specific subclasses
     * @return color to display as a representation of the cell in the visualization
     */
    public Color getMyColor(){
        return myCurrentState.getMyCellColor();
    }

    /**
     * Get the string of length one representation of the cell
     * @return a string of length one that represents the state of the cell
     */
    @Override
    public String toString() {
        return myCurrentState.getMyShortenedName();
    }


    public void setMyNeighbors(NeighborsDefinitions neighbors) {
        if (neighbors == NeighborsDefinitions.HEXAGON){
            handleHexagons();
        }
        else if (neighbors == NeighborsDefinitions.TRIANGLE_12_POINT_UP){
            handleTriangles();
        }
        else{
            myNeighbors = neighbors;
        }
    }

    private void handleTriangles() {
        if ((myLocation.getRow() + myLocation.getCol()) % 2 == 0){
            myNeighbors = NeighborsDefinitions.TRIANGLE_12_POINT_UP;
        }
        else{
            myNeighbors = NeighborsDefinitions.TRIANGLE_12_POINT_DOWN;
        }
    }

    private void handleHexagons() {
        if (myLocation.getRow() % 2 == 1){
            myNeighbors = NeighborsDefinitions.HEXAGON;
        }
        else{
            myNeighbors = NeighborsDefinitions.FLIPPED_HEXAGON;
        }
    }

    public boolean containsAgent(){
        return false;
    }
}
