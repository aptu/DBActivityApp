package controllers;

import db.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import scene.SceneHolder;

import java.sql.SQLException;

public class FindActivityController {

    public Button backButton;
    public ListView eventList;
    public ChoiceBox distanceList;
    public Button findActivityButton;
    public CheckBox activityCheckbox;
    public ChoiceBox activitySelect;

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
            for(String s: DBManager.db.getListofAllActivities())
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
