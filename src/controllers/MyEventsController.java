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
    private boolean isLoaded = false;
    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.eventScene);
    }

    public void cancelEvent(ActionEvent actionEvent) {
        
    }

    public void loadEvents() {
        if (!isLoaded) {
            try {
                List<Event> events = DBManager.db.getUserEvents();
                ObservableList<String> list = FXCollections.observableArrayList();
                for (Event e : events) {
                    list.add(e.getEventName() + " " + e.getStartTime() + " " + e.getEndTime());
                }
                eventList.setItems(list);
            } catch(SQLException e) {
                System.out.println("Error getting user events from DB");
            }

            isLoaded = true;
        }
    }
}
