package controllers;

import db.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import scene.SceneHolder;

import java.sql.SQLException;

public class FindActivityController {

    public Button backButton;
    public ListView eventList;
    public ChoiceBox distanceList;
    public Button findActivityButton;
    public CheckBox activityCheckbox;
    public ChoiceBox activitySelect;
    public ScrollPane mapScrollPane;
    public Pane scrollCanvas;

    boolean isLoaded = false;


    public void loadSettings(){
        if (!isLoaded) {
            ObservableList<String> list = FXCollections.observableArrayList();
            list.add("5 Miles");
            list.add("10 Miles");
            distanceList.setItems(list);
            distanceList.setValue(list.get(0));

            list = FXCollections.observableArrayList();
            for (String s : DBManager.db.getAllActivities()) {
                list.add(s);
            }
            activitySelect.setItems(list);
            activitySelect.setValue(list.get(0));

            ImageView userLocation = new ImageView();
            userLocation.setImage(new Image(getClass().getResourceAsStream("UserLocation.png")));

            userLocation.setX(ControllerHolder.UserLocationX - 30);
            userLocation.setY(ControllerHolder.UserLocationY - 30);
            scrollCanvas.getChildren().add(userLocation);

            double vPercet = (ControllerHolder.UserLocationY - 30) /580.0;
            vPercet = (vPercet < 0)?0:vPercet;
            vPercet = (vPercet > 1)?1:vPercet;

            double hPecent = (ControllerHolder.UserLocationX - 30) / 800.0;
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

        ObservableList<String> list = FXCollections.observableArrayList();
        if(activityCheckbox.isSelected())
        {
            for(String s: DBManager.db.getListOfAllActivities())
            {
                list.add(s);
            }
        }
        else
        {
            String activity = activitySelect.getValue().toString();
            for(String s: DBManager.db.findActivity(activity))
            {
                list.add(s);
            }
        }

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
}
