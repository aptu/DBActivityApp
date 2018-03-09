package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import scene.SceneHolder;

import java.util.Timer;

public class ActivityController {
    public ChoiceBox activityTypeBox;
    public Label timerLabel;
    public Button startStopButton;
    public Button backButton;

    //used to start the displaying timer
    private Timer timer;
    private boolean timerStarted = false;

    public void startStopTimer(ActionEvent actionEvent) {

        if(timerStarted)
        {
            startStopButton.setText("Start");
            backButton.setDisable(false);
            //log activity
        }
        else
        {
            startStopButton.setText("Stop");
            backButton.setDisable(true);
        }

        timerStarted = !timerStarted;

    }

    public void goToMenu(ActionEvent actionEvent) {

        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }
}
