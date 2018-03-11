package controllers;

import db.DBManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.concurrent.Task;
import scene.SceneHolder;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

public class ActivityController {
    public ChoiceBox activityTypeBox;
    public Label timerLabel;
    public Label loggedLabel;
    public Button startStopButton;
    public Button backButton;
    public Button pauseButton;

    //used to start the displaying timer
    private long startTime;
    private long pauseStart;
    private long totalPauseTime = 0;

    private boolean activityOngoing = false;
    private boolean paused = false;

    private String currentActivity = null;

    public void init() {
        getDefaultInterest();
    }

    public void startStopTimer(ActionEvent actionEvent) throws InterruptedException {

        if (!activityOngoing) {
            currentActivity = activityTypeBox.getValue().toString();
            startStopButton.setText("Finish");
            startTime = System.nanoTime();
            activityOngoing = true;
            loggedLabel.setText(currentActivity + "...");
        } else {
            activityOngoing = false;
            startStopButton.setText("Start");
            long totalTime = System.nanoTime() - startTime;
            totalPauseTime = 0;

            try {
                PreparedStatement insertStatement = DBManager.db.connection.prepareStatement(
                        "insert into ActivityApp.ActivityHistory " +
                            "(UserID, ActivityID, DateTime, CalBurned, Duration, Distance, Latitude, Longitude) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?);");

                // Get user ID
                insertStatement.setInt(1, DBManager.db.currUserId);

                // Find the ActivityID for whatever the heck we're doing
                PreparedStatement lookupStatement = DBManager.db.connection.prepareStatement(
                        "select ActivityID from ActivityApp.Activity where Type like ?");
                lookupStatement.setString(1, currentActivity);
                ResultSet results = lookupStatement.executeQuery();
                results.next();

                // Set ActivityID
                insertStatement.setInt(2, results.getInt("ActivityID"));

                // Set Date
                insertStatement.setTimestamp(3, new Timestamp(new Date().getTime()));

                // Set Calories
                insertStatement.setFloat(4, getCalories(currentActivity, (int)(totalTime / 1000000000)));

                // Set Duration, minutes
                insertStatement.setFloat(5, ((float)totalTime / 1000000000 / 60));

                // Set Distance
                insertStatement.setInt(6, getDistance(currentActivity, (int)(totalTime / 1000000000)));

                // Set Location
                insertStatement.setInt(7, ControllerHolder.UserLocationY);
                insertStatement.setInt(8, ControllerHolder.UserLocationX);

                insertStatement.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            timerLabel.setText("00.00.00");
            loggedLabel.setText(currentActivity + " was logged.");
            currentActivity = null;
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
            loggedLabel.setText(currentActivity + " paused.");
        } else {
            totalPauseTime += (System.nanoTime() - pauseStart);
            pauseButton.setText("Pause");
            loggedLabel.setText(currentActivity + "...");
        }
    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }

    private void getDefaultInterest() {
        // default interested is a random interest form the user interests.
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

    private float getCalories(String activity, int duration)
    {
        // Comical approximation of burning calories based on whatever I felt was appropriate at 4:00 in the morning

        float weight;
        try {
            PreparedStatement lookupStatement = DBManager.db.connection.prepareStatement(
                    "select Weight from ActivityApp.User where UserID = ?");
            lookupStatement.setInt(1, DBManager.db.currUserId);
            ResultSet results = lookupStatement.executeQuery();
            results.next();
            weight = results.getFloat("Weight");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }

        switch (activity)
        {
            case "Hiking":
                // y = 2.875x - 25
                // linear equation based on two rough points I found on a website somewhere
                // Equation doesn't really hold weight (heh) at extreme bounds but that's okay
                return (float)(2.875 * weight - 25) * ((float)duration / 60 / 60);
            case "Biking":
                // Hell yeah this time I found a table: http://www.ilovebicycling.com/how-many-calories-do-you-burn-when-cycling/
                // y = 3.74x - 14
                return (float)(3.74 * weight - 14) * ((float)duration / 60 / 60);
            case "Running":
                // y = 4.68 - 18
                return (float)(4.68 * weight - 18) * ((float)duration / 60 / 60);
            case "Walking":
                // y = 2.34 - 18
                // Walking's about half of running, probably
                return (float)(2.34 * weight - 18) * ((float)duration / 60 / 60);
            case "Swimming":
                // Suspiciously, the table I linked above shows the exact same data for Swimming (laps, freestyle, fast) as
                // it does for Running (6mph, 10 min mile). I begin to doubt the veracity of www.ilovebicycling.com.
                // ... but not enough to not use their data.
                // y = 4.68 - 18
                return (float)(4.68 * weight - 18) * ((float)duration / 60 / 60);
            case "Snowboarding":
                // I had trouble finding data on this one. I found a website that claims the "non-profit trade association
                // Snowsports Industries America" put out the numbers 500 c/h for skiing and 450 c/h for snowboarding,
                // but admits that the data didn't come from a published comparison study and hilariously comes with the
                // caveat "Neither stat includes time spent in the lift," so in a future extension to this app we should
                // probably check with the user when they hit "start" to make sure they're really on the slope.
                return 450;
            case "Skiing":
                // See above
                return 500;
            case "Fishing":
                // Calories burned while fishing is probably proportional to the number of fish you catch, and since
                // you're on your phone busy tracking how long you've spent fishing I'm gonna guess not too many.
                // 1 fish per hour * 100 calories per fish. Probably generous.
                return 100 * ((float)duration / 60 / 60);
            case "Polevaulting":
                // So, I ended up finding that same table except with a lot more entries and on a completely different
                // website. www.nutritiontwins.com attributes the data to www.nutritionstrategy.com, which has no information
                // whatsoever about where the data comes from. In addition to the previous oddity mentioned above, all
                // entries in the table increase from weight class to weight class by a constant amount (i.e., Pole Vaulting
                // increases from 130lbs at 354 calories to 155lbs at 422 (a difference of 68 calories), then up to 490 at
                // 180 lbs and so on). The magnitude changes from activity to activity, but the shape of the graph remains
                // identical, which seems incredibly unlikely, especially given such classic entries as "Tailoring, general"
                // and "Typing, computer data entry". I think this data may have been completely made up. What is the state
                // of journalism in this country?
                // y = 2.8x - 10
                return (float)(2.8 * weight - 10) * ((float)duration / 60 / 60);
            case "Skydiving":
                // Before you ask, yes, Sky Diving is in that table and no, I'm not going to use it. You violated my
                // trust one too many times, www.nutritionstrategy.com.
                // So how many calories does sky diving burn? Well, you're really just sitting there, so let's say 0.
                // Unless, of course, your parachute fails, in which case we'll say "all of them".
                if (duration < 120) {
                    // Sky Diving is about 60 seconds of free fall followed by 5-7 minutes of parachute landing
                    // If you finished in under two minutes, I'm going to assume you're dead.
                    // (Although actually, the ratio of sky diving accidents that result in death is, according
                    // to the Boston Globe, [paywall]).
                    // calories in the human body = ~110,000 for "the average person" while "making a lot of unjustified
                    // assumptions" which honestly sounds a lot like what I'm doing so I think this guy's legit
                    return (float)(625 * weight);
                } else
                    return 0;
        }

        // I have finished writing this function, and it is now 5:00 in the morning.
        return 0;
    }

    private int getDistance(String activity, int duration) {
        // Oh no you don't; I'm not falling for this one again.
        // Ideally, we would just grab starting and ending locations, but since we don't actually have a GPS
        // I think that number isn't changing
        return duration / 60;
    }
}
