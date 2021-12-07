package com.bookit.gui;

import com.bookit.db.DataAccess;
import com.bookit.db.SQLStatements;
import com.bookit.exceptions.ErrorAlerts;
import com.mysql.cj.util.StringUtils;
import javafx.scene.control.Alert;
import java.util.ResourceBundle;
//import javax.swing.JOptionPane;
import java.net.URL;
import com.bookit.common.Flight;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
//import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

public class ManageFlightsController extends ControllerMenu implements ErrorAlerts, Initializable{
    
    @Override
    // Populate the TableView on load 
    public void initialize(URL url, ResourceBundle rb) {	
    	try {
    		lblStatusText.setText("");
        	showTblView();
			}
		catch (Exception e) {
		        System.out.println(e);
		        e.printStackTrace();
	    		lblStatusText.setText("Error loading table!");
		        showErrorAlert("Error",  "Error loading flight data.");
		    }
    }
    
    /************************************
     * 	Header Objects 
     ************************************/
    @FXML
    private Button btnCreateFlight;
    
    @FXML
    private Button btnReloadFlights;
    
    @FXML
    private Label lblStatusText;
    
    /************************************
     * 	Table View Objects 
     ************************************/
    @FXML
    private TableView<Flight> tblView = new TableView<Flight>();
	
    
    /************************************
     * 	Create/Update Flight Objects 
     ************************************/
    @FXML
    private AnchorPane anchorFlight;

    @FXML
    private Label anchorLabel;
    
    @FXML
    private Button btnSubmitFlight;
    
    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtAirline;

    @FXML
    private TextField txtFlightNumber;

    @FXML
    private TextField txtTotalSeats;

    @FXML
    private TextField txtOrigination;

    @FXML
    private TextField txtDepartureDates;
    
    @FXML
    private DatePicker txtDepartureDate;

    @FXML
    private TextField txtDepartureTime;

    @FXML
    private TextField txtDestination;

    @FXML
    private TextField txtArrivalDates;
    
    @FXML
    private DatePicker txtArrivalDate;

    @FXML
    private TextField txtArrivalTime;
    
    private Flight currentFlight;

    /******  Hidden on Create Flight ******/
    @FXML
    private Button btnSubmitUpdate;
    
    @FXML
    private Label lblFlightID;
    
    @FXML
    private Label lblFlightIDValue;
    
        
    /************************************
     * 		Action Events
     ************************************/
    
    public void refreshFlightsAction(ActionEvent event) {
    	
    	try {
    		showTblView();
    		lblStatusText.setText("Flights have been reloaded");
			}
		catch (Exception e) {
		        System.out.println(e);
		        e.printStackTrace();
		        showErrorAlert("Error",  "Error reloading flight data.");
		    }
    }

    @FXML
    public void createFlightAction(ActionEvent event) {
    	
    	try {
    		createFlight();
			}
		catch (Exception e) {
		        System.out.println(e);
		        e.printStackTrace();
		        showErrorAlert("Error",  "Error creating flight.");
		    }
    }
    
    
    @FXML
    public void createFlightSubmitAction(ActionEvent event) {

    	try {
    		submitCreatedFlight();
			}
		catch (Exception e) {
		        System.out.println(e);
		        e.printStackTrace();
		        showErrorAlert("Error",  "Error creating flight.");
		    }
    }
    
    @FXML
    public void updateFlightAction(ActionEvent event) {
    	
    	try {
    		updateFlight(currentFlight);
			}
		catch (Exception e) {
		        System.out.println(e);
		        e.printStackTrace();
		        showErrorAlert("Error",  "Error updating flight.");
		    }
    }
 
    
	/************************************
	 * 	Controller Logic
	 ************************************/
    private enum updateType {
    	update, insert, delete
    }
    
    private void showTblView() {
    	try {
        	anchorFlight.setVisible(false);
        	tblView.setVisible(true);
        	btnCreateFlight.setVisible(true);
        	loadAllFlights();
			}
		catch (Exception e) {
		        System.out.println(e);
		        e.printStackTrace();
		        showErrorAlert("Error",  "Error displaying table view.");
		    }
    }
    
