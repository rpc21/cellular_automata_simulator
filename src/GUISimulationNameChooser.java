
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUISimulationNameChooser extends GUIControlManager{

    private ChoiceBox<Object> myChooser = new ChoiceBox<>();
    private List<Object> possibleSims = Arrays.asList(Simulation.GOL_SIMULATION_NAME, Simulation.SPREADING_FIRE_SIMULATION_NAME,
            Simulation.PERCOLATION_SIMULATION_NAME, Simulation.SEGREGATION_SIMULATION_NAME, Simulation.WATOR_SIMULATION_NAME,
            Simulation.FORAGE_SIMULATION_NAME, Simulation.SUGAR_SIMULATION_NAME);
    public GUISimulationNameChooser(String initialSim){
        super.setUpChoiceBox(myChooser, initialSim, possibleSims);
    }
    public String getMyName(){
        return myChooser.getValue().toString();
    }
    public List<Node> getDisplay(){
        List<Node> myList = new ArrayList<>();
        myList.add(myChooser);
        return myList;
    }
}