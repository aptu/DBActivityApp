package controllers;

import db.Event;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import scene.SceneHolder;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import db.DBManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FindEventController {

    public ListView eventList;
    public ChoiceBox distanceSelect;
    public Button findEventsButton;
    public CheckBox allActivitiesBox;
    public ChoiceBox activitySelect;
    public CheckBox happeningNowBox;
    public DatePicker startTimeSelect;
    public DatePicker endTimeSelect;
    public Pane mapCanvas;
    public ImageView mapImage;
    public ScrollPane mapScrollPane;
    public Button attendEventsButton;

    ImageView userLocation;
    List<ImageView> eventMarkers = new ArrayList<ImageView>();
    Image eventMarkerImage;
    Image selectedEventMarkerImage;

    List<Event> events = new ArrayList<>();


    private boolean isLoaded = false;

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.eventScene);
    }

    public void loadSettings(){
        if (!isLoaded) {

            ObservableList<String> list = FXCollections.observableArrayList();
            list.add("1 mile");
            list.add("5 miles");
            list.add("10 miles");
            distanceSelect.setItems(list);
            distanceSelect.setValue(list.get(0));

            list = FXCollections.observableArrayList();
            for (String s : DBManager.db.getAllActivities()) {
                list.add(s);
            }

            activitySelect.setItems(list);
            activitySelect.setValue(list.get(0));

            userLocation = new ImageView();
            userLocation.setImage(new Image(getClass().getResourceAsStream("UserLocation.png")));
            mapCanvas.getChildren().add(userLocation);

            eventMarkerImage = new Image(getClass().getResourceAsStream("ActivityLocation.png"));
            selectedEventMarkerImage = new Image(getClass().getResourceAsStream("SelectedLocation.png"));

            isLoaded = true;
        }

        startTimeSelect.setValue(LocalDate.now());
        endTimeSelect.setValue(LocalDate.now());

        userLocation.setX(ControllerHolder.UserLocationX - 30);
        userLocation.setY(ControllerHolder.UserLocationY - 30);

        double vPercent = (ControllerHolder.UserLocationY - 30 - 68.75) /580.0;
        vPercent = (vPercent < 0)?0:vPercent;
        vPercent = (vPercent > 1)?1:vPercent;

        double hPercent = (ControllerHolder.UserLocationX - 30 - 84) / 800.0;
        hPercent = (hPercent < 0)?0:hPercent;
        hPercent = (hPercent > 1)?1:hPercent;

        mapScrollPane.setHvalue(hPercent);
        mapScrollPane.setVvalue(vPercent);
    }

    public void useActivity(ActionEvent actionEvent) {
        if(allActivitiesBox.isSelected())
        {
            activitySelect.setDisable(true);
        }
        else
        {
            activitySelect.setDisable(false);
        }
    }
    public void findEvents(ActionEvent actionEvent) throws SQLException {
        eventMarkers.clear();
        mapCanvas.getChildren().clear();
        mapCanvas.getChildren().add(mapImage);
        int distancePerMile = 50;
        int distance;
        switch (distanceSelect.getValue().toString()) {
            case "1 mile":
                distance = distancePerMile;
                break;
            case "5 miles":
                distance = 5*distancePerMile;
                break;
            case "10 miles":
                distance = 10*distancePerMile;
                break;
            default:
                distance = distancePerMile;
                break;
        }

        //give it just a little bit of padding
        distance += 10;

        ObservableList<String> list = FXCollections.observableArrayList();

        LocalDate startDate = null;
        LocalDate endDate = null;

        if(!happeningNowBox.isSelected()) {
            startDate = startTimeSelect.getValue();
            endDate = endTimeSelect.getValue();
        }


        if(allActivitiesBox.isSelected()) {
            events = DBManager.db.findAllEvents(distance, happeningNowBox.isSelected(), startDate, endDate);
        }
        else
            events = DBManager.db.findEvents(activitySelect.getValue().toString(), distance, happeningNowBox.isSelected(), startDate, endDate);

        for(Event event: events)
        {
            ImageView eventLocation = new ImageView();
            eventLocation.setImage(eventMarkerImage);
            eventLocation.setX(event.getLongitude() - ControllerHolder.ActivityOffset);
            eventLocation.setY(event.getLatitude() - ControllerHolder.ActivityOffset);
            mapCanvas.getChildren().add(eventLocation);

            eventMarkers.add(eventLocation);
            list.add(event.getActivityName()+": "+event.getEventName());
        }

        mapCanvas.getChildren().add(userLocation);

        eventList.setItems(list);
    }

    public void attendEvent(ActionEvent actionEvent) throws SQLException {
        List<Integer> index = eventList.getSelectionModel().getSelectedIndices();
        Event selected = events.get(index.get(0));

        DBManager.db.addUserAttendingEvent(selected.getEventId());
        attendEventsButton.setDisable(true);
        findEvents(null);

    }

    public void happenNowAction(ActionEvent actionEvent) {
        if(happeningNowBox.isSelected()){
            startTimeSelect.setDisable(true);
            endTimeSelect.setDisable(true);
        }
        else {
            startTimeSelect.setDisable(false);
            endTimeSelect.setDisable(false);

        }
    }

    public void getSelected(MouseEvent mouseEvent) {
        if(events.size() > 0) {

            List<Integer> index = eventList.getSelectionModel().getSelectedIndices();
            mapCanvas.getChildren().clear();
            mapCanvas.getChildren().add(mapImage);
            Event selected = events.get(index.get(0));

            for(ImageView iv: eventMarkers) {
                iv.setImage(eventMarkerImage);
                mapCanvas.getChildren().add(iv);
            }

            mapCanvas.getChildren().add(userLocation);
            eventMarkers.get(index.get(0)).setImage(selectedEventMarkerImage);

            double vPercent = (selected.getLatitude() - ControllerHolder.ActivityOffset - 68.75) / 580.0;
            vPercent = (vPercent < 0) ? 0 : vPercent;
            vPercent = (vPercent > 1) ? 1 : vPercent;

            double hPercent = (selected.getLongitude() - ControllerHolder.ActivityOffset - 84) / 800.0;
            hPercent = (hPercent < 0) ? 0 : hPercent;
            hPercent = (hPercent > 1) ? 1 : hPercent;

            mapScrollPane.setVvalue(vPercent);
            mapScrollPane.setHvalue(hPercent);

            attendEventsButton.setDisable(false);
        }
    }
}
