package com.bookit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.bookit.db.*;


public class SplashscreenController implements Initializable{
	public Button submitButton;
	
    @Override
    // Delay the the transition for 2 sec, then take to login.
    public void initialize(URL url, ResourceBundle rb) {
        PauseTransition delay = new PauseTransition(Duration.seconds(2.18));
        delay.setOnFinished((ActionEvent event) -> {
            System.out.println("begin");
            goToLoginPage(event);
            System.out.println("goToLogin Done");
        });
        delay.play();    
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

