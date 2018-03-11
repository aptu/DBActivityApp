package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import scene.SceneHolder;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import db.DBManager;

public class FindEventController {

    public ListView eventList;
    public ChoiceBox distanceSelect;
    public Button findEventsButton;
    public CheckBox allActivitiesBox;
    public ChoiceBox activitySelect;
    public CheckBox happeningNowBox;
    public DatePicker startTimeSelect;
    public DatePicker endTimeSelect;

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

            isLoaded = true;
        }
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
}
