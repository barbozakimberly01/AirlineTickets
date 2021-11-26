package com.bookit.gui;

import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.bookit.db.*;

public class SplashscreenController {
	public Button submitButton;
			
	public void handleButtonClick(Event event){
		try {
		goToLoginPage(event);
		}catch (Exception e){
			System.out.println("Error in CheckConnection()");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ErrorMessage");
            alert.setHeaderText("Connection Error");
            alert.setContentText("There was an error while trying to connect to the database ..");        
            alert.show();
            return;
		}
	}
	
	private void goToLoginPage(Event event) {
	    try {
	    	SceneCreator.launchScene("/com/bookit/gui/Login.fxml");
	
	    } catch (IOException ex) {
	        System.out.println("Error loading Login FXML !");
	        System.out.println(ex);
	
	    } catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
	}
}

