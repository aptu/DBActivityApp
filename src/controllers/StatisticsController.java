package controllers;

import db.DBManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import scene.SceneHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsController {
    public Label statsLabel;
    public ChoiceBox<String> activityTypeBox;

    private boolean isLoaded = false;

    public void loadStats() {

        if (!isLoaded) {
            ObservableList<String> activities = FXCollections.observableArrayList();
            activities.addAll(DBManager.db.getAllActivities());

            activityTypeBox.setItems(activities);

            activityTypeBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                    updateStats(activityTypeBox.getItems().get((Integer) number2));
                }
            });
            isLoaded = true;
        }
    }

    private void updateStats(String activity) {
        try {
            PreparedStatement query = DBManager.db.connection.prepareStatement("" +
                    "select * from ActivityApp.ActivityHistory where UserID = ? " +
                    "and ActivityID = ?");

            query.setInt(1, DBManager.db.currUserId);
            query.setInt(2, DBManager.db.getActivityIdByType(activity));

            ResultSet results = query.executeQuery();

            int count = 0;

            int sumCalories = 0;
            int sumDuration = 0;
            int sumDistance = 0;

            double averageCalories = 0;
            double averageDuration = 0;
            double averageDistance = 0;


            while (results.next()) {
                count++;
                sumCalories += results.getInt("CalBurned");
                sumDuration += results.getDouble("Duration");
                sumDistance += results.getDouble("Distance");
            }

            String text = "";

            if (count != 0) {
                averageCalories = (double)sumCalories / count;
                averageDuration = (double)sumDuration / count;
                averageDistance = (double)sumDistance / count;

                text += "Total times you've participated in " + activity + ": " + count + "\n\n";

                text += "Total calories burned while " + activity + ": " + sumCalories + "\n";
                text += "Average calories burned while " + activity + ": " + averageCalories + "\n\n";

                text += "Total time spent " + activity + ": " + sumDuration + "\n";
                text += "Average time spent " + activity + ": " + averageDuration + "\n\n";

                text += "Total distance traveled while " + activity + ": " + sumDistance + "\n";
                text += "Average distance traveled while " + activity + ": " + averageDistance + "\n\n";

            } else {
                text = "No data available.\nGo do some activities!";
            }

            statsLabel.setText(text);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }
}
