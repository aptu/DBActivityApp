package controllers;

import db.ActivityHistoryItem;
import db.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import scene.SceneHolder;

import java.sql.SQLException;
import java.util.List;

public class HistoryController {

    public ListView historyList;
    public ScrollPane mapScrollPane;
    public ImageView activityLocationMarker;
    private List<ActivityHistoryItem> userHistory;

    public void loadHistory() throws SQLException {
        activityLocationMarker.setVisible(true);
        userHistory = DBManager.db.getUserHistory();
        if(userHistory.size() > 0) {

            ObservableList<String> list = FXCollections.observableArrayList();
            for (ActivityHistoryItem hist : DBManager.db.getUserHistory()) {
                list.add(hist.getActivityName());
            }

            historyList.setItems(list);

            historyList.getSelectionModel().selectFirst();

            selectItem(null);
        }
        else {
            activityLocationMarker.setVisible(false);
        }
    }

    public void goToMenu(ActionEvent actionEvent) {
        SceneHolder.primaryStage.setScene(SceneHolder.mainMenuScene);
    }

    public void selectItem(MouseEvent mouseEvent) {
        if(userHistory.size() > 0) {
            List<Integer> index = historyList.getSelectionModel().getSelectedIndices();

            ActivityHistoryItem selected = userHistory.get(index.get(0));

            activityLocationMarker.setX(selected.getLatitude() - ControllerHolder.ActivityOffset);
            activityLocationMarker.setY(selected.getLongitude() - ControllerHolder.ActivityOffset);

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
