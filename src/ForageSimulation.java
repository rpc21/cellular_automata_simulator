import java.util.HashMap;
import java.util.List;

public class ForageSimulation extends Simulation {

    public ForageSimulation(HashMap<String, Double> params, int rows, int cols){
        super(params, rows, cols);
    }

    @Override
    public boolean isOver() {
        return false;
    }

    @Override
    public String getMyName() {
        return null;
    }

    @Override
    public List<String> getPercentageFields() {
        return null;
    }
}
