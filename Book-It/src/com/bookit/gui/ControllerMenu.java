package com.bookit.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

abstract class ControllerMenu {
	
    @FXML
    Button btnSearchFlights;
    
    @FXML
    Button btnMyFlights;
    
    @FXML
    Button btnLogout;
    
	
	// Book Flights
    @FXML
    void searchFlightsAction(ActionEvent event) {
    	try {
			SceneCreator.launchScene("/com/bookit/gui/Search.fxml");
			} 
    	catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
    } 

    // My Flights
    @FXML
    void myFlightsAction(ActionEvent event) {
    	try {
			SceneCreator.launchScene("/com/bookit/gui/MyFlights.fxml");
			}
		catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
    }

    // Logout
    @FXML
    void logoutAction(ActionEvent event) {
    	try {
			SceneCreator.launchScene("/com/bookit/gui/Login.fxml");
			}
		catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
    }


}
