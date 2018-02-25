package controllers;

import javafx.event.ActionEvent;
import scene.SceneHolder;

public class EventController {
    public void goToFindEvent(ActionEvent actionEvent) {

    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }
}
