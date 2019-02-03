import java.util.List;

public class GOLSimulation extends Simulation {

    public GOLSimulation(int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
    }

    @Override
    public void simulate(double simulationSpeed) {

    }

    @Override
    public boolean isOver() {
        for (Cell cell: myGrid.getCells()){
            if (((GOLCell) cell).isAlive()){
                return false;
            }
        }
        return true;
    }

}
