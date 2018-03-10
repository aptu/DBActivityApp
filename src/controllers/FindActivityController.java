package controllers;

import db.DBManager;
import db.LocatableActivityItem;
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
import java.util.ArrayList;
import java.util.List;

public class FindActivityController {

    public Button backButton;
    public ListView eventList;
    public ChoiceBox distanceList;
    public Button findActivityButton;
    public CheckBox activityCheckbox;
    public ChoiceBox activitySelect;
    public ScrollPane mapScrollPane;
    public Pane scrollCanvas;
    public ImageView mapImage;

    ImageView userLocation;


    boolean isLoaded = false;
    List<LocatableActivityItem> activities = new ArrayList<LocatableActivityItem>();


    public void loadSettings(){
        if (!isLoaded) {

            ObservableList<String> list = FXCollections.observableArrayList();
            list.add("1 mile");
            list.add("5 miles");
            list.add("10 miles");
            distanceList.setItems(list);
            distanceList.setValue(list.get(0));

            list = FXCollections.observableArrayList();
            for (String s : DBManager.db.getAllActivities()) {
                list.add(s);
            }
            activitySelect.setItems(list);
            activitySelect.setValue(list.get(0));

            userLocation = new ImageView();
            userLocation.setImage(new Image(getClass().getResourceAsStream("UserLocation.png")));
            scrollCanvas.getChildren().add(userLocation);

            userLocation.setX(ControllerHolder.UserLocationX - 30);
            userLocation.setY(ControllerHolder.UserLocationY - 30);

            double vPercet = (ControllerHolder.UserLocationY - 30 - 68.75) /580.0;
            vPercet = (vPercet < 0)?0:vPercet;
            vPercet = (vPercet > 1)?1:vPercet;

            double hPecent = (ControllerHolder.UserLocationX - 30 - 84) / 800.0;
            hPecent = (hPecent < 0)?0:hPecent;
            hPecent = (hPecent > 1)?1:hPecent;


            mapScrollPane.setHvalue(hPecent);
            mapScrollPane.setVvalue(vPercet);

            isLoaded = true;
        }
    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }

    public void findActivities(ActionEvent actionEvent) throws SQLException {
        scrollCanvas.getChildren().clear();
        scrollCanvas.getChildren().add(mapImage);

        ObservableList<String> list = FXCollections.observableArrayList();
        if(activityCheckbox.isSelected())
            activities = DBManager.db.findAllActivities();
        else
            activities = DBManager.db.findActivity(activitySelect.getValue().toString());

        for(LocatableActivityItem activity: activities)
        {
            ImageView activityLocation = new ImageView();
            activityLocation.setImage(new Image(getClass().getResourceAsStream("ActivityLocation.png")));
            activityLocation.setX(activity.getLongitude() - ControllerHolder.ActivityOffset);
            activityLocation.setY(activity.getLatitude() - ControllerHolder.ActivityOffset);
            scrollCanvas.getChildren().add(activityLocation);

            list.add(activity.getLocName());
        }

        scrollCanvas.getChildren().add(userLocation);

        eventList.setItems(list);
    }

    public void useActivity(ActionEvent actionEvent) {
        if(activityCheckbox.isSelected())
        {
            activitySelect.setDisable(true);
        }
        else
        {
            activitySelect.setDisable(false);
        }
    }

    public void getSelected(MouseEvent mouseEvent) {
        if(activities.size() > 0) {
            List<Integer> index = eventList.getSelectionModel().getSelectedIndices();

            LocatableActivityItem selected = activities.get(index.get(0));

            double vPercet = (selected.getLatitude() - ControllerHolder.ActivityOffset - 68.75) / 580.0;
            vPercet = (vPercet < 0) ? 0 : vPercet;
            vPercet = (vPercet > 1) ? 1 : vPercet;

            double hPecent = (selected.getLongitude() - ControllerHolder.ActivityOffset - 84) / 800.0;
            hPecent = (hPecent < 0) ? 0 : hPecent;
            hPecent = (hPecent > 1) ? 1 : hPecent;

            mapScrollPane.setVvalue(vPercet);
            mapScrollPane.setHvalue(hPecent);
        }


    }
}
