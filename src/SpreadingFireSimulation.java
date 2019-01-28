import java.util.List;

public class SpreadingFireSimulation extends Simulation {

    public SpreadingFireSimulation(int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
    }

    public void setInitialStates(int[][] initialStates){
        for (int i = 0; i < getMyGrid().getNumRows(); i++){
            for (int j = 0; j < getMyGrid().getNumCols(); j++){
                Location thisLocation = new Location(i, j);
                SpreadingFireCell newCell = new SpreadingFireCell(thisLocation, initialStates[i][j], getMyGrid());
                System.out.println(newCell + " to be inserted at "+ i + ", "+j);
                System.out.println(newCell.getMyLocation().getRow()+", "+newCell.getMyLocation().getCol());
                getMyGrid().put(newCell.getMyLocation(), newCell);

            }
        }
        getMyGrid().printGrid();
        System.out.println("Initial states set");
    }

    @Override
    public void simulate(double simulationSpeed) {

    }

    @Override
    public void updateNewParams(List<Integer> paramList) {

    }

    @Override
    public boolean checkIfDone() {
        return false;
    }
}
