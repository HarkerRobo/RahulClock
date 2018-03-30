package rahulclock;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Interacts with FXML
 */
public class Controller {
    @FXML private Text time;
    @FXML private BorderPane pane;

    @FXML public void initialize() {
        Font font = Font.loadFont(getClass().getResource("/font/SFPixelateShaded-Bold.ttf").toExternalForm(), time.getFont().getSize());
        time.setFont(font);

        Server server = new Server(time::setText);
        server.start();

        new Connector(() -> {
            Platform.runLater(() -> {
                System.out.println(pane.getScene());
                pane.getScene().setFill(Color.WHITE);
            });
        }, server).start();
    }
}