    private void showAnchor() {
    	try {
        	lblStatusText.setText("");
        	tblView.setVisible(false);
        	btnCreateFlight.setVisible(false);
        	showFlightID(false);
    		anchorFlight.setVisible(true);	
			}
		catch (Exception e) {
		        System.out.println(e);
		        e.printStackTrace();
		        showErrorAlert("Error",  "Error displaying anchor view.");
		    }
    } 
    
    private void showFlightID(boolean choice) {
    	try {
    		lblFlightID.setVisible(choice);
        	lblFlightIDValue.setVisible(choice);
			}
		catch (Exception e) {
		        System.out.println(e);
		        e.printStackTrace();
		        showErrorAlert("Error",  "Error displaying FlightID.");
		    }
    }
	
    private void loadAllFlights() {
		
		try {
			
			tblView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			// Execute SQL command
			ResultSet rs = DataAccess.sqlCmd(SQLStatements.ALLFLIGHTS);
			
			if (rs == null) {
				showErrorAlert("Database Connection Error",  "Error occured retrieving flight data.");
			}
			
			if (!rs.isBeforeFirst()) {
				// Check if flights available
				lblStatusText.textFillProperty().setValue(Paint.valueOf("0xf80202ff"));
				lblStatusText.setText("No flights found.");
				System.out.println("Text Fill value: " + lblStatusText.getTextFill());
		        }
			else {
				// Begin loading flights
				tblView.getItems().clear();
				tblView.getColumns().clear();
				ObservableList<Flight> flightResultsList = FXCollections.observableArrayList();
				
	            
	            while(rs.next()){
					
	            	LocalDate localDDate = LocalDate.parse(rs.getString("DepartureDate"));
	            	LocalDate localADate = LocalDate.parse(rs.getString("ArrivalDate"));
	            	
					flightResultsList.addAll(new Flight(
							rs.getInt("FlightID"),
							rs.getString("Airline"),
							rs.getString("FlightNumber"),
							rs.getString("Origination"),
							rs.getString("Destination"),
							localDDate,
							rs.getString("DepartureTime"),
							localADate,
							rs.getString("ArrivalTime"),
							rs.getInt("Price"),
							rs.getInt("TotalSeats")
                		));
	            }
				rs.close();
				
				// Create table columns
				TableColumn<Flight, Integer> colFlightID = new TableColumn<>("FlightID");
				colFlightID.setCellValueFactory(new PropertyValueFactory<>("FlightID"));
				colFlightID.setPrefWidth(70);
				colFlightID.setMaxWidth(colFlightID.getPrefWidth());
				colFlightID.setMinWidth(colFlightID.getPrefWidth());
		    	
	            TableColumn<Flight, String> colAirline = new TableColumn<>("Airline");
	            colAirline.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
		    	
		    	TableColumn<Flight, String> colFlightNumber = new TableColumn<>("FlightNumber");
		    	colFlightNumber.setCellValueFactory(new PropertyValueFactory<>("FlightNumber"));
		    	
	            TableColumn<Flight, String> colOrigination = new TableColumn<>("Origination");
	            colOrigination.setCellValueFactory(new PropertyValueFactory<>("Origination"));
	            colOrigination.setPrefWidth(150);
	            colOrigination.setMaxWidth(colOrigination.getPrefWidth());
	            colOrigination.setMinWidth(colOrigination.getPrefWidth());
		    	
		    	TableColumn<Flight, String> colDestination = new TableColumn<>("Destination");
	            colDestination.setCellValueFactory(new PropertyValueFactory<>("Destination"));
	            colDestination.setPrefWidth(150);
	            colDestination.setMaxWidth(colDestination.getPrefWidth());
	            colDestination.setMinWidth(colDestination.getPrefWidth());
		    	
	            TableColumn<Flight, LocalDate> colDepartureDate = new TableColumn<>("DepartureDate");
	            colDepartureDate.setCellValueFactory(new PropertyValueFactory<>("DepartureDate"));
		    	
		    	TableColumn<Flight, String> colDepartureTime = new TableColumn<>("DepartureTime");
		    	colDepartureTime.setCellValueFactory(new PropertyValueFactory<>("DepartureTime"));
		    	
	            TableColumn<Flight, LocalDate> colArrivalDate = new TableColumn<>("ArrivalDate");
	            colArrivalDate.setCellValueFactory(new PropertyValueFactory<>("ArrivalDate"));
		    	
		    	TableColumn<Flight, String> colArrivalTime = new TableColumn<>("ArrivalTime");
		    	colArrivalTime.setCellValueFactory(new PropertyValueFactory<>("ArrivalTime"));

		    	TableColumn<Flight, Integer> colPrice = new TableColumn<>("Price");
		    	colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
		    	colPrice.setPrefWidth(70);
		    	colPrice.setMaxWidth(colPrice.getPrefWidth());
		    	colPrice.setMinWidth(colPrice.getPrefWidth());
		    	
		    	TableColumn<Flight, Integer> colSeatCount = new TableColumn<>("SeatCount");
		    	colSeatCount.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
		    	colSeatCount.setPrefWidth(80);
		    	colSeatCount.setMaxWidth(colSeatCount.getPrefWidth());
		    	colSeatCount.setMinWidth(colSeatCount.getPrefWidth());
	            
				tblView.setItems(flightResultsList);
				tblView.getColumns().addAll(colFlightID, colAirline, colFlightNumber, colOrigination, colDestination, colDepartureDate, colDepartureTime, colArrivalDate, colArrivalTime, colPrice, colSeatCount);
				tblView.applyCss();
				addButtonToTable("","Delete");
				addButtonToTable("","Edit");
				System.out.println("ResultList: " + flightResultsList.size());
			}

				     
		} catch (SQLException e) {
			
			System.out.println(e);
			showErrorAlert("Database Connection Error", "Error retrieving flight data.");
		}

	} 

