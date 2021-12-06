package com.bookit.gui;

import static javax.swing.JOptionPane.showMessageDialog;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.bookit.common.Booking;
import com.bookit.common.Bookings;
import com.bookit.common.Flight;
import com.bookit.db.DataAccess;
import com.bookit.db.SQLStatements;
import com.bookit.exceptions.ErrorAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.util.Callback;


public class MyFlightsController extends ControllerMenu implements ErrorAlerts, Initializable {
	
	
	
	
	@FXML
	private TableView<Flight> tblView = new TableView <Flight>(); 
	
	private Bookings currentBooking;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try { 
			
			loadMyFlights();
			
			
			
		} catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }	
	}
	
	void loadMyFlights() {
		try { 
			
			// Execute SQL command
			String ssn = Booking.getSsn();
			String query = SQLStatements.BOOKINGS + ssn;

			ResultSet rs = DataAccess.sqlCmd(query);
			
			if (!rs.isBeforeFirst()) {
				// Check if flights available
				showErrorAlert("Information", "User Bookings", "No Bookings Found");

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
							rs.getInt("BookingID"),
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
				TableColumn<Flight, Integer> colBookingID = new TableColumn<>("BookingID");
				colBookingID.setCellValueFactory(new PropertyValueFactory<>("BookingID"));
				colBookingID.setVisible(false);				
		    	
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
				tblView.getColumns().addAll(colBookingID, colAirline, colFlightNumber, colOrigination, colDestination, colDepartureDate, colDepartureTime, colArrivalDate, colArrivalTime, colPrice, colSeatCount);
				tblView.applyCss();
				addButtonToTable();
				System.out.println("ResultList: " + flightResultsList.size());
			
			}
		
		} catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
			
		
	}
	
	private void addButtonToTable() {
	        TableColumn<Flight, Void> colBtn = new TableColumn("");
	
	        try {
		        Callback<TableColumn<Flight, Void>, TableCell<Flight, Void>> cellFactory = new Callback<TableColumn<Flight, Void>, TableCell<Flight, Void>>() {
		            @Override
		            public TableCell<Flight, Void> call(final TableColumn<Flight, Void> param) {
		                final TableCell<Flight, Void> cell = new TableCell<Flight, Void>() {
		
		                    private final Button btn = new Button("Delete");
		                    
		
		                    {
		                        btn.setOnAction((ActionEvent event) -> {
		                        	
		                        	Flight flight = getTableView().getItems().get(getIndex());
		                            System.out.println( "Delete selectedData: " + flight);
		                        	
		                            deleteBooking(currentBooking);	
		                          
		                        
		                            
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
	 	    }
	
	    }

	private void deleteBooking(Bookings booking) {
    	System.out.println("Booking:" + booking);
    	currentBooking = booking;
    	try {
    		
    		// Delete flight
			int result = dbUpdate();
	    	System.out.println("Delete result:" + result);
	    	if (result == 1) {
	    		loadMyFlights();
			
	    	}
	    	else {
	    		showErrorAlert("Error","Deletion Error","Record Not Deleted");
	    	}
        	
    	} catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	        showMessageDialog(null, "Error Occured! Booking " + booking.getBookingID() + " was not deleted"); 
 	        
 	    }
    }
	
	private int dbUpdate () {
    	int result=0;
    	String sqlCmd="";
    
    	try {
    		
    		// Execute SQL command
    		Connection conn = DataAccess.GetConnecton();
    		
    		sqlCmd = String.format(SQLStatements.DELETEBOOKING, currentBooking.getBookingID());
    		
    		System.out.println("sqlCmd:" + sqlCmd);
    		PreparedStatement pstmt = conn.prepareStatement(sqlCmd);
        	result = pstmt.executeUpdate();
        	
    	}
    	
    	catch (SQLException e) {
			System.out.println(e);
			showErrorAlert("Error", "Database Connection Error", "Transaction not completed.");
			return 0;
		}
    	return result;
    }

	
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
