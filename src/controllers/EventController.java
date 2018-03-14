package controllers;

import javafx.event.ActionEvent;
import scene.SceneHolder;

import java.sql.SQLException;

public class EventController {
    public void goToFindEvent(ActionEvent actionEvent) {
        ControllerHolder.findEventController.loadSettings();
        SceneHolder.primaryStage.setScene(SceneHolder.findEventScene);
    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }

    public void goToMyEvents(ActionEvent actionEvent) throws SQLException {
        ControllerHolder.myEventsController.loadEvents();
        SceneHolder.primaryStage.setScene(SceneHolder.myEventsScene);
    }

    public void goToCreateEvent(ActionEvent actionEvent) {
        ControllerHolder.createEventController.loadSettings();
        SceneHolder.primaryStage.setScene(SceneHolder.createEventScene);
    }

}
