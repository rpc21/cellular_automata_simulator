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
    private static final int GRID_SPACING = 50;
    private static final int CTRL_OFFSET = 20;


    public GUIManager (){
        mySimulations = new ArrayList<>();
        myGUIGrids = new ArrayList<>();
        myGUISimPanels = new ArrayList<>();
        myGUISimulationFactory = new GUISimulationFactory();
    }
    /**
     * This method is used to add the subsequent simulation should users want to compare two at the same time. It deals with
     * ensuring the current window displays the appropriate grid, side panel, and its accompanying style options
     * @param sim is the added simulation
     * @param currNode is the root of the main scene window
     * @param stage is the primary window display the user is interacting with
     * @see GUISimulationPanel
     * @see GUIGrid
     */
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

    /**
     * This method is used to remove the secondary simulation should users want to go back to only viewing a single
     * simulation. It deals with ensuring the primary node no longer displays the additional simulation's grid, panel,
     * and style
     * @param currNode is the root of the main scene window
     */
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

    /**
     * This method is used to update all of the grids currently being displayed on the window by first informing the backend
     * of any changes to the preferred neighbors setting, updating the simulation's current state, and displaying this new
     * state on the GUIGrid(s)
     * @see GUIGrid
     */
    public void updateGUIParts(){
        for (int i = 0; i < numSimulations; i ++ ){
            mySimulations.get(i).updateNeighbors(myGUIGrids.get(i).getNeighbors());
            mySimulations.get(i).updateGrid();
            myGUIGrids.get(i).makeGUIGrid(mySimulations.get(i).getMyGrid().getCells());
        }
    }
    /**
     * This method is invoked when a user clicks the reset button. Depending on whether the simulation needs to be entirely reset
     * because the type of the simulation changed, or if particular parameters simply changed, this method handles calls to
     * the GUISimulationFactory (to alter display if that's all that needs to be modified) or invokes the backend to make
     * changes if need be
     * @param currDefaultPanel this panel provides information about the current simulation and helps restart the current one
     *                         if only some parameters changed
     * @param credentials notifies Simulation whether or not a new set of credentials needs to be read in
     * @see GUIGrid
     * @see GUIDefaultPanel
     * @see GUISimulationPanel
     * @see XMLStyler
     * @see GUISimulationFactory
     */
    public void resetSimulations(GUIDefaultPanel currDefaultPanel, Map<String,String> credentials){
        for (int i = 0; i < numSimulations; i ++ ){
            Simulation currSim = null;
            GUISimulationPanel currSimPanel = myGUISimPanels.get(i);
            if (currSimPanel.getName().equals(currDefaultPanel.getSimName())) {
                GUIGrid iterGrid = myGUIGrids.get(i);
                currDefaultPanel.getMyBasicParams().put(XMLStyler.NEIGHBORS_TYPE_TAG_NAME,iterGrid.getNeighbors());
                currSim = mySimFact.generateSimulation(currDefaultPanel.getMyBasicParams(), currSimPanel.getMyParams(), credentials);
            }
            else {
                currSim = makeNewSimulation(currDefaultPanel.getSimName());
            }
            mySimulations.set(i,currSim);
            GUIGrid currGrid = myGUISimulationFactory.makeGUIGrid(currSim,myStage);
            currGrid.makeGUIGrid(currSim.getMyGrid().getCells());
            myGUIGrids.set(i,currGrid);
            myGUISimPanels.set(i,myGUISimulationFactory.makeSimulationPanel(currSim.getMyName(),currSim));
        }
       managePositions();
    }
    /**
     * This method returns a list of the current grids to help the GUI handle the default panel
     * @see GUIGrid
     */
    public List<GUIGrid> getGrids(){
       return myGUIGrids;
    }
    /**
     * This method returns a list of the current grids' simulation panels so the GUI can add them to the main node
     * @see GUISimulationPanel
     */
    public List<GUISimulationPanel> getPanels(){
        return myGUISimPanels;
    }
    /**
     * This method returns the primary (initial) simulation currently being displayed
     * @see GUIGrid
     */
    public Simulation getPrimarySimulation(){
        return mySimulations.get(0);
    }

    /**
     * This is a helper method that creates a new simulation when the user wishes to change simulations
     * @see GUISimulationFactory
     * @returns currSim New simulation reference to be displayed by the GUI
     */
    private Simulation makeNewSimulation(String newSim){
        Simulation currSim = null;
        File file = new File(myGUISimulationFactory.makeXMLFileName(newSim));
        File styleFile = new File(myGUISimulationFactory.makeXMLFileName(newSim));
        var sim = new setUpSimulation().setSimulation(file, styleFile);
        try{
            currSim = sim;
        }catch (Exception e){
            System.out.println("Failed to properly load XML files for new simulation, please check to see if files " +
                    "are in the correct folder and formatted properly");
        }
        return currSim;
    }
    /**
     * Helps manage the positions of the GUIGrid and simulation panels in the GUI depending on the current number of
     * simulations being displayed
     * @see GUISimulationPanel
     * @see GUIGrid
     */
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




