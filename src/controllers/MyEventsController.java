package controllers;

import javafx.event.ActionEvent;
import scene.SceneHolder;

public class MyEventsController {
    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.eventScene);
    }
}
