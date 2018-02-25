package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import scene.SceneHolder;

public class LoginController {

    public TextField userInput;

    public void login(ActionEvent actionEvent) {

        //TODO: double check that the user exists (we don't care about passwords)

        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);

        userInput.setText("");

    }
}
