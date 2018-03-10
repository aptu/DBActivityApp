package controllers;

import db.DBManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.concurrent.Task;
import scene.SceneHolder;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

public class ActivityController {
    public ChoiceBox activityTypeBox;
    public Label timerLabel;
    public Button startStopButton;
    public Button backButton;
    public Button pauseButton;

    //used to start the displaying timer
    private long startTime;
    private long pauseStart;
    private long totalPauseTime = 0;

    private boolean activityOngoing = false;
    private boolean paused = false;

    public void init() {
        getDefaultInterest();
    }

    public void startStopTimer(ActionEvent actionEvent) throws InterruptedException {

        if (!activityOngoing)
        {
            // TODO: Check which activity
            startStopButton.setText("Finish");
            startTime = System.nanoTime();
            activityOngoing = true;
        }
        else {
            activityOngoing = false;
            startStopButton.setText("Start");
            double totalTime = System.nanoTime() - startTime;
            totalPauseTime = 0;

            // TODO: Log activity
            return;
        }

        Timer timer = new Timer();
        int count = 0;
        timer.schedule(new TimerTask() { // timer task to update the seconds
            @Override
            public void run() {
                // use Platform.runLater(Runnable runnable) If you need to update a GUI component from a non-GUI thread.
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (!activityOngoing)
                            return;

                        if (paused)
                            return;

                        long nanoElapsed = System.nanoTime() - startTime - totalPauseTime;
                        Integer seconds = (int)(nanoElapsed / 1000000000);
                        Integer minutes = 0;
                        Integer hours = 0;
                        if (seconds > 59)
                        {
                            minutes = seconds / 60;
                            seconds %= 60;
                        }
                        if (minutes > 59)
                        {
                            hours = minutes / 60;
                            minutes %= 60;
                        }

                        String hoursString;
                        String minutesString;
                        String secondsString;

                        if (hours < 10)
                            hoursString = "0" + hours.toString();
                        else
                            hoursString = hours.toString();

                        if (minutes < 10)
                            minutesString = "0" + minutes.toString();
                        else
                            minutesString = minutes.toString();

                        if (seconds < 10)
                            secondsString = "0" + seconds.toString();
                        else
                            secondsString = seconds.toString();

                        timerLabel.setText(hoursString + ":" + minutesString + ":" + secondsString);
                    }
                });
                if (!activityOngoing)
                    timer.cancel();
            }
        }, 0, 1);
    }

    public void pause(ActionEvent event) {
        paused = !paused;
        if (paused) {
            pauseStart = System.nanoTime();
            pauseButton.setText("Resume");
        } else {
            totalPauseTime += (System.nanoTime() - pauseStart);
            pauseButton.setText("Pause");
        }
    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }

    private void getDefaultInterest() {
        // default interested is a random interest form the user interestes.
        if (!activityOngoing) {
            boolean[] boolInterests;
            List<String> allActivities = DBManager.db.getAllActivities();
            try {
                boolInterests = DBManager.db.getUserInterests();
                int i;
                for(i = 0; i < boolInterests.length; i++) {
                    if(boolInterests[i])
                        break;
                }

                if(i == boolInterests.length)
                    return;

                activityTypeBox.setValue(allActivities.get(i));

            } catch(SQLException e) {
                System.out.println("Error getting user interests from DB");
            }
        }
    }
}
