package controllers;

import db.DBManager;
import db.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import scene.SceneHolder;

import java.sql.SQLException;
import java.util.List;

public class MyEventsController {

    public ListView eventList;
    public ScrollPane mapScrollPane;
    public ImageView mapView;
    public ImageView eventLocationMarker;
    public Label eventInfo;


    private List<Event> userEvents;
    public Label eventText;

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.eventScene);
    }

    public void cancelEvent(ActionEvent actionEvent) throws SQLException {
        if (eventList.getItems().size() > 0) {
            List<Integer> index = eventList.getSelectionModel().getSelectedIndices();
            Event selected = userEvents.get(index.get(0));
            try {
                DBManager.db.cancelEvent(selected.getEventId());
            } catch (SQLException e) {
                System.out.println("Error cancelling user event from DB");
            }

            loadEvents();
        }
    }

    public void loadEvents() throws SQLException {
        eventLocationMarker.setVisible(true);
        userEvents = DBManager.db.getUserEvents();
        if(userEvents.size() > 0) {

            ObservableList<String> list = FXCollections.observableArrayList();
            for (Event event : userEvents) {
                list.add(event.getEventName());
            }

            eventList.setItems(list);

            eventList.getSelectionModel().selectFirst();

            getSelected(null);
        }
        else {
            eventInfo.setText("");
            eventLocationMarker.setVisible(false);
        }
    }

    public void getSelected(MouseEvent mouseEvent) {
        if(userEvents.size() > 0) {
            List<Integer> index = eventList.getSelectionModel().getSelectedIndices();

            Event selected = userEvents.get(index.get(0));

            eventLocationMarker.setX(selected.getLatitude() - ControllerHolder.ActivityOffset);
            eventLocationMarker.setY(selected.getLongitude() - ControllerHolder.ActivityOffset);

            double vPercent = (selected.getLatitude() - ControllerHolder.ActivityOffset - 68.75) / 580.0;
            vPercent = (vPercent < 0) ? 0 : vPercent;
            vPercent = (vPercent > 1) ? 1 : vPercent;

            double hPercent = (selected.getLongitude() - ControllerHolder.ActivityOffset - 84) / 800.0;
            hPercent = (hPercent < 0) ? 0 : hPercent;
            hPercent = (hPercent > 1) ? 1 : hPercent;

            mapScrollPane.setVvalue(vPercent);
            mapScrollPane.setHvalue(hPercent);

            eventInfo.setText("Activity: " + selected.getActivityName() + "\n" +
            "Start Time: " + selected.getStartTime().toString() + "\n" +
            "End Time: " + selected.getEndTime());
        }
    }
}
