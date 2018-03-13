package controllers;

import db.DBManager;
import db.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import scene.SceneHolder;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

public class CreateEventController {
    public Pane mapCanvas;
    public ScrollPane mapScrollPane;
    public ChoiceBox activitySelect;
    public ImageView userLocation;
    public TextField eventName;
    public DatePicker startTimeSelect;
    public DatePicker endTimeSelect;

    boolean isLoaded = false;

    public void loadSettings() {
        if(!isLoaded) {
            userLocation = new ImageView();
            userLocation.setImage(new Image(getClass().getResourceAsStream("ActivityLocation.png")));
            userLocation.setX(ControllerHolder.UserLocationX - ControllerHolder.ActivityOffset);
            userLocation.setY(ControllerHolder.UserLocationY - ControllerHolder.ActivityOffset);
            mapCanvas.getChildren().add(userLocation);

            double vPercet = (ControllerHolder.UserLocationY - 30 - 68.75) /580.0;
            vPercet = (vPercet < 0)?0:vPercet;
            vPercet = (vPercet > 1)?1:vPercet;

            double hPecent = (ControllerHolder.UserLocationX - 30 - 84) / 800.0;
            hPecent = (hPecent < 0)?0:hPecent;
            hPecent = (hPecent > 1)?1:hPecent;


            mapScrollPane.setHvalue(hPecent);
            mapScrollPane.setVvalue(vPercet);

            // Populate user activity checkbox
            loadUserActivity();

            isLoaded = true;
        }
    }


    public void createEvent(ActionEvent actionEvent) {
        String type = (String) activitySelect.getSelectionModel().getSelectedItem();
        int activityID;
        try {
            activityID = DBManager.db.getActivityIdByType(type);
        } catch (SQLException e) {
            System.out.println("Error getting activityId from DB");
            return;
        }
        double longitude = userLocation.getX();
        double latitude = userLocation.getY();
        String name = eventName.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.SMART);
        LocalDateTime startTime = LocalDate.parse(startTimeSelect.getValue().toString(), formatter).atStartOfDay();
        LocalDateTime endTime = LocalDate.parse(endTimeSelect.getValue().toString(), formatter).atStartOfDay();
        Event event = new Event(activityID, startTime, endTime, name, latitude, longitude);

        try {
            int eventID = DBManager.db.createEvent(event);
            DBManager.db.addUserAttendingEvent(eventID);
        } catch(SQLException e) {
            System.out.println("Error creating event or adding an event to users" + e + e.getMessage());
        }

    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.eventScene);
    }

    public void getLocation(MouseEvent mouseEvent) {
        //use this to differentiate a click form a pan
        if(mouseEvent.isStillSincePress()) {
            userLocation.setX(mouseEvent.getX() - ControllerHolder.ActivityOffset);
            userLocation.setY(mouseEvent.getY() - ControllerHolder.ActivityOffset);
        }
    }

    public void loadUserActivity() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (String s : DBManager.db.getAllActivities()) {
            list.add(s);
        }

        activitySelect.setItems(list);
        activitySelect.setValue(list.get(0));
    }
}
