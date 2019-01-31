import java.util.List;

public class PercolationSimulation extends Simulation {

    public PercolationSimulation(int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
    }

    @Override
    public void simulate(double simulationSpeed) {

    }

    @Override
    public boolean isOver() {
        return false;
    }

}
