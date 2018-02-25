package controllers;

import javafx.event.ActionEvent;
import scene.SceneHolder;

public class MainMenuController {

    public void goToActivities(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.activityScene);
    }

    public void goToFindActivities(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.findActivityScene);
    }

    public void goToEvents(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.eventScene);
    }

    public void goToProfile(ActionEvent actionEvent) {

        //load the user preferences before we switch to this scene
        ControllerHolder.profileController.loadUserPreferences();

        SceneHolder.primaryStage.setScene(SceneHolder.profileScene);
    }

    public void goToHistory(ActionEvent actionEvent) {
    }


    public void logOut(ActionEvent actionEvent) {

        SceneHolder.primaryStage.setScene(SceneHolder.loginScene);
    }
}
