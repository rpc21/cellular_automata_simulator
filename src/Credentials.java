import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Credentials {
    private static final double CREDENTIALS_PANEL_X_COOR = GUI.STAGE_SIZE * 1.0/GUI.SCALE;
    private static final double CREDENTIALS_PANEL_Y_COOR = GUI.STAGE_SIZE * 1.0/GUI.SCALE;

    private Text myCredentials = new Text("");
    private StackPane myStackPane = new StackPane();
    public Credentials(String title, String author){
        myCredentials.setText(title + "\n" + author);
        myCredentials.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, 15));
        myStackPane.getChildren().add(myCredentials);
        myStackPane.setLayoutX(CREDENTIALS_PANEL_X_COOR - myCredentials.getBoundsInLocal().getWidth());
        myStackPane.setLayoutY(CREDENTIALS_PANEL_Y_COOR - myCredentials.getBoundsInLocal().getHeight());
    }
    public StackPane getMyCredentials(){
        return myStackPane;
    }
}
