import java.util.HashMap;

public class GUIBlankPanel extends GUISimulationPanel{
    HashMap<String,Double> myMap = new HashMap<String,Double>();

    public GUIBlankPanel(Simulation mySim){
        super(mySim);
    }

    public HashMap<String,Double> getMyParams(){
        return myMap;
    }


}