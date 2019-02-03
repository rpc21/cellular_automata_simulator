public class WatorSimulation extends Simulation {


    public WatorSimulation(int rows, int cols){
        setMyGrid(new WrapAroundGrid(rows, cols));
    }

    @Override
    public void simulate(double simulationSpeed) {

    }

    @Override
    public boolean isOver() {
        return myGrid.getOccupiedLocations().size() == 0;
    }

    @Override
    public void updateGrid() {
        for (Location loc : myGrid.getOccupiedLocations()){
           ((WatorCell) myGrid.get(loc)).step();
        }
    }
}
