package controllers;

import db.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import scene.SceneHolder;

import java.sql.SQLException;

public class HistoryController {

    public ListView historyList;
    public ScrollPane mapScrollPane;

    public void loadHistory() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (String s : DBManager.db.getUserHistory()) {
            list.add(s);
        }

        historyList.setItems(list);
    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }
}
