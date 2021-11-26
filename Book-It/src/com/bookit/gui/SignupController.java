package com.bookit.gui;
import java.io.IOException;
import com.bookit.db.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignupController {
	@FXML
	private Button submit;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField addressField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField stateField;
	@FXML
	private TextField zipcodeField;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField ssnField;
	@FXML
	private TextField securityAnswerField;
	public static User user;
	
	@FXML
	private void UserSignup(Event event) {
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
		user = new User( 0, firstNameField.getText(), lastNameField.getText(), addressField.getText(), 
				cityField.getText(), stateField.getText(), zipcodeField.getText(), usernameField.getText(), passwordField.getText(),
				emailField.getText(), ssnField.getText(), null, securityAnswerField.getText(), false);
		try {
			if(user.getFirstname() != null && !user.getFirstname().isBlank() && user.getLastname() != null && !user.getLastname().isBlank() && user.getStreetAddress() != null && !user.getStreetAddress().isBlank() && user.getCity() != null
					&& !user.getCity().isBlank() && user.getState() != null && !user.getState().isBlank() && user.getZipcode() != null && !user.getZipcode().isBlank() && user.getUsername() != null && !user.getUsername().isBlank() && user.getPassword() != null
					&& !user.getPassword().isBlank() && user.getEmailAddress() != null && !user.getEmailAddress().isBlank() && user.getSsn() != null && !user.getSsn().isBlank() && user.getSecurityAnswer() != null && !user.getSecurityAnswer().isBlank())  {
				 //TODO: 1- validate SSN input.
				if(DataAccess.UserSignup(user)){
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("ConfirmationMessage");
		            alert.setHeaderText("Signup Confirmation");
		            alert.setContentText("You have signed up successfully.");
		            alert.show();
		            SceneCreator.launchScene("/com/bookit/gui/Login.fxml");
				}
				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
		            alert.setTitle("ErrorMessage");
		            alert.setHeaderText("Invalid Username Error");
		            alert.setContentText("The Username you have entered already exists. Enter a different username.");        
		            alert.show();
				}
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("ErrorMessage");
	            alert.setHeaderText("Required Fields");
	            alert.setContentText("All fields are required.");        
	            alert.show();
			}		
	    }
		catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
	}
}
