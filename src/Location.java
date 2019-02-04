/**
 * This class defines a Location that is used to specify where a cell is in a grid.  This code is adapted from Mike
 * Scott's Wa-tor Simulation source code
 *
 * Based on code by:
 * @author Mike Scott, University of Texas
 * Original source code can be found at:
 * https://www2.cs.duke.edu/courses/compsci308/current/assign/02_cellsociety/nifty/scott-wator-world/SourceCode/Location.java
 *
 * Author:
 * @author Dima Fayyad, Ryan Culhane, Anna Darwish
 */
public class Location {
    //https://www2.cs.duke.edu/courses/spring19/compsci308/assign/02_cellsociety/nifty/scott-wator-world/SourceCode/Location.java
    private int row;
    private int col;

    /**
     * Constructs a location with given row and column coordinates.
     * @param r the row
     * @param c the column
     */
    public Location(int r, int c) {
        row = r;
        col = c;
    }

    /**
     * Gets the row coordinate.
     * @return the row of this location
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Gets the column coordinate.
     * @return the column of this location
     */
    public int getCol()
    {
        return col;
    }

    /**
     * Indicates whether some other <code>Location</code> object is "equal to"
     * this one.
     * @param other the other location to test
     * @return <code>true</code> if <code>other</code> is a
     * <code>Location</code> with the same row and column as this location;
     * <code>false</code> otherwise
     */
    public boolean equals(Object other)
    {
        if (!(other instanceof Location))
            return false;

        Location otherLoc = (Location) other;
        return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
    }

    /**
     * toString method for location
     * @return (row, col)
     */
    @Override
    public String toString() {
        return "("+row+","+col+")";
    }
}
