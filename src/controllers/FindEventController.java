package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import scene.SceneHolder;

public class FindEventController {

    public ListView eventList;
    public ChoiceBox distanceSelect;
    public Button findEventsButton;
    public CheckBox allActivitiesBox;
    public ChoiceBox activitySelect;
    public CheckBox happeningNowBox;
    public DatePicker startTimeSelect;
    public DatePicker endTimeSelect;

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.eventScene);
    }
}
