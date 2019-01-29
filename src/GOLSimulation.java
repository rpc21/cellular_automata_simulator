import java.util.List;

public class GOLSimulation extends Simulation {

    public GOLSimulation(int rows, int cols){
        setMyGrid(new BasicGrid(rows, cols));
    }


    @Override
    public void simulate(double simulationSpeed) {

    }

    @Override
    public boolean checkIfDone() {
        return false;
    }
}
