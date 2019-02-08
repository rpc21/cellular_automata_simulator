import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS;

public class GUIManager {
    private List<Simulation> mySimulations;
    private List<GUIHexagonGrid> myGUIGrids;
    private List<GUISimulationPanel> myGUISimPanels;
    private GUISimulationFactory myGUISimulationFactory;
    private SimulationFactory mySimFact = new SimulationFactory();
    private int numSimulations;
    private GridPane myGridPane;
    private ScrollPane myScrollPane;


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
        myGUIGrids.add(new GUIHexagonGrid(sim.getMyGrid().getNumRows(), sim.getMyGrid().getNumCols(),sim));
        myGUIGrids.get(numSimulations - 1).makeGUIGrid(sim.getMyGrid().getCells());
        myGUISimPanels.add(myGUISimulationFactory.makeSimulationPanel(sim.getMyName(), sim));
        managePositions();

//        ScrollPane myScroll = new ScrollPane();
//        myScroll.setMaxSize(700,700);
//        myScroll.setMinSize(300,300);
//
//        myScroll.setHbarPolicy(ALWAYS);
//        myScroll.setVbarPolicy(ALWAYS);
//        myScroll.setLayoutX(CellularAutomataMain.WINDOW_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2);
//        myScroll.setLayoutY(CellularAutomataMain.WINDOW_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2);
//        myGUIGrids.get(numSimulations-1).getGUIHexGrid().setTranslateX(CellularAutomataMain.WINDOW_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2);
//        myGUIGrids.get(numSimulations-1).getGUIHexGrid().setTranslateY(CellularAutomataMain.WINDOW_SIZE/2 - GUIGrid.GUI_GRID_SIZE/2);
        //myScroll.setContent(myGUIGrids.get(numSimulations  - 1).getGUIHexGrid());
        currNode.getChildren().addAll( myGUIGrids.get(numSimulations-1).getGUIHexGrid(),myGUISimPanels.get(numSimulations - 1).getGUISimulationPanel());
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
                File file = new File("tests/" + currDefaultPanel.getSimName().replaceAll(" ","") + "Test.xml");
                var p = new XMLParser(Simulation.DATA_TYPE).getSimulation(file);
                try {
                    currSim = p;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (currSim != null)
                mySimulations.set(i,currSim);
            GUIHexagonGrid currGrid = new GUIHexagonGrid(currSim.getMyGrid().getNumRows(), currSim.getMyGrid().getNumCols(), currSim);
            currGrid.makeGUIGrid(currSim.getMyGrid().getCells());
            myGUIGrids.set(i,currGrid);
            myGUISimPanels.set(i,myGUISimulationFactory.makeSimulationPanel(currSim.getMyName(),currSim));
        }


       managePositions();
    }

    public List<GUIHexagonGrid> getGrids(){
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

    private void managePositions() {

        if (numSimulations == 1) {
            StackPane gridOne = myGUIGrids.get(0).getGUIHexGrid();
            gridOne.setScaleX(1.0);
            gridOne.setScaleY(1.0);
            StackPane panelOne = myGUISimPanels.get(0).getGUISimulationPanel();
            panelOne.setLayoutY(0.0);
        } else {
            StackPane gridOne = myGUIGrids.get(0).getGUIHexGrid();
            StackPane gridTwo = myGUIGrids.get(1).getGUIHexGrid();
            gridOne.setScaleX(0.75);
            gridOne.setScaleY(0.75);
            gridTwo.setScaleX(0.75);
            gridTwo.setScaleY(0.75);
            if (gridOne.getBoundsInParent().intersects((gridTwo.getBoundsInParent())))
                gridTwo.setLayoutY(300);
            //gridTwo.setTranslateY(gridTwo.getTranslateY() + gridOne.getBoundsInParent().getHeight() * 2);
            gridTwo.setTranslateX(gridOne.getTranslateX());

            StackPane panelOne = myGUISimPanels.get(0).getGUISimulationPanel();
            StackPane panelTwo = myGUISimPanels.get(1).getGUISimulationPanel();
            panelOne.setScaleX(0.75);
            panelOne.setScaleY(0.75);
            panelTwo.setScaleX(0.75);
            panelTwo.setScaleY(0.75);
            panelOne.setLayoutY(0.0);
            panelTwo.setLayoutY(gridTwo.getLayoutY() + 30);
        }
//        myGridPane.getChildren().clear();
//        int count = 0;
//        for (int i = 0; i < 2; i++){
//            for (int j = 0; j < 2; j++) {
//                if (count < myGUIGrids.size()) {
//                    myGUIGrids.get(count).getGUIHexGrid().setScaleX(1.0 - (0.25 * (myGUIGrids.size() - 1)));
//                    myGUIGrids.get(count).getGUIHexGrid().setScaleY(1.0 - (0.25 * (myGUIGrids.size() - 1)));
//                    myGridPane.add(myGUIGrids.get(count).getGUIHexGrid(), i, j);
//                }
//                count++;
//            }
//        }
//        if (myGUIGrids.size() > 1) {
//            RowConstraints column = new RowConstraints(200);
//            myGridPane.getRowConstraints().add(column);
//            if (myGUIGrids.size() > 2) {
//                ColumnConstraints col = new ColumnConstraints(200);
//                myGridPane.getColumnConstraints().add(col);
//            }
//        }

//        myGridPane.setLayoutX(0.0);
//        myGridPane.setLayoutY(0.0);
//        myGridPane.setTranslateX(0.0);
//        myGridPane.setTranslateY(0.0);
        System.out.println(myGridPane.getChildren().size());


    }












    }


