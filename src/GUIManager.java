import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUIManager {
    private List<Simulation> mySimulations;
    private List<GUIGrid> myGUIGrids;
    private List<GUISimulationPanel> myGUISimPanels;
    private GUISimulationFactory myGUISimulationFactory;
    private SimulationFactory mySimFact = new SimulationFactory();
    private int numSimulations;
    private Stage myStage;

    private static final int GRID_X_COOR = 250;
    private static final int GRID_Y_COOR = 350;
    private int GRID_SPACING = 50;
    private int CTRL_OFFSET = 20;


    public GUIManager (){
        mySimulations = new ArrayList<>();
        myGUIGrids = new ArrayList<>();
        myGUISimPanels = new ArrayList<>();
        myGUISimulationFactory = new GUISimulationFactory();
    }
    public void addSimulation(Simulation sim, Group currNode, Stage stage){
        numSimulations++;
        mySimulations.add(sim);
        myStage = stage;
        myGUIGrids.add(myGUISimulationFactory.makeGUIGrid(sim,stage));
        myGUIGrids.get(numSimulations - 1).makeGUIGrid(sim.getMyGrid().getCells());
        myGUISimPanels.add(myGUISimulationFactory.makeSimulationPanel(sim.getMyName(), sim));
        managePositions();
        currNode.getChildren().addAll( myGUIGrids.get(numSimulations - 1).getGUIGrid(),
                                       myGUIGrids.get(numSimulations - 1).getGUIStyle(),
                                       myGUISimPanels.get(numSimulations - 1).getGUISimulationPanel());
    }


    public void removeSimulation(Group currNode){
        int mostRecentSim = numSimulations - 1;
        currNode.getChildren().removeAll(myGUIGrids.get(mostRecentSim).getGUIGrid(),
                myGUISimPanels.get(mostRecentSim).getGUISimulationPanel(), myGUIGrids.get(mostRecentSim).getGUIStyle());
        myGUIGrids.remove(myGUIGrids.size() -1 );
        myGUISimPanels.remove(myGUISimPanels.size() -1);
        mySimulations.remove(mySimulations.size() - 1);
        numSimulations--;
        managePositions();
    }


    public void updateGUIParts(){
        for (int i = 0; i < numSimulations; i ++ ){
            mySimulations.get(i).updateNeighbors(myGUIGrids.get(i).getNeighbors());
            mySimulations.get(i).updateGrid();
            myGUIGrids.get(i).makeGUIGrid(mySimulations.get(i).getMyGrid().getCells());
        }
    }

    public void resetSimulations(GUIDefaultPanel currDefaultPanel, Map<String,String> credentials){
        for (int i = 0; i < numSimulations; i ++ ){
            Simulation currSim = null;
            GUISimulationPanel currSimPanel = myGUISimPanels.get(i);
            GUIGrid guiGrid = myGUIGrids.get(i);
            if (currSimPanel.getName().equals(currDefaultPanel.getSimName())) {
                currDefaultPanel.getMyBasicParams().put(XMLStyler.NEIGHBORS_TYPE_TAG_NAME,guiGrid.getNeighbors());
                currSim = mySimFact.generateSimulation(currDefaultPanel.getMyBasicParams(), currSimPanel.getMyParams(), credentials);
            }
            else {
                String newSim = currDefaultPanel.getSimName();
                File file = new File(myGUISimulationFactory.makeXMLFileName(newSim));
                File styleFile = new File(myGUISimulationFactory.makeXMLFileName(newSim));
                var sim = new setUpSimulation().setSimulation(file, styleFile);
                try{
                    currSim = sim;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (currSim != null)
                mySimulations.set(i,currSim);
            GUIGrid currGrid = myGUISimulationFactory.makeGUIGrid(currSim,myStage);
            currGrid.makeGUIGrid(currSim.getMyGrid().getCells());
            myGUIGrids.set(i,currGrid);
            myGUISimPanels.set(i,myGUISimulationFactory.makeSimulationPanel(currSim.getMyName(),currSim));
        }


       managePositions();
    }

    public List<GUIGrid> getGrids(){
       return myGUIGrids;
    }
    public List<GUISimulationPanel> getPanels(){
        return myGUISimPanels;
    }
    public Simulation getPrimarySimulation(){
        return mySimulations.get(0);
    }


    private void managePositions() {
        for (int i = 0; i < numSimulations; i++) {
            StackPane currGrid = myGUIGrids.get(i).getGUIGrid();

            currGrid.setScaleX(1.0 / (double) numSimulations);
            currGrid.setScaleY(1.0 / (double) numSimulations);

            currGrid.setLayoutY(GRID_Y_COOR);
            currGrid.setTranslateY(i * (currGrid.getBoundsInParent().getHeight() + GRID_SPACING));
            currGrid.setLayoutX(GRID_X_COOR);

            StackPane currPanel = myGUISimPanels.get(i).getGUISimulationPanel();
            currPanel.setLayoutY(currGrid.getLayoutY() + currGrid.getTranslateY());

            Node style = myGUIGrids.get(i).getGUIStyle();
            style.setLayoutX(currGrid.getLayoutX() + currGrid.getTranslateX());
            style.setLayoutY(currGrid.getLayoutY() + currGrid.getTranslateY() +
                    currGrid.getBoundsInParent().getHeight() + CTRL_OFFSET);

        }
    }



    }




