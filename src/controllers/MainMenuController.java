package controllers;

import db.DBManager;
import javafx.event.ActionEvent;
import scene.SceneHolder;

import java.sql.SQLException;

public class MainMenuController {

    public void goToActivities(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.activityScene);
    }

    public void goToFindActivities(ActionEvent actionEvent) {
        ControllerHolder.findActivityController.loadSettings();
        SceneHolder.primaryStage.setScene(SceneHolder.findActivityScene);
    }

    public void goToEvents(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.eventScene);
    }

    public void goToProfile(ActionEvent actionEvent) throws SQLException {

        //load the user preferences before we switch to this scene
        ControllerHolder.profileController.loadUserPreferences();

        SceneHolder.primaryStage.setScene(SceneHolder.profileScene);
    }

    public void goToHistory(ActionEvent actionEvent) {
    }


    public void logOut(ActionEvent actionEvent) throws SQLException {

        DBManager.db.logout();
        SceneHolder.primaryStage.setScene(SceneHolder.loginScene);
    }
}
