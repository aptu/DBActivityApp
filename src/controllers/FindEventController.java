package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import scene.SceneHolder;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import db.DBManager;

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
    ImageView userLocation;
    List<ImageView> eventMarkers = new ArrayList<ImageView>();
    Image eventMarkerImage;
    Image selectedEventMarkerImage;


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

    public void attendEvent(ActionEvent actionEvent) {
    }
}
