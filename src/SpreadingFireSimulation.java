import java.util.List;

public class SpreadingFireSimulation extends Simulation {

    public SpreadingFireSimulation(int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
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
