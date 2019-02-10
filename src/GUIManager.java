import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS;

public class GUIManager {
    private List<Simulation> mySimulations;
    private List<GUIGrid> myGUIGrids;
    private List<GUISimulationPanel> myGUISimPanels;
    private GUISimulationFactory myGUISimulationFactory;
    private SimulationFactory mySimFact = new SimulationFactory();
    private int numSimulations;
    private Stage myStage;
    private ScrollPane myScrollPane;
    private String myShape;

    public GUIManager (){
        myScrollPane = new ScrollPane();
        mySimulations = new ArrayList<>();
        myGUIGrids = new ArrayList<>();
        myGUISimPanels = new ArrayList<>();
        myGUISimulationFactory = new GUISimulationFactory();
    }
    public void addSimulation(Simulation sim, Group currNode, Stage stage){
        numSimulations++;
        mySimulations.add(sim);
        myStage = stage;
        myShape = "Rectangle";
        myGUIGrids.add(myGUISimulationFactory.makeGUIGrid(myShape, sim,stage));
        myGUIGrids.get(numSimulations - 1).makeGUIGrid(sim.getMyGrid().getCells());
        myGUISimPanels.add(myGUISimulationFactory.makeSimulationPanel(sim.getMyName(), sim));
        managePositions();
        currNode.getChildren().addAll( myGUIGrids.get(numSimulations - 1).getGUIGrid(),myGUIGrids.get(numSimulations - 1).getGUIStyle(),myGUISimPanels.get(numSimulations - 1).getGUISimulationPanel());
        for (GUIGrid g: myGUIGrids) {
            if (!currNode.getChildren().contains(g.getGUIGrid())) {
                currNode.getChildren().add(g.getGUIGrid());
                currNode.getChildren().add(g.getGUIStyle());
            }
        }
    }


    public void removeSimulation(Group currNode){
        currNode.getChildren().removeAll(myGUIGrids.get(myGUIGrids.size() - 1).getGUIGrid(),
                myGUISimPanels.get(myGUISimPanels.size() -1).getGUISimulationPanel());
        myGUIGrids.remove(myGUIGrids.size() -1 );
        myGUISimPanels.remove(myGUISimPanels.size() -1);
        mySimulations.remove(mySimulations.size() - 1);
        numSimulations--;
        managePositions();
    }


//    public void updateGUIGrid(HashMap<String,String> myProperties){
//        for (int i = 0; i < numSimulations; i++) {
//            myGUIGrids.set(i, myGUISimulationFactory.makeGUIGrid(myProperties.get("Shape"), mySimulations.get(i)));
//            myGUIGrids.get(i).makeGUIGrid(mySimulations.get(i).getMyGrid().getCells());
//        }
//        managePositions();
//    }

    public void updateGUIParts(){
        for (int i = 0; i < numSimulations; i ++ ){
            mySimulations.get(i).updateGrid();
            myGUIGrids.get(i).makeGUIGrid(mySimulations.get(i).getMyGrid().getCells());
        }
    }

    public void resetSimulations(GUIDefaultPanel currDefaultPanel){
        for (int i = 0; i < numSimulations; i ++ ){
            Simulation currSim = null;
            GUISimulationPanel currSimPanel = myGUISimPanels.get(i);
            if (currSimPanel.getName().equals(currDefaultPanel.getSimName())) {
                currSim = mySimFact.generateSimulation(currDefaultPanel.getMyBasicParams(), currSimPanel.getMyParams());
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
            GUIGrid currGrid = myGUISimulationFactory.makeGUIGrid(myShape, currSim,myStage);
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


    public void managePositions() {

        if (numSimulations == 1) {
            StackPane gridOne = myGUIGrids.get(0).getGUIGrid();
            gridOne.setScaleX(1.0);
            gridOne.setScaleY(1.0);
            StackPane panelOne = myGUISimPanels.get(0).getGUISimulationPanel();
            gridOne.setLayoutY(CellularAutomataMain.WINDOW_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + 100);
            gridOne.setLayoutX(CellularAutomataMain.WINDOW_SIZE/2 - myGUIGrids.get(0).getHalfWay());
            myGUIGrids.get(0).getGUIStyle().setLayoutY(gridOne.getLayoutY() + gridOne.getTranslateY() + gridOne.getBoundsInParent().getHeight() + 30);
            myGUIGrids.get(0).getGUIStyle().setLayoutX(gridOne.getLayoutX() + gridOne.getTranslateX());
            panelOne.setLayoutY(gridOne.getLayoutY());
            panelOne.setTranslateY(gridOne.getTranslateY());
        } else {
            StackPane gridOne = myGUIGrids.get(0).getGUIGrid();
            StackPane gridTwo = myGUIGrids.get(1).getGUIGrid();
            StackPane panelOne = myGUISimPanels.get(0).getGUISimulationPanel();
            gridOne.setScaleX(0.5);
            gridOne.setScaleY(0.5);
            gridTwo.setScaleX(0.5);
            gridTwo.setScaleY(0.5);
            gridOne.setLayoutY(CellularAutomataMain.WINDOW_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + 100);
            gridOne.setLayoutX(CellularAutomataMain.WINDOW_SIZE/2 - 0.5 * myGUIGrids.get(0).getHalfWay());
            myGUIGrids.get(0).getGUIStyle().setLayoutY(gridOne.getLayoutY() + gridOne.getTranslateY() + gridOne.getBoundsInParent().getHeight() + 30);
            myGUIGrids.get(0).getGUIStyle().setLayoutX(gridOne.getLayoutX() + gridOne.getTranslateX());
            panelOne.setLayoutY(gridOne.getLayoutY());
            panelOne.setTranslateY(gridOne.getTranslateY());
            gridTwo.setLayoutY(gridOne.getLayoutY() + gridOne.getTranslateY() + gridOne.getBoundsInParent().getHeight() + 60);
            gridTwo.setLayoutX(gridOne.getLayoutX());
            myGUIGrids.get(1).getGUIStyle().setLayoutY(gridTwo.getLayoutY() + gridTwo.getTranslateY() + gridTwo.getBoundsInParent().getHeight() + 10);
            myGUIGrids.get(1).getGUIStyle().setLayoutX(gridTwo.getLayoutX() + gridTwo.getTranslateX());
            StackPane panelTwo = myGUISimPanels.get(1).getGUISimulationPanel();
            panelTwo.setLayoutY(gridTwo.getLayoutY());
        }


    }












    }


