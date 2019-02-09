import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS;

public class GUIManager {
    private List<Simulation> mySimulations;
    private List<GUIGrid> myGUIGrids;
    private List<GUISimulationPanel> myGUISimPanels;
    private GUISimulationFactory myGUISimulationFactory;
    private SimulationFactory mySimFact = new SimulationFactory();
    private int numSimulations;
    private GridPane myGridPane;
    private ScrollPane myScrollPane;
    private String myCurrShape = "Hexagon";


    public GUIManager (){
        myGridPane = new GridPane();
        myScrollPane = new ScrollPane();
        mySimulations = new ArrayList<>();
        myGUIGrids = new ArrayList<>();
        myGUISimPanels = new ArrayList<>();
        myGUISimulationFactory = new GUISimulationFactory();
    }
    public void addSimulation(Simulation sim, Group currNode){
        numSimulations++;
        mySimulations.add(sim);
        myGUIGrids.add(myGUISimulationFactory.makeGUIGrid(myCurrShape, sim));
        myGUIGrids.get(numSimulations - 1).makeGUIGrid(sim.getMyGrid().getCells());
        System.out.println("jjj" + myGUIGrids.size());
        myGUISimPanels.add(myGUISimulationFactory.makeSimulationPanel(sim.getMyName(), sim));
        managePositions();

//        ScrollPane myScroll = new ScrollPane();
//        myScroll.setMaxSize(400,400);
//        myScroll.setMinSize(400,400);
//
//        myScroll.setHbarPolicy(ALWAYS);
//        myScroll.setVbarPolicy(ALWAYS);
//        myScroll.setLayoutX(CellularAutomataMain.WINDOW_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2);
//        myScroll.setLayoutY(CellularAutomataMain.WINDOW_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2);
//        myGUIGrids.get(numSimulations-1).getGUIGrid().setLayoutX(-100);
//        myGUIGrids.get(numSimulations-1).getGUIGrid().setLayoutY(-100);
//        myGUIGrids.get(numSimulations-1).getGUIGrid().setTranslateX(-200);
//        myGUIGrids.get(numSimulations-1).getGUIGrid().setTranslateY(-200);
//        Rectangle myRectangle = new Rectangle (500,500);
//        myScroll.setContent(myGUIGrids.get(numSimulations-1).getGUIGrid());
        currNode.getChildren().addAll( myGUIGrids.get(numSimulations - 1).getGUIGrid(),myGUISimPanels.get(numSimulations - 1).getGUISimulationPanel());
        for (GUIGrid g: myGUIGrids)
            if (!currNode.getChildren().contains(g.getGUIGrid()))
                currNode.getChildren().add(g.getGUIGrid());
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

    public void updateGUIParts(){
        for (int i = 0; i < numSimulations; i ++ ){
            mySimulations.get(i).updateGrid();
            myGUIGrids.get(i).makeGUIGrid(mySimulations.get(i).getMyGrid().getCells());
        }
    }

    public void resetSimulations(GUIDefaultPanel currDefaultPanel){
        myGridPane.getChildren().clear();
        for (int i = 0; i < numSimulations; i ++ ){
            Simulation currSim = null;
            GUISimulationPanel currSimPanel = myGUISimPanels.get(i);
            if (currSimPanel.getName().equals(currDefaultPanel.getSimName())) {
                currSim = mySimFact.generateSimulation(currDefaultPanel.getMyBasicParams(), currSimPanel.getMyParams());
            }
            else {
                String newSim = currDefaultPanel.getSimName().replaceAll(" ", "");
                File file = new File(myGUISimulationFactory.makeXMLFileName(newSim));
                File styleFile = new File(myGUISimulationFactory.makeXMLFileName(newSim));
                //var sim = myParser.getSimulation(file);
                var sim = new setUpSimulation().setSimulation(file, styleFile);
                try{
                    currSim = sim;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (currSim != null)
                mySimulations.set(i,currSim);
            GUIGrid currGrid = myGUISimulationFactory.makeGUIGrid(myCurrShape, currSim);
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

    public GridPane retGridPane(){
        return myGridPane;
    }

    public void managePositions() {

        if (numSimulations == 1) {
            StackPane gridOne = myGUIGrids.get(0).getGUIGrid();
            gridOne.setScaleX(1.0);
            gridOne.setScaleY(1.0);
            StackPane panelOne = myGUISimPanels.get(0).getGUISimulationPanel();
            gridOne.setLayoutY(CellularAutomataMain.WINDOW_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2 + 100);
            gridOne.setLayoutX(CellularAutomataMain.WINDOW_SIZE/2 - myGUIGrids.get(0).getHalfWay());
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
            panelOne.setLayoutY(gridOne.getLayoutY());
            panelOne.setTranslateY(gridOne.getTranslateY());
            gridTwo.setLayoutY(gridOne.getLayoutY() + gridOne.getTranslateY() + gridOne.getBoundsInParent().getHeight() + 60);
            gridTwo.setLayoutX(gridOne.getLayoutX());

            StackPane panelTwo = myGUISimPanels.get(1).getGUISimulationPanel();
            panelTwo.setLayoutY(gridTwo.getLayoutY());
        }
        System.out.println(myGridPane.getChildren().size());


    }












    }


