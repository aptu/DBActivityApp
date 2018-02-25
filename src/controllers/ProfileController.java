package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import scene.SceneHolder;

public class ProfileController {
    public Label userDOB;
    public Label userName;
    public Label userGender;
    public CheckBox hikingBox;
    public CheckBox bikingBox;
    public CheckBox walkingBox;
    public CheckBox SnowboardingBox;
    public CheckBox fishingBox;
    public CheckBox runningBox;
    public CheckBox swimmingBox;
    public CheckBox polevaultingBox;
    public CheckBox skydivingBox;
    public CheckBox skiingBox;


    public void loadUserPreferences()
    {
        //TODO: query database for user preferences

        hikingBox.setSelected(true);
    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }

    public void updateSettings(ActionEvent actionEvent) {

        //TODO: update the user preferences

        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }
}
