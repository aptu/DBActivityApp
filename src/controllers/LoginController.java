package controllers;

import db.DBManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import scene.SceneHolder;

import java.sql.SQLException;


public class LoginController {

    public TextField userInput;
    public PasswordField password_input;

    public void onLogin(ActionEvent actionEvent) {
        login();
    }

    @FXML
    public void onEnter(KeyEvent keyEvent){
        if (keyEvent.getCode() == KeyCode.ENTER)
            login();
    }

    private void login()
    {
        //TODO: double check that the user exists (we don't care about passwords)
        boolean succeeded = false;

        try {
            DBManager.db.connect(password_input.getText());
            succeeded = DBManager.db.login(userInput.getText());
            System.out.println(String.format("Login succeeded = %s", succeeded));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        if(succeeded)
            SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
        else
        {
            //something later
        }

        userInput.setText("");
    }
}
