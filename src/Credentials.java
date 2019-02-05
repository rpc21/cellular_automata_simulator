import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Credentials {
    private static final int CREDENTIALS_LENGTH = 230;
    private static final int CREDENTIALS_HEIGHT = 20;
    private static final int CREDENTIALS_PANEL_X_COOR = GUI.STAGE_SIZE;
    private static final int CREDENTIALS_PANEL_Y_COOR = GUI.STAGE_SIZE;

    private Text myCredentials = new Text("");
    private StackPane myStackPane = new StackPane();
    public Credentials(String title, String author){
        myCredentials.setText(title + "\n" + author);
        myCredentials.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, 15));
        myCredentials.setX(CREDENTIALS_PANEL_X_COOR - myCredentials.getBoundsInLocal().getWidth());
        myCredentials.setY(CREDENTIALS_PANEL_Y_COOR);
        myStackPane.getChildren().add(myCredentials);
        myStackPane.setLayoutX(CREDENTIALS_PANEL_X_COOR - myCredentials.getBoundsInLocal().getWidth());
        myStackPane.setLayoutY(CREDENTIALS_PANEL_Y_COOR - myCredentials.getBoundsInLocal().getHeight() * 3);
    }
    public StackPane getMyCredentials(){
        return myStackPane;
    }
}
