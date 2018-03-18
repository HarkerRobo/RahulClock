package rahulclock;

import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Interacts with FXML
 */
public class Controller {
    @FXML private Text time;

    @FXML public void initialize() {
        Font font = Font.loadFont(getClass().getResource("/font/SFPixelateShaded-Bold.ttf").toExternalForm(), 60);
        time.setFont(font);
        new Server(time::setText).start();
    }
}
