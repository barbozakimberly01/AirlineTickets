package com.bookit.gui;

import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.stage.Stage.*;
import com.bookit.db.*;

public class LoginController {
	@FXML
	private Button loginButton;
	@FXML
	private Button forgotPassword;
	@FXML
	private Button signUp;
	public static User user;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	
	
	@FXML
	//Take the user to the SignUp Scene
	private void goToSignUp(Event event) {
		
		try {
	    	SceneCreator.launchScene("/com/bookit/gui/Signup.fxml");

	    } catch (IOException ex) {
	        System.out.println("Error loading humu FXML !");
	        System.out.println(ex.getMessage());

	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    }
	
	}
	
	@FXML
	//Take the user to the ForgotPassword Scene
	private void goToForgotPassword(Event event) {
		try {
	    	SceneCreator.launchScene("/com/bookit/gui/ForgotPassword.fxml");

	    } catch (IOException ex) {
	        System.out.println("Error loading Login FXML !");
	        System.out.println(ex);

	    } catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
	}

	@FXML
	//Connect to database, Ensure that the user is valid then take them to Search Scene.
	private void validateUser(Event event) {
		try {
			DataAccess.GetConnecton();
            System.out.println("CheckConnection true");
        } catch (Exception e) {
            System.out.println("There is an error in CheckConnection() method");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Connection Error");
            alert.setContentText("An error in the process of trying to connect to the database.");
            alert.show();
            return;
        }
		user = new User( 0, null, null, null, null, null, null, usernameField.getText(), passwordField.getText(), null, null, null, null, false);
		try {
			if(DataAccess.validUser(user)) {
	    	SceneCreator.launchScene("/com/bookit/gui/Search.fxml");  	
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("ErrorMessage");
	            alert.setHeaderText("Invalid User Error");
	            alert.setContentText("Invalid Username or Password..");        
	            alert.show();
	            SceneCreator.launchScene("/com/bookit/gui/Login.fxml"); 
			}
	    } catch (IOException ex) {
	        System.out.println("Error loading Login FXML !");
	        System.out.println(ex);

	    } catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
	}
}
