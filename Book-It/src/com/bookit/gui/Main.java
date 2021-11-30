package com.bookit.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	 static Parent root;
	 static Stage primaryStage;
	static Parent getRoot() {

        return root;
    }

    static void setRoot(Parent root) {

        Main.root = root;
    }

    static Stage getStage() {

        return primaryStage;
    }

    static void setStage(Stage stage) {

        Main.primaryStage = stage;
    }
	
	@Override
	
	public void start(Stage primaryStage) {
		try {
			 //Parent root = FXMLLoader.load(getClass().getResource("Splashscreen.fxml"));
			//--------
			Parent root = FXMLLoader.load(getClass().getResource("ManageFlights.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("UserBookings.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("Search.fxml"));
			 
			// --------
			 
			Scene scene = new Scene(root, 1200, 650);
			Main.primaryStage = primaryStage;
			primaryStage.setTitle("Book-It Airline Reservation System");
			scene.getRoot().setStyle("-fx-font-family: 'Helvetica'");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
