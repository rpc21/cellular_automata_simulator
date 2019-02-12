import java.util.HashMap;
import java.util.Map;

public class GUIBlankPanel extends GUISimulationPanel{
    HashMap<String,Double> myMap = new HashMap<String,Double>();

    public GUIBlankPanel(String mySimName, Map<String,Double> initParams) {
        super(mySimName);
    }
    public HashMap<String,Double> getMyParams(){
        return myMap;
    }


}