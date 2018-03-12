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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    List<ImageView> activityMarkers = new ArrayList<ImageView>();
    Image activityMarkerImage;
    Image selectedActivityMarkerImage;
    Image waypointMarkerImage;


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

            activityMarkerImage = new Image(getClass().getResourceAsStream("ActivityLocation.png"));
            selectedActivityMarkerImage = new Image(getClass().getResourceAsStream("SelectedLocation.png"));
            waypointMarkerImage = new Image(getClass().getResourceAsStream("Waypoint.png"));

            isLoaded = true;
        }
    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }

    public void findActivities(ActionEvent actionEvent) throws SQLException {
        activityMarkers.clear();
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
            activityLocation.setImage(activityMarkerImage);
            activityLocation.setX(activity.getLongitude() - ControllerHolder.ActivityOffset);
            activityLocation.setY(activity.getLatitude() - ControllerHolder.ActivityOffset);
            scrollCanvas.getChildren().add(activityLocation);

            activityMarkers.add(activityLocation);
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

    public void getSelected(MouseEvent mouseEvent) throws SQLException {
        if(activities.size() > 0) {

            List<Integer> index = eventList.getSelectionModel().getSelectedIndices();
            scrollCanvas.getChildren().clear();
            scrollCanvas.getChildren().add(mapImage);
            LocatableActivityItem selected = activities.get(index.get(0));

            loadWaypoints(selected.getLocationId());


            for(ImageView iv: activityMarkers) {
                iv.setImage(activityMarkerImage);
                scrollCanvas.getChildren().add(iv);
            }

            scrollCanvas.getChildren().add(userLocation);
            activityMarkers.get(index.get(0)).setImage(selectedActivityMarkerImage);

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


    private void loadWaypoints(int LocID) throws SQLException {

        PreparedStatement statement = DBManager.db.connection.prepareStatement("select Longitute, Lattitude from ActivityApp.WayPoints where LocationID = ? order by SeqNumber");
        statement.setInt(1, LocID);

        int latitude, longitude;
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            latitude = result.getInt("Lattitude");
            longitude = result.getInt("Longitute");

            ImageView waypoint = new ImageView(waypointMarkerImage);
            waypoint.setY(latitude);
            waypoint.setX(longitude);
            scrollCanvas.getChildren().add(waypoint);
        }


    }
}

