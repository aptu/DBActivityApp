import controllers.ControllerHolder;
import db.DBManager;
import javafx.application.Application;
import javafx.stage.Stage;
import scene.SceneHolder;
import scene.SceneLoader;
import java.util.Random;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        SceneHolder.primaryStage = primaryStage;

        SceneLoader loader = new SceneLoader();

        loader.loadScenes(getClass());

        primaryStage.setTitle("Activity Tracker");
        primaryStage.setScene(SceneHolder.loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        Random rando = new Random();
        ControllerHolder.UserLocationX = rando.nextInt(600) + 100;
        ControllerHolder.UserLocationY = rando.nextInt(500) + 40;
        DBManager.db = new DBManager();
        launch(args);
    }
}
