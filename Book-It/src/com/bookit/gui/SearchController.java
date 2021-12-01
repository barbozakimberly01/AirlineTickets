package com.bookit.gui;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.stage.Stage.*;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.table.DefaultTableModel;

import com.bookit.common.Bookings;
import com.bookit.common.SearchFlight;
import com.bookit.db.*;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class SearchController {
	@FXML
	private TextField leavingFrom;
	@FXML
	private TextField goingTo;
	@FXML
	private DatePicker departureDate;
	@FXML
	private Button searchButton;
	@FXML
	private Button completeBookingButton;
	@FXML
	private Button manageFlights;
	@FXML
	private Label lblFlightAirline;
	@FXML
	private Label lblFlightOrigination;
	@FXML
	private Label lblFlightDestination;
	@FXML
	private Label lblFlightDepartureDate;
	@FXML
	private Label lblPassengerName;
	@FXML
	private Label lblDepartureTime;
	@FXML
	private Label lblFlightID;	
	@FXML
	private TextField nameOnCardField;
	@FXML
	private TextField creditCardNumberField;
	@FXML
	private TextField expirationDateField;
	@FXML
	private TextField cVVField;
	@FXML
    private ObservableList<ObservableList> data;
	@FXML
	private TableView<SearchFlight> flightResultsView = new TableView();
	
	Preferences userInfo = Preferences.userRoot();
	private String userName = userInfo.get("Username", "");
	private String sSN = userInfo.get("SSN", "");
	private String firstName = userInfo.get("FirstName", "");
	private String lastName = userInfo.get("LastName", "");

	public void manageFlights(Event event) {
		try {
			SceneCreator.launchScene("/com/bookit/gui/ManageFlights.fxml");
			}
			catch (Exception e) {
		        System.out.println(e);
		        e.printStackTrace();
		    }
	}
	
	// Read user input and search for flights
	public void searchFlights(Event event){
		searchButton.setOnAction(new EventHandler<ActionEvent>()
	    {
	        public void handle(ActionEvent e)
	        {
	        	try {
	        		DataAccess.GetConnecton();
	        		}
	        		catch(Exception err) {	
	        			return;
	        		}
	        		SearchFlight search = new SearchFlight(leavingFrom.getText(), goingTo.getText(), departureDate.getValue());
	        		
	        		try {
	        			SearchFlight(search);
	        			System.out.println("Test");
	        		}
	        		catch (Exception error) {
	        			System.out.println("Test2");
	        			System.out.println(error.getMessage());
	        		}
	        }
	    });
	}
	
	//Get flight results from database and display
	public void SearchFlight(SearchFlight Flight) throws SQLException{
		Connection con = DataAccess.GetConnecton();
		try {
			PreparedStatement preparedStmt = con.prepareStatement(SQLStatements.SEARCHFLIGHT);
		    preparedStmt.setString(1, Flight.getOrigination());
		    preparedStmt.setString(2, Flight.getDestination());
		    LocalDate departDate = LocalDate.of(Flight.getDepartureDate().getYear(),Flight.getDepartureDate().getMonth(), Flight.getDepartureDate().getDayOfMonth());
		    Date date = Date.valueOf(departDate);
		    preparedStmt.setDate(3, date);
		    ResultSet rs = preparedStmt.executeQuery(); 
		    //ResultSetMetaData rsm = (ResultSetMetaData) rs.getMetaData();
		    //int count = rsm.getColumnCount();
		    
		    flightResultsView.getItems().clear();
		    flightResultsView.getColumns().clear();
		    TableColumn flightIDColumn = new TableColumn("FlightID");
	    	flightIDColumn.setCellValueFactory(new PropertyValueFactory<>("FlightID"));
	    	flightIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    	
	    	TableColumn airlineColumn = new TableColumn("Airline");
	    	airlineColumn.setCellValueFactory(new PropertyValueFactory<>("Airline"));
	    	airlineColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    	
	    	TableColumn flightNumberColumn = new TableColumn("FlightNumber");
	    	flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("FlightNumber"));
	    	flightNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    	
	    	TableColumn OriginationColumn = new TableColumn("Origination");
	    	OriginationColumn.setCellValueFactory(new PropertyValueFactory<>("Origination"));
	    	OriginationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    	
	    	TableColumn DestinationColumn = new TableColumn("Destination");
	    	DestinationColumn.setCellValueFactory(new PropertyValueFactory<>("Destination"));
	    	DestinationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    	
	    	flightResultsView.setRowFactory( frv -> {
	    		TableRow<SearchFlight> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if(event.getClickCount() >= 1) {
						SearchFlight flightdata = row.getItem();
						System.out.println("Record clicked");
						//System.out.println(flightdata.getAirline() + ", " + flightdata.getFlightNumber() + ", " + flightdata.getOrigination() + ", " + flightdata.getDestination()/* + ", " + flightdata.getDepartureDate()*/);
						lblFlightID.setText(flightdata.getFlightID());
						lblFlightAirline.setText(flightdata.getAirline());
						lblFlightDepartureDate.setText(String.valueOf(flightdata.getFlightNumber()));
						lblFlightOrigination.setText(flightdata.getOrigination()); 
						lblFlightDestination.setText(flightdata.getDestination());
						
						/*Preferences userInfo = Preferences.userRoot();
				    	String userName = userInfo.get("Username", "");
				    	String sSN = userInfo.get("SSN", "");
				    	String firstName = userInfo.get("FirstName", "");
				    	String lastName = userInfo.get("LastName", "");*/
						lblPassengerName.setText(firstName + " " + lastName);
						
					}					
				});
				return row;
			});
	    	
			ObservableList<SearchFlight> flightResultsList = FXCollections.observableArrayList();
			while(rs.next()){
				flightResultsList.addAll(new SearchFlight(String.valueOf(rs.getInt("FlightID")), rs.getString("Airline"), rs.getString("FlightNumber"), rs.getString("Origination"), rs.getString("Destination")));
			}
			con.close();						
			flightResultsView.setItems(flightResultsList);
			flightResultsView.getColumns().addAll(flightIDColumn, airlineColumn, flightNumberColumn, OriginationColumn, DestinationColumn);
		}
		catch (SQLException err) {
	    	System.out.println(err.getMessage());
	    	con.close();
	    }
			
	}
	
	@FXML
	private void CompleteBooking(Event event) {
		try {
			DataAccess.GetConnecton();
			}
			catch(Exception err) {	
				return;
			}
			
		//String nameOnCard = nameOnCardField.getText();
		//String creditCardNumber = creditCardNumberField.getText();
			Bookings booking = new Bookings(0, sSN, lblFlightID.getText(), nameOnCardField.getText(), creditCardNumberField.getText(), expirationDateField.getText(), cVVField.getText());
	    	System.out.println(booking.getNameOnCard());
	    	//TODO-Call a method to insert record into database

			try {
				DataAccess.BookPayFlight(booking);
				System.out.println("Test");
			}
			catch (Exception error) {
				System.out.println("Test2");
				System.out.println(error.getMessage());
			}
	} 
}