    private void addButtonToTable(String columnName, String buttonName) {
        TableColumn<Flight, Void> colBtn = new TableColumn(columnName);

        try {
	        Callback<TableColumn<Flight, Void>, TableCell<Flight, Void>> cellFactory = new Callback<TableColumn<Flight, Void>, TableCell<Flight, Void>>() {
	            @Override
	            public TableCell<Flight, Void> call(final TableColumn<Flight, Void> param) {
	                final TableCell<Flight, Void> cell = new TableCell<Flight, Void>() {
	
	                    private final Button btn = new Button(buttonName);
	                    
	
	                    {
	                        btn.setOnAction((ActionEvent event) -> {
	                        	
	                        	Flight flight = getTableView().getItems().get(getIndex());
	                            System.out.println(buttonName + " selectedData: " + flight);
	                        	
	                        	if(buttonName == "Delete") {
	                        		deleteFlight(flight);
	                        	}
	                        	if(buttonName == "Edit") {
	                        		editFlight(flight);
	                        	}
	                            
	                        });
	                    }
	
	                    @Override
	                    public void updateItem(Void item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (empty) {
	                            setGraphic(null);
	                        } else {
	                            setGraphic(btn);
	                            btn.setPrefWidth(60);
	                            btn.setMaxWidth(btn.getPrefWidth());
	                            btn.setMinWidth(btn.getPrefWidth());
	                        }
	                    }
	                };
	                return cell;
	            }
	        };
	
	        colBtn.setCellFactory(cellFactory);
	        colBtn.setPrefWidth(70);
	        colBtn.setMaxWidth(colBtn.getPrefWidth());
	        colBtn.setMinWidth(colBtn.getPrefWidth());
	
	        tblView.getColumns().add(colBtn);
        } catch (Exception e) {
 	        System.out.println(e);
 	        e.printStackTrace();
 	        showErrorAlert("TableView Error", "Error adding buttons.");
 	    }

    }
    
