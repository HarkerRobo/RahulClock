package rahulclock;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The GUI that gets launched
 */
public class App extends Application {

    static {
        try {
			FileHandler handler = new FileHandler("rahulclock-log.txt", true);
            handler.setLevel(Level.ALL);
            handler.setFormatter(new SimpleFormatter());
			Logger.getLogger("rahulclock").addHandler(handler);
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layout.fxml"));

        Scene scene = new Scene(root, 300, 275);
        scene.setFill(Color.LIGHTGRAY);

        stage.setTitle("RahulClock");
        stage.setScene(scene);
        stage.show();
    }

}
