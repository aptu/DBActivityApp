package controllers;

import db.DBManager;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import scene.SceneHolder;

import java.sql.SQLException;


public class LoginController {

    public TextField userInput;
    public PasswordField password_input;

    public void login(ActionEvent actionEvent) {

        //TODO: double check that the user exists (we don't care about passwords)

        boolean suceeded = false;

        try {
            DBManager.db.connect(password_input.getText());
            suceeded = DBManager.db.login(userInput.getText());
        }
        catch (Exception e)
        {

        }

        if(suceeded)
            SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
        else
        {
            //something later
        }

        userInput.setText("");

    }
}
