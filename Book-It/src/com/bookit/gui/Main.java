package com.bookit.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
//***************Combobox
import javafx.scene.layout.BorderPane;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.sql.SQLException;

//***********************



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

     void setStage(Stage stage) {

        Main.primaryStage = stage;
    }
	
    
  //*******COMBOBOX CODE***

	 
	//*********************
    
	@Override
	
	public void start(Stage primaryStage) {
		try {
			 Parent root = FXMLLoader.load(getClass().getResource("Splashscreen.fxml"));
			//--------
			//Parent root = FXMLLoader.load(getClass().getResource("ManageFlights.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("UserBookings.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("Search.fxml"));
			 
			// --------
			
			 
			Scene scene = new Scene(root, 1300, 650);
			Main.primaryStage = primaryStage;
			primaryStage.setTitle("Book-It: Airline Reservation System");
			//scene.getRoot().setStyle("-fx-font-family: 'Helvetica'; -fx-text-fill: '#FFFF00'");
			//scene.getRoot();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			//primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("@Images/arLogo.png")));
			//primaryStage.setIconified(false);
			primaryStage.show();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
