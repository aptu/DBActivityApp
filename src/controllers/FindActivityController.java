package controllers;

import javafx.event.ActionEvent;
import scene.SceneHolder;

public class FindActivityController {

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }
}
