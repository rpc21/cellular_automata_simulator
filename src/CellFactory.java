import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CellFactory {
    public void setInitialStates(String simulationType, Grid grid){

    }


    private ArrayList<Location> getPossibleGridLocations() {
        ArrayList<Location> possibleLocations = new ArrayList<>();
        for (int i = 0; i<myGrid.getNumRows(); i++){
            for (int j = 0; j<myGrid.getNumCols(); j++){
                possibleLocations.add(new Location(i, j));
            }
        }
        return possibleLocations;
    }


    public void setInitialStates(String simulationType, HashMap<String, Double> parameters){
        ArrayList<Location> possibleLocations = getPossibleGridLocations();
        Collections.shuffle(possibleLocations);

    }




    public Cell generateSimulationSpecificCell(String simulationType, Location loc, int state, Grid grid,
                                                HashMap<String, Double> parameters){
        if (simulationType.equals("Game of Life")){
            return new GOLCell(loc, state, grid);
        }
        else if (simulationType.equals("Spreading Fire")){
            return new SpreadingFireCell(loc, state, grid, parameters);
        }
        else if (simulationType.equals("Percolation")){
            return new PercolationCell(loc, state, grid);
        }
        else if (simulationType.equals("Segregation")){
            return new SegregationCell(loc, state, grid, parameters);
        }
        else if (simulationType.equals("Wator")){
            return generateWatorCell(loc, grid, parameters);
        }
        return new GOLCell(loc, state, grid);
    }

    private WatorCell generateWatorCell(Location loc, Grid grid, HashMap<String, Double> parameters){
        double randomNumber = Math.random();
        if (randomNumber <= parameters.get("fishPercentage")){
            return new WatorFish(loc, grid, parameters);
        }
        else if (randomNumber <= parameters.get("fishPercentage") + parameters.get("sharkPercentage")){
            return new WatorShark(loc, grid, parameters);
        }
        else{
            return new WatorEmpty(loc);
        }
    }

    public Cell generateSimulationSpecificCell(String simulationType, Location loc, String state, Grid grid,
                                                HashMap<String, Double> parameters){
        if (simulationType.equals("Game of Life")){
            return new GOLCell(loc, GOLState.valueOf(state), grid);
        }
        else if (simulationType.equals("Spreading Fire")){
            return new SpreadingFireCell(loc, SpreadingFireState.valueOf(state), grid, parameters);
        }
        else if (simulationType.equals("Percolation")){
            return new PercolationCell(loc, PercolationState.valueOf(state), grid);
        }
        else if (simulationType.equals("Segregation")){
            return new SegregationCell(loc, SegregationState.valueOf(state), grid, parameters);
        }
        else if (simulationType.equals("Wator")) {
            return generateWatorCell(loc, grid, parameters);
        }
        return new GOLCell(loc, GOLState.valueOf(state), grid);
    }
}
