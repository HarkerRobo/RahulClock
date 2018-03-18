package rahulclock;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The GUI that gets launched
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layout.fxml"));

        Scene scene = new Scene(root, 300, 275);
        // scene.setFill(Color.DEEPSKYBLUE);

        stage.setTitle("RahulClock");
        stage.setScene(scene);
        stage.show();
    }

}
