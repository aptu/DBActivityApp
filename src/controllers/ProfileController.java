package controllers;

import db.DBManager;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import scene.SceneHolder;

import java.sql.SQLException;
import java.util.*;

public class ProfileController {
    public Label userDOB;
    public Label userName;
    public Label userGender;
    public CheckBox hikingBox;
    public CheckBox bikingBox;
    public CheckBox walkingBox;
    public CheckBox snowboardingBox;
    public CheckBox fishingBox;
    public CheckBox runningBox;
    public CheckBox swimmingBox;
    public CheckBox polevaultingBox;
    public CheckBox skydivingBox;
    public CheckBox skiingBox;

    public void loadUserPreferences() throws SQLException {
        //TODO: query database for user preferences
        userName.setText(DBManager.db.getUserName());
        userDOB.setText(DBManager.db.getDOB());
        boolean[] interests = DBManager.db.getUserInterests();
        userGender.setText(DBManager.db.getGender());

        hikingBox.setSelected(interests[0]);
        bikingBox.setSelected(interests[1]);
        walkingBox.setSelected(interests[2]);
        snowboardingBox.setSelected(interests[3]);
        fishingBox.setSelected(interests[4]);
        runningBox.setSelected(interests[5]);
        swimmingBox.setSelected(interests[6]);
        polevaultingBox.setSelected(interests[7]);
        skydivingBox.setSelected(interests[8]);
        skiingBox.setSelected(interests[9]);
    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }

    public void updateSettings(ActionEvent actionEvent) {

        //TODO: update the user preferences

        try {
            DBManager.db.clearUserInterest();

            if (hikingBox.isSelected())
                DBManager.db.setUserInterest(101);

            if(bikingBox.isSelected())
                DBManager.db.setUserInterest(102);

            if(walkingBox.isSelected())
                DBManager.db.setUserInterest(103);

            if(snowboardingBox.isSelected())
                DBManager.db.setUserInterest(104);

            if(fishingBox.isSelected())
                DBManager.db.setUserInterest(105);

            if(runningBox.isSelected())
                DBManager.db.setUserInterest(106);

            if(swimmingBox.isSelected())
                DBManager.db.setUserInterest(107);

            if(polevaultingBox.isSelected())
                DBManager.db.setUserInterest(108);

            if(skydivingBox.isSelected())
                DBManager.db.setUserInterest(109);

            if(skiingBox.isSelected())
                DBManager.db.setUserInterest(110);
        }
        catch (Exception e){
        }

        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }
}
