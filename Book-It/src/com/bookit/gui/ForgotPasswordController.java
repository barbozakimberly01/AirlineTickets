package com.bookit.gui;

import java.io.IOException;

import com.bookit.common.User;
import com.bookit.db.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ForgotPasswordController {
	@FXML
	private Button submit;
	@FXML
	private Button backToLogin;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField securityAnswerField;	
	@FXML
	private TextField securityQuestionField;	
	@FXML
	private Label wrongSecurityAnswer;
	@FXML
	private Label showPassword;
	public static User user;
	@FXML
	
	//Connect to the database, Ensure that the username field and security question field are not blank and null, and display password if
	//the user inputs the correct security answer.
	private void validateSecurityAnswer(Event event) {
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
		user = new User( 0, null, null, null, null, null, null, usernameField.getText(), null, null, null, securityQuestionField.getText(), securityAnswerField.getText(), false);
		try {
			DataAccess dataAccess = new DataAccess();
			if(dataAccess.validSecurityAnswer(user)) {
				showPassword.setText("Your password is " + user.getPassword());
				showPassword.setVisible(true);
				wrongSecurityAnswer.setVisible(false);
			}
			else {
				wrongSecurityAnswer.setVisible(true);
				showPassword.setVisible(false);
			}		
	    }  
		catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
	}

	@FXML
	//Take the user back to the Login Scene
	private void backToLogin(Event event) {
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
