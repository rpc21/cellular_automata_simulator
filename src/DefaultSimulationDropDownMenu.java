//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.Control;
//
//public class DefaultSimulationDropDownMenu extends DefaultControl {
//    private ChoiceBox<String> myChoiceBox;
//    private GUIGrid myVisGrid;
//
//    private static final double MY_Y_COORDINATE = 160;
//    public DefaultSimulationDropDownMenu(GUIGrid myGrid){
//        super(myGrid, MY_Y_COORDINATE);
//        myVisGrid = myGrid;
//        makeSimulationDropDownMenu();
//    }
//        private void makeSimulationDropDownMenu(){
//        myChoiceBox = new ChoiceBox<>();
//        myChoiceBox.getItems().addAll("Game of Life", "Spreading Fire", "Percolation");
//        myChoiceBox.setValue("Game of Life");
//
//        myChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
//                resetSimulation(myChoiceBox.getItems().get((Integer) number2));
//            }
//        });
//
//    }
//    private void resetSimulation(String mySim){
//       return;
//
//
//    }
//    @Override
//    public Control getMyControl() {
//        return null;
//    }
    //    private void resetSimulation(String mySim){
//        animation.stop();
//        mySimulationChooser.setValue(mySim);
//        if (mySim.equals("Game of Life")){
//            currTestFile = GOLTESTFILE;
//        }
//        else if (mySim.equals("Spreading Fire")){
//            currTestFile = SPREADINGFIREFILE;
//        }
//        else if (mySim.equals("Percolation"))
//            currTestFile = PERCOLATIONFILE;
//        start(window);
//
//
//    }
//}
