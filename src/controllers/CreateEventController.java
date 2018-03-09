package controllers;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import scene.SceneHolder;

public class CreateEventController {
    public void createEvent(ActionEvent actionEvent) {
    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.eventScene);
    }

    public void pickLocation(MouseEvent mouseEvent) {
    }
}
