package com.bookit.gui;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SceneCreator { 
	//Create the stage and scene base
    public static void launchScene (String sceneName) throws IOException { 
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(sceneName));
        Main.setRoot(loader.load());
        Scene scene = new Scene(Main.getRoot(), 1300, 650);
        scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
        Main.getStage().setScene(scene);
        Main.getStage().show();
    }
}