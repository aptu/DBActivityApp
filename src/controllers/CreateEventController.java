package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import scene.SceneHolder;

public class CreateEventController {
    public Pane mapCanvas;
    public ScrollPane mapScrollPane;
    ImageView userLocation;
    boolean isLoaded = false;

    public void loadSettings() {
        if(!isLoaded) {
            userLocation = new ImageView();
            userLocation.setImage(new Image(getClass().getResourceAsStream("ActivityLocation.png")));
            userLocation.setX(ControllerHolder.UserLocationX - ControllerHolder.ActivityOffset);
            userLocation.setY(ControllerHolder.UserLocationY - ControllerHolder.ActivityOffset);
            mapCanvas.getChildren().add(userLocation);

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


    public void createEvent(ActionEvent actionEvent) {

    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.eventScene);
    }

    public void getLocation(MouseEvent mouseEvent) {
        //use this to differentiate a click form a pan
        if(mouseEvent.isStillSincePress()) {
            userLocation.setX(mouseEvent.getX() - ControllerHolder.ActivityOffset);
            userLocation.setY(mouseEvent.getY() - ControllerHolder.ActivityOffset);
        }


    }
}
