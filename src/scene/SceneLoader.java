package scene;

import controllers.ControllerHolder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneLoader {

    public void loadScenes(Class mainClass) throws Exception
    {

        Parent loginSceneLayout = FXMLLoader.load(mainClass.getResource("scene_layouts/login_scene_layout.fxml"));
        SceneHolder.loginScene = new Scene(loginSceneLayout, 402, 786);

        Parent mainMenuSceneLaoyout = FXMLLoader.load(mainClass.getResource("scene_layouts/main_menu_scene_layout.fxml"));
        SceneHolder.mainMenuScene = new Scene(mainMenuSceneLaoyout, 402, 786);


        FXMLLoader loader = new FXMLLoader(mainClass.getResource("scene_layouts/profile_scene_layout.fxml"));
        Parent profileSceneLayout = loader.load();
        SceneHolder.profileScene = new Scene(profileSceneLayout, 402, 786);
        ControllerHolder.profileController = loader.getController();

        loader = new FXMLLoader(mainClass.getResource("scene_layouts/activity_scene_layout.fxml"));
        Parent activitySceneLayout = loader.load();
        SceneHolder.activityScene= new Scene(activitySceneLayout, 402, 786);
        ControllerHolder.activityController = loader.getController();

        Parent findActivitySceneLaoyout = FXMLLoader.load(mainClass.getResource("scene_layouts/find_activity_scene_layout.fxml"));
        SceneHolder.findActivityScene = new Scene(findActivitySceneLaoyout, 402, 786);

        Parent eventSceneLaoyout = FXMLLoader.load(mainClass.getResource("scene_layouts/events_scene_layout.fxml"));
        SceneHolder.eventScene = new Scene(eventSceneLaoyout, 402, 786);

    }
}
