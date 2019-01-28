public abstract class MovableCell extends Cell {
    /** ADAPTED from WATORWORLD CODE
     * https://www2.cs.duke.edu/courses/spring19/compsci308/assign/02_cellsociety/nifty/scott-wator-world/SourceCode/Actor.java
     * Moves this actor to a new location. If there is another actor at the
     * given location, it is removed. <br />
     * Precondition: (1) This actor is contained in a grid (2)
     * <code>newLocation</code> is valid in the grid of this actor
     * @param newLocation the new location
     */
    public void swapLocations(Location newLocation) {
        if (myGrid == null)
            throw new IllegalStateException("This actor is not in a grid.");
        if (myGrid.get(myLocation) != this)
            throw new IllegalStateException(
                    "The grid contains a different actor at location "
                            + myLocation + ".");
        if (!myGrid.isValid(newLocation))
            throw new IllegalArgumentException("Location " + newLocation
                    + " is not valid.");

        if (newLocation.equals(myLocation))
            return;
        myGrid.remove(myLocation);
        Cell other = myGrid.get(newLocation);
        if (other != null)
            myGrid.put(myLocation, other);
        myLocation = newLocation;
        myGrid.put(myLocation, this);
    }
}
