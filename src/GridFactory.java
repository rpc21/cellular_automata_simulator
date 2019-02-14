public class GridFactory {

    /**
     * Generates a grid of the specified type and size rows x cols
     * @param gridType String representing grid type.  options defined in Grid.GRID_EDGE_TYPES
     * @param rows number of rows in the grid
     * @param cols number of cols in the grid
     * @return empty grid of the specified type
     */
    public Grid generateGrid(String gridType, int rows, int cols){
        if (gridType.equals(Grid.BASIC_GRID_NAME)){
            return new BasicGrid(rows, cols);
        }
        else if (gridType.equals(Grid.TOROIDAL_GRID_NAME)){
            return new WrapAroundGrid(rows, cols);
        }
        return new BasicGrid(rows, cols);
    }

    /**
     * Creates a new empty grid of the same type as the grid that is passed to it
     * @param currentGrid current grid
     * @return a new empty grid of the same type as the grid that is passed to it
     */
    public Grid generateGrid(Grid currentGrid){
        if (currentGrid instanceof WrapAroundGrid){
            return generateGrid(Grid.TOROIDAL_GRID_NAME, currentGrid.getNumRows(), currentGrid.getNumCols());
        }
        else{
            return generateGrid(Grid.BASIC_GRID_NAME, currentGrid.getNumRows(), currentGrid.getNumCols());
        }
    }

}