    private void deleteFlight(Flight flight) {
    	System.out.println("Flight:" + flight);
    	currentFlight = flight;
    	try {
    		
    		// Delete flight
			int result = dbUpdate(updateType.delete);
	    	System.out.println("Delete result:" + result);
	    	if (result == 1) {
	    		showTblView();
				lblStatusText.setText("Flight " + flight.getFlightID() + " has been deleted");
	    	}
	    	else {
	    		lblStatusText.textFillProperty().setValue(Paint.valueOf("0xf80202ff"));
	    		lblStatusText.setText("Error: Flight " + flight.getFlightID() + " was not deleted");
	    	}
        	
    	} catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	        lblStatusText.textFillProperty().setValue(Paint.valueOf("0xf80202ff"));
	        lblStatusText.setText("Error: Flight " + flight.getFlightID() + " was not deleted");
	        showErrorAlert("Error", "Error Occured! Flight " + flight.getFlightID() + " was not deleted"); 
 	    }
    }
    
    private void editFlight(Flight flight) {
    	
    	System.out.println("Flight:" + flight);
    	currentFlight = flight;
    	try {
    	
	    	// Display Edit screen
	    	showAnchor();
	    	anchorLabel.setText("Edit Flight");
	    	showFlightID(true);
	    	
	    	// Show Edit Flight button and hide Create Flight button
	    	btnSubmitFlight.setVisible(false);
	    	btnSubmitUpdate.setVisible(true);
	    	
	    	// Set label and text fields with selected flight data
	    	lblFlightIDValue.setText(flight.toString(flight.getFlightID()));
	    	txtAirline.setText(flight.getAirlineName());
	    	txtFlightNumber.setText(flight.getFlightNumber());
	    	txtOrigination.setText(flight.getOrigination());
	    	txtDestination.setText(flight.getDestination());
	    	txtDepartureDate.setValue(flight.getDepartureDate());
	    	txtDepartureTime.setText(flight.getDepartureTime());
	    	txtArrivalDate.setValue(flight.getArrivalDate());
	    	txtArrivalTime.setText(flight.getArrivalTime());
	    	txtPrice.setText(flight.toString(flight.getPrice()));
	    	txtTotalSeats.setText(flight.toString(flight.getTotalSeats()));
    	
    	} catch (Exception e) {
 	        System.out.println(e);
 	        e.printStackTrace();
 	        showErrorAlert("Edit Error", "Flight " + flight.getFlightID() + " was not edited");
 	    }
    }
    
    private void createFlight() {
    	
    	try {
    	
	    	// Display Create Flight screen
	    	showAnchor();
	    	anchorLabel.setText("Create New Flight");
	    	showFlightID(false);
	    	
	    	// Create all data fields
	    	lblFlightIDValue.setText("");
	    	txtAirline.setText("");
	    	txtFlightNumber.setText("");
	    	txtOrigination.setText("");
	    	txtDestination.setText("");
	    	txtDepartureDate.setValue(null);
	    	txtDepartureTime.setText("");
	    	txtArrivalDate.setValue(null);
	    	txtArrivalTime.setText("");
	    	txtPrice.setText("");
	    	txtTotalSeats.setText("");
	    	
	    	// Show Create Flight button and hide Edit Flight button
	    	btnSubmitUpdate.setVisible(false);
	    	btnSubmitFlight.setVisible(true);
    	
    	} catch (Exception e) {
 	        System.out.println(e);
 	        e.printStackTrace();
 	        showErrorAlert("Create Error", "Flight was not creted");
 	       // return;
 	    }
    }
    
    private boolean passFieldValidation() {
    	boolean status = true; 
    	
    	try {
    		 
    		String dDate = "";
    		String dTime = "";
    		String aDate = "";
    		String aTime = "";
    		
    		// Check date fields are not null
    		if (txtDepartureDate.getValue() == null || txtArrivalDate.getValue() == null) {
    			System.out.println("Check date fields are not null");
    			status = false; 
    			showErrorAlert("Required Fields!", "All fields are required!");
    		}
    		else {
    			dDate = txtDepartureDate.getValue().toString();
        		dTime = txtDepartureTime.getText();
        		aDate = txtArrivalDate.getValue().toString(); 
        		aTime = txtArrivalTime.getText();
    		}
    		
    		// Check fields are not empty/blank
    		if (txtAirline.getText().isBlank() || txtFlightNumber.getText().isBlank() || txtOrigination.getText().isBlank() ||
				txtDestination.getText().isBlank() & dDate.isBlank() || txtDepartureTime.getText().isBlank() ||
				aDate.isBlank() || txtArrivalTime.getText().isBlank() || txtPrice.getText().isBlank() || 
				txtTotalSeats.getText().isBlank()) {
    			System.out.println("Check fields are not empty/blank");
    			status = false; 
    			showErrorAlert("Required Fields!", "All fields are required!");
	    	}
    		// Check format of time fields
			if (dTime.charAt(2) != ':' || dTime.charAt(5) != ':' || aTime.charAt(2) != ':' || aTime.charAt(5)!= ':') {
				System.out.println("Check format of time fields. dTime: " + dTime + "," + " | aTime: " + aTime );
				
				// Add missing leading zero
				if (dTime.charAt(1) == ':') { 
					dTime = "0" + dTime;
					System.out.println("new dTime: " + dTime);
				}
				if ( aTime.charAt(1) == ':') {
					aTime = "0" + aTime;
					System.out.println("new aTime: " + aTime);
				}
				
				if (dTime.charAt(2) != ':' || dTime.charAt(5) != ':' || aTime.charAt(2) != ':' || aTime.charAt(5)!= ':')  {
					status = false; 
					showErrorAlert("Invalid Format", "Invalid Time format!");
				}
			}
			// Check numeric only fields
    		if (Integer.parseInt(txtPrice.getText()) < 1 || 
    				Integer.parseInt(txtTotalSeats.getText()) < 1 || StringUtils.isStrictlyNumeric(txtPrice.getText()) == false || 
    						StringUtils.isStrictlyNumeric(txtTotalSeats.getText()) == false) {
    			System.out.println("Check numeric only fields");
    			status = false; 
    			showErrorAlert("Invalid value!", "Price or Seat Capacity: Enter numeric values only!");
    		}
			// Check values of date
			if (Integer.parseInt(dDate.substring(5, 7)) > 12 || Integer.parseInt(dDate.substring(5, 7)) < 1 || // months
					Integer.parseInt(dDate.substring(8)) > 32 || Integer.parseInt(dDate.substring(8)) < 1 || // days
					Integer.parseInt(aDate.substring(5, 7)) > 12 || Integer.parseInt(aDate.substring(5, 7)) < 1 || // months
					Integer.parseInt(aDate.substring(8)) > 32 || Integer.parseInt(aDate.substring(8)) < 1) { // days
				System.out.println("Check values of date");
				status = false; 
				showErrorAlert("Invalid value!", "Invalid Date value!");
    		}
    		// Check that ArrivalDate is greater than/equal DepartureDate
			if (txtArrivalDate.getValue().compareTo(txtDepartureDate.getValue()) < 0) {
				System.out.println("Check that ArrivalDate is greater than/equal DepartureDate");
				status = false; 
				showErrorAlert("Invalid Dates!", "Arrival Date can not be eariler than Departure Date");
			}
			// Check values of time
			if (Integer.parseInt(dTime.substring(0,2)) > 12 || Integer.parseInt(dTime.substring(0,2)) < 1 || // Departure hours
					Integer.parseInt(dTime.substring(3,5)) > 59 || Integer.parseInt(dTime.substring(3,5)) < 0 || // minutes
					Integer.parseInt(dTime.substring(6, 8)) > 59 || Integer.parseInt(dTime.substring(6, 8)) < 0 || // seconds
					Integer.parseInt(aTime.substring(0,2)) > 12 || Integer.parseInt(aTime.substring(0,2)) < 1 || // Arrival hours
					Integer.parseInt(aTime.substring(3,5)) > 59 || Integer.parseInt(aTime.substring(3,5)) < 0|| // minutes
					Integer.parseInt(aTime.substring(6, 8)) > 59 || Integer.parseInt(aTime.substring(6, 8)) < 0) { // seconds
				System.out.println("Check values of time");
				status = false; 
				showErrorAlert("Invalid value!", "Invalid Time value!");
    		}
    		// Check values of time meridiem
			if ((dTime.substring(9).strip().compareTo("AM") != 0 && dTime.substring(9).strip().compareTo("PM") != 0) || 
					(aTime.substring(9).strip().compareTo("AM") != 0 && aTime.substring(9).strip().compareTo("PM") != 0)) {
				System.out.println("Check values of time meridiem");
				status = false; 
				showErrorAlert("Invalid value!", "Invalid Time meridiem value!");
			}
    			
		}
    	catch (NumberFormatException e) {
			showErrorAlert("Invalid value!", "Price or Seat Capacity: Enter numeric values only!");
		}
    	catch (StringIndexOutOfBoundsException e) {
			showErrorAlert("Invalid value!", "Time value is missing meridiem!");
		}
    	catch (NullPointerException e) {
			showErrorAlert("NULL value encountered!", "All fields are required!");
		}
    	catch(Exception e) {
 	        System.out.println(e);
 	        e.printStackTrace();
 	        showErrorAlert("NULL value encountered!", "All fields are required!");
    	}

    	return status;
    }
       
    private void updateFlight(Flight flight) {
    	
    	try {
    		if (passFieldValidation()){
		    	int result = dbUpdate(updateType.update);
		    	System.out.println("Update result:" + result);
		    	if (result > 0) {
		    		showTblView();
		        	lblStatusText.setText("Flight " + flight.getFlightID() + " has been updated");
		    	}
		    	else {
		    		lblStatusText.setText("Error: Flight " + flight.getFlightID() + " was not updated");
		    	}
    		}
    	} 
    	catch(Exception e) {
 	        System.out.println(e);
 	        e.printStackTrace();
 	        lblStatusText.textFillProperty().setValue(Paint.valueOf("0xf80202ff"));
    		lblStatusText.setText("Error: Flight " + flight.getFlightID() + " was not updated");
	        showErrorAlert("Error", "Flight " + flight.getFlightID() + " was not updated");
    	}
	    
    }
    
    
    private void submitCreatedFlight() {
    	
    	try {
    		
    		if (passFieldValidation()){
    			int result = dbUpdate(updateType.insert);
		    	System.out.println("Create result:" + result);
		    	if (result > 0) {
		    		showTblView();
		        	lblStatusText.setText("Flight has been created");
		    	}
		    	else {
		    		lblStatusText.applyCss();
		    		lblStatusText.textFillProperty().setValue(Paint.valueOf("0xf80202ff"));
		    		lblStatusText.setText("Error: Flight was not created");
		    	}
    		}
    	}
    	catch(Exception e) {
 	        System.out.println(e);
 	        e.printStackTrace();
 	        lblStatusText.textFillProperty().setValue(Paint.valueOf("0xf80202ff"));
    		lblStatusText.setText("Error: Flight was not created");
	        showErrorAlert("Error", "Flight was not created.");
    	}
    }
    
    private int dbUpdate (updateType type) {
    	int result=0;
    	String sqlCmd="";
    
    	try {
    		
    		// Execute SQL command
    		Connection conn = DataAccess.GetConnecton();
    		
    		if (type == updateType.delete) {
    			sqlCmd = String.format(SQLStatements.DELETE, currentFlight.getFlightID());
    		}
    		else if (type == updateType.update) {
    			sqlCmd = String.format(SQLStatements.UPDATEFLIGHT, 
        				txtAirline.getText(),
        				txtFlightNumber.getText(),
        				txtOrigination.getText(),
        				txtDestination.getText(),
        				txtDepartureDate.getValue(),
        				txtDepartureTime.getText(),
        				txtArrivalDate.getValue(),
        				txtArrivalTime.getText(),
        				Integer.parseInt(txtPrice.getText()),
    					Integer.parseInt(txtTotalSeats.getText()),
    					Integer.parseInt(lblFlightIDValue.getText())
        				); 
    		}
    		else {	// Insert
    			sqlCmd = String.format(SQLStatements.NEWFLIGHT, 
        				txtAirline.getText(),
        				txtFlightNumber.getText(),
        				txtOrigination.getText(),
        				txtDestination.getText(),
        				txtDepartureDate.getValue(),
        				txtDepartureTime.getText(),
        				txtArrivalDate.getValue(),
        				txtArrivalTime.getText(),
        				Integer.parseInt(txtPrice.getText()),
    					Integer.parseInt(txtTotalSeats.getText())
        				); 
    		}
    		  		
    		System.out.println("sqlCmd:" + sqlCmd);
    		PreparedStatement pstmt = conn.prepareStatement(sqlCmd);
        	result = pstmt.executeUpdate();
        	
    	}
    	
    	catch (SQLException e) {
			System.out.println(e);
			showErrorAlert("Database Connection Error", "Transaction not completed.");
			return 0;
		}
    	return result;
    }

	
    @Override
	public void showErrorAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.show();
		
	}
}


