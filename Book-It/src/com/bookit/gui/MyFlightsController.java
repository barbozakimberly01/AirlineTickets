package com.bookit.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.bookit.common.Flight;
import com.bookit.exceptions.ErrorAlerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


public class MyFlightsController extends ControllerMenu implements ErrorAlerts, Initializable {
	
	
	
	
	@FXML
	private TableView<Flight> tblView = new TableView <Flight>(); 
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	void loadMyFlights() {
		try { 
			
			
			
		
		} catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
			
		
	}
	
	
//	TableColumn<Flight, String> column1 = new TableColumn<Flight, String>("FlightNumber");
//	column1.setCellValueFactory(new PropertyValueFactory<>("FlightNumber"));
//	column1.setMinWidth(80);
//	TableColumn<Flight, String> column2 = new TableColumn<Flight, String>("Departure Date");
//	column2.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
//	column2.setMinWidth(100);
//	TableColumn<Flight, String> column3 = new TableColumn<Flight, String>("Departure Time");
//	column3.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
//	column3.setMinWidth(100);
//	TableColumn<Flight, String> column4 = new TableColumn<Flight, String>("Arrival Time");
//	column4.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
//	column4.setMinWidth(100);
//	TableColumn<Flight, String> column5 = new TableColumn<Flight, String>("Flight Duration");
//	column5.setCellValueFactory(new PropertyValueFactory<>("flightDuration"));
//	column5.setMinWidth(100);
//	TableColumn<Flight, String> column6 = new TableColumn<Flight, String>("To");
//	column6.setCellValueFactory(new PropertyValueFactory<>("to"));
//	column6.setMinWidth(115);
//	TableColumn<Flight, String> column7 = new TableColumn<Flight, String>("From");
//	column7.setCellValueFactory(new PropertyValueFactory<>("from"));
//	column7.setMinWidth(115);
//	TableColumn<Flight, String> column8 = new TableColumn<Flight, String>("Airline");
//	column8.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
//	column8.setMinWidth(80);
//	TableColumn<Flight, Integer> column10 = new TableColumn<Flight, Integer>("#Booked");
//	column10.setCellValueFactory(new PropertyValueFactory<>("numBooked"));
//	column10.setMinWidth(75);
//	TableColumn<Flight, Double> column12 = new TableColumn<Flight, Double>("Flight Price");
//	column12.setCellValueFactory(new PropertyValueFactory<>("flight_price"));
//	column12.setMinWidth(80);
//	TableColumn<Flight, String> column13 = new TableColumn<Flight, String>("Boarding Time");
//	column13.setCellValueFactory(new PropertyValueFactory<>("boardingTime"));
//	column13.setMinWidth(110);
//	table.setTableMenuButtonVisible(false);
//	TableColumn<Flight, String> column14 = new TableColumn<Flight, String>("FlightID");
	
	@Override
	public void showErrorAlert(String title, String header, String message) {
		// TODO Auto-generated method stub
		Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();

	}
//	column14.setCellValueFactory(new PropertyValueFactory<>("flightID"));
//	column14.setMinWidth(80);

}
