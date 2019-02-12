public class GridFactory {

    public Grid generateGrid(String gridType, int rows, int cols){
        if (gridType.equals(Grid.BASIC_GRID_NAME)){
            return new BasicGrid(rows, cols);
        }
        else if (gridType.equals(Grid.TOROIDAL_GRID_NAME)){
            return new WrapAroundGrid(rows, cols);
        }
        return new BasicGrid(rows, cols);
    }

    public Grid generateGrid(Grid currentGrid){
        if (currentGrid instanceof WrapAroundGrid){
            return generateGrid(Grid.TOROIDAL_GRID_NAME, currentGrid.getNumRows(), currentGrid.getNumCols());
        }
        else{
            return generateGrid(Grid.BASIC_GRID_NAME, currentGrid.getNumRows(), currentGrid.getNumCols());
        }
    }

}
