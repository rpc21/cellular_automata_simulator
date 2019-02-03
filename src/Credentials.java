import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Credentials {
    private static final int CREDENTIALS_LENGTH = 150;
    private static final int CREDENTIALS_HEIGHT = 20;
    private static final int CREDENTIALS_PANEL_X_COOR = GUI.STAGE_SIZE - CREDENTIALS_LENGTH;
    private static final int CREDENTIALS_PANEL_Y_COOR = GUI.STAGE_SIZE - CREDENTIALS_HEIGHT;

    private Text myCredentials = new Text("");
    private StackPane myStackPane = new StackPane();
    public Credentials(String title, String author){
        myCredentials.setText(title + "\n" + author);
        myCredentials.setFont(Font.font(GUISimulationPanel.DEFAULT_FONT_NAME, 15));
        myCredentials.setX(CREDENTIALS_PANEL_X_COOR);
        myCredentials.setY(CREDENTIALS_PANEL_Y_COOR);
    }
    public Text getMyCredentials(){
        return myCredentials;
    }
}
