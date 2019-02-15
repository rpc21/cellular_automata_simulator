import java.util.HashMap;
import java.util.Map;

public class GUIBlankPanel extends GUISimulationPanel{
    Map<String,Double> myMap = new HashMap<>();

    public GUIBlankPanel(String mySimName, Map<String,Double> initParams) {
        super(mySimName);
    }

    /**
     * Returns an empty map if the simulation has no simulation-specific parameters
     * @see GUISimulationPanel
     */
    public Map<String,Double> getMyParams(){
        return myMap;
    }


}