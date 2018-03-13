package controllers;

import db.DBManager;
import db.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import scene.SceneHolder;

import java.sql.SQLException;
import java.util.List;

public class MyEventsController {

    public ListView eventList;
    private List<Event> userEvents;

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.eventScene);
    }

    public void cancelEvent(ActionEvent actionEvent) {
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

    public void loadEvents() {
        try {
            userEvents = DBManager.db.getUserEvents();
            ObservableList<String> list = FXCollections.observableArrayList();
            for (Event e : userEvents) {
                list.add(e.getEventName() + " " + e.getStartTime() + " " + e.getEndTime());
            }
            eventList.setItems(list);
        } catch(SQLException e) {
            System.out.println("Error getting user events from DB");
        }
    }

}
