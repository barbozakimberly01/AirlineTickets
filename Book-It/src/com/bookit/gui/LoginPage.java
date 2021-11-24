package com.bookit.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginPage extends Application {

	public static void main(String[] args) {
		// Left blank to see what .fxml will produce
		launch(args);

	}
	
	@Override
	public void start(Stage stage) throws Exception {
	   Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));

	   	Scene scene = new Scene(root, 1024, 768);
	   	
	   	//May not need to use .css stylesheet
//	   	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	    stage.setTitle("BookIt");
	    stage.setScene(scene);
	    stage.show();
	}

}
