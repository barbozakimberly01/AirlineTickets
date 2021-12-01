package com.bookit.gui;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import com.bookit.db.DataAccess;
import com.bookit.db.SQLStatements;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;
import com.bookit.common.Flight;
import com.bookit.common.SearchFlight;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ManageFlightsController implements Initializable{
	
	// Load flights on scene load
    
    @Override
    // Populate the TableView on load
    public void initialize(URL url, ResourceBundle rb) {
    	lblStatusText.setText("");
    	showTblView();
    	
    }
    
    /************************************
     * 	Header Objects 
     ************************************/
    @FXML
    private Button btnCreateFlight;
    
    @FXML
    private Button btnReloadFlights;

    @FXML
    private Button btnLogout;
    
    @FXML
    private Button btnSearchFlights;

    @FXML
    private Button btnMyFlights;
    
    @FXML
    private Label lblStatusText;
    
    /************************************
     * 	Table View Objects 
     ************************************/
    @FXML
    private TableView<Flight> tblView = new TableView();
	
    
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
    private TextField txtDepartureDate;

    @FXML
    private TextField txtDepartureTime;

    @FXML
    private TextField txtDestination;

    @FXML
    private TextField txtArrivalDate;

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
    
    public void refreshFlights(ActionEvent event) {
    	btnReloadFlights.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent e)
    		{
    			showTblView();
    			lblStatusText.setText("Flights have been reloaded");
    		}
    	});
    }

    @FXML
    void createFlightAction(ActionEvent event) {
    	btnCreateFlight.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent e)
    		{
    			createFlight();
    		}
    	});
    }
    
    @FXML
    void createFlightSubmitAction(ActionEvent event) {
    	btnSubmitFlight.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent e)
    		{
    			submitCreatedFlight();
    		}
    	});
    }
    
    @FXML
    void updateFlightAction(ActionEvent event) {
    	btnSubmitUpdate.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent e)
    		{
    			updateFlight(currentFlight);
    		}
    	});
    }

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

    @FXML
    void searchFlightsAction(ActionEvent event) {
    	try {
			SceneCreator.launchScene("/com/bookit/gui/Search.fxml");
			} catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
    }    
    
	/************************************
	 * 	Controller Logic
	 ************************************/
    private enum updateType {
    	update, insert, delete
    }
    
    private void showTblView() {
    	anchorFlight.setVisible(false);
    	tblView.setVisible(true);
    	btnCreateFlight.setVisible(true);
    	loadAllFlights();
    }
    
    private void showAnchor() {
    	lblStatusText.setText("");
    	tblView.setVisible(false);
    	btnCreateFlight.setVisible(false);
    	showFlightID(false);
		anchorFlight.setVisible(true);	
    } 
    
    private void showFlightID(boolean choice) {
    	lblFlightID.setVisible(choice);
    	lblFlightIDValue.setVisible(choice);
    }
	
    public void loadAllFlights() {
		
		try {
			
			tblView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			// Execute SQL command
			ResultSet rs = DataAccess.sqlCmd(SQLStatements.ALLFLIGHTS);
			
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
					
					flightResultsList.addAll(new Flight(
							rs.getInt("FlightID"),
							rs.getString("Airline"),
							rs.getString("FlightNumber"),
							rs.getString("Origination"),
							rs.getString("Destination"),
							rs.getString("DepartureDate"),
							rs.getString("DepartureTime"),
							rs.getString("ArrivalDate"),
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
		    	
	            TableColumn<Flight, String> colDepartureDate = new TableColumn<>("DepartureDate");
	            colDepartureDate.setCellValueFactory(new PropertyValueFactory<>("DepartureDate"));
		    	
		    	TableColumn<Flight, String> colDepartureTime = new TableColumn<>("DepartureTime");
		    	colDepartureTime.setCellValueFactory(new PropertyValueFactory<>("DepartureTime"));
		    	
	            TableColumn<Flight, String> colArrivalDate = new TableColumn<>("ArrivalDate");
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
				addButtonToTable("","Delete");
				
				addButtonToTable("","Edit");
				System.out.println("ResultList: " + flightResultsList.size());
			}

				     
		} catch (SQLException e) {
			
			System.out.println(e);
			
		}

	} 

    private void addButtonToTable(String columnName, String buttonName) {
        TableColumn<Flight, Void> colBtn = new TableColumn(columnName);

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
	    		lblStatusText.setText("Error: Flight " + flight.getFlightID() + " was not deleted");
	    	}
        	
    	} catch (Exception e) {
 	        System.out.println(e);
 	        e.printStackTrace();
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
	    	txtDepartureDate.setText(flight.getDepartureDate());
	    	txtDepartureTime.setText(flight.getDepartureTime());
	    	txtArrivalDate.setText(flight.getArrivalDate());
	    	txtArrivalTime.setText(flight.getArrivalTime());
	    	txtPrice.setText(flight.toString(flight.getPrice()));
	    	txtTotalSeats.setText(flight.toString(flight.getTotalSeats()));
    	
    	} catch (Exception e) {
 	        System.out.println(e);
 	        e.printStackTrace();
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
	    	txtDepartureDate.setText("");
	    	txtDepartureTime.setText("");
	    	txtArrivalDate.setText("");
	    	txtArrivalTime.setText("");
	    	txtPrice.setText("");
	    	txtTotalSeats.setText("");
	    	
	    	// Show Create Flight button and hide Edit Flight button
	    	btnSubmitUpdate.setVisible(false);
	    	btnSubmitFlight.setVisible(true);
    	
    	} catch (Exception e) {
 	        System.out.println(e);
 	        e.printStackTrace();
 	    }
    }
       
    public void updateFlight(Flight flight) {
    	
    	int result = dbUpdate(updateType.update);
    	System.out.println("Update result:" + result);
    	if (result == 1) {
    		showTblView();
    		
        	lblStatusText.setText("Flight " + flight.getFlightID() + " has been updated");
    	}
    	else {
    		lblStatusText.setText("Error: Record was not updated");
    	}
    }
    
    public void submitCreatedFlight() {
    	
    	int result = dbUpdate(updateType.insert);
    	System.out.println("Create result:" + result);
    	if (result == 1) {
    		showTblView();
        	lblStatusText.setText("Flight has been created");
    	}
    	else {
    		lblStatusText.setText("Error: Flight was not created");
    	}    	
    }
    
    
    public int dbUpdate (updateType type) {
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
        				txtDepartureDate.getText(),
        				txtDepartureTime.getText(),
        				txtArrivalDate.getText(),
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
        				txtDepartureDate.getText(),
        				txtDepartureTime.getText(),
        				txtArrivalDate.getText(),
        				txtArrivalTime.getText(),
        				Integer.parseInt(txtPrice.getText()),
    					Integer.parseInt(txtTotalSeats.getText())
        				); 
    		}
    		  		
    		System.out.println("sqlCmd:" + sqlCmd);
    		PreparedStatement pstmt = conn.prepareStatement(sqlCmd);
        	result = pstmt.executeUpdate();
        	
    	}catch (SQLException e) {
			
			System.out.println(e);
		}
    	return result;
    }
}


