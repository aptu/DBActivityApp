package controllers;

import db.ActivityHistoryItem;
import db.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import scene.SceneHolder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HistoryController {

    public ListView<String> historyList;
    public ScrollPane mapScrollPane;
    public ImageView activityLocationMarker;
    public Label historyText;
    public Label aggregateText;
    private List<ActivityHistoryItem> userHistory;

    public void loadHistory() throws SQLException {
        activityLocationMarker.setVisible(true);
        userHistory = DBManager.db.getUserHistory();
        if(userHistory.size() > 0) {

            ObservableList<String> list = FXCollections.observableArrayList();
            for (ActivityHistoryItem hist : DBManager.db.getUserHistory()) {
                list.add(hist.getActivityName());
            }

            historyList.setItems(list);

            historyList.getSelectionModel().selectFirst();

            selectItem(null);
        }
        else {
            activityLocationMarker.setVisible(false);
        }

    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }

    public void selectItem(MouseEvent mouseEvent) {
        if(userHistory.size() > 0) {
            List<Integer> index = historyList.getSelectionModel().getSelectedIndices();

            ActivityHistoryItem selected = userHistory.get(index.get(0));

            activityLocationMarker.setX(selected.getLatitude() - ControllerHolder.ActivityOffset);
            activityLocationMarker.setY(selected.getLongitude() - ControllerHolder.ActivityOffset);

            double vPercent = (selected.getLatitude() - ControllerHolder.ActivityOffset - 68.75) / 580.0;
            vPercent = (vPercent < 0) ? 0 : vPercent;
            vPercent = (vPercent > 1) ? 1 : vPercent;

            double hPercent = (selected.getLongitude() - ControllerHolder.ActivityOffset - 84) / 800.0;
            hPercent = (hPercent < 0) ? 0 : hPercent;
            hPercent = (hPercent > 1) ? 1 : hPercent;

            mapScrollPane.setVvalue(vPercent);
            mapScrollPane.setHvalue(hPercent);

            historyText.setText("Time: " + selected.getDateTime() + "\n" + "Calories burned: " +
                    selected.getCalBurned() + "\n" + "Duration: " + selected.getDuration() + "\n" + "Distance: " + selected.getDistance());

            aggregateText.setText(getAggregateData(selected));
        }
    }

    private String getAggregateData(ActivityHistoryItem historyItem)
    {
        double averageCaloriesBurned = 0;
        double averageDuration = 0;
        double averageDistance = 0;
        int numEntries = 0;

        try {
            PreparedStatement query = DBManager.db.connection.prepareStatement(
                    "SELECT ah.* " +
                    "FROM ActivityApp.ActivityHistory ah, ActivityApp.User u " +
                    "WHERE u.UserID != ? " +
                    "AND u.Gender = ? " +
                    "AND Abs(Date(?) - u.DOB) < Date('5-0-0') " +
                    "AND Abs(? - u.Weight) < 30 " +
                    "AND u.UserID = ah.UserID " +
                    "AND ah.ActivityID = ?;");

            PreparedStatement myInfo = DBManager.db.connection.prepareStatement("" +
                    "select * from ActivityApp.User where UserID = ?;");
            myInfo.setInt(1, DBManager.db.currUserId);

            ResultSet myInfoResults = myInfo.executeQuery();
            myInfoResults.next();
            String myGender = myInfoResults.getString("Gender");
            double weight = myInfoResults.getDouble("Weight");
            Date dob = myInfoResults.getDate("DOB");

            query.setInt(1, DBManager.db.currUserId);
            query.setString(2, myGender);
            query.setDate(3, dob);
            query.setDouble(4, weight);
            query.setInt(5, historyItem.getActivityId());

            ResultSet queryResults = query.executeQuery();

            while (queryResults.next()) {
                numEntries++;

                averageCaloriesBurned += queryResults.getDouble("CalBurned");
                averageDuration += queryResults.getDouble("Duration");
                averageDistance += queryResults.getDouble("Distance");
            }
            averageCaloriesBurned /= numEntries;
            averageDuration /= numEntries;
            averageDistance /= numEntries;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (numEntries == 0)
            return "No data available.\nSign up your friends!";

        return "People like you:\n" + "Calories Burned: " + averageCaloriesBurned + "\n" +
               "Duration: " + averageDuration + "\n" + "Distance: " + averageDistance;
    }
}
