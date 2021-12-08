package com.bookit.gui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import com.bookit.common.Booking;
import com.bookit.db.DataAccess;
import com.bookit.db.SQLStatements;
import com.bookit.exceptions.ErrorAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


public class MyFlightsController extends ControllerMenu implements ErrorAlerts, Initializable {
	
	
	@FXML
	private TableView<Booking> tblView = new TableView <Booking>(); 
	
    @FXML
    private Label lblUserNameWelcome;
    
	private Booking currentBooking;
	
	// 
	
	Preferences userInfo = Preferences.userRoot();
	private int isAdmin = userInfo.getInt("IsAdmin", 0);
	private String ssn = userInfo.get("SSN", "");
	private String firstName = userInfo.get("FirstName", "");
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try { 
			// Check if user is an admin
			btnManageFlights.setVisible(false);
			if(isAdmin == 1) {
				btnManageFlights.setVisible(true);
			}
			
			// Display welcome message with users name
			lblUserNameWelcome.setText("Welcome back, " + firstName);
			loadMyFlights();		
			
		} catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }	
	}
	
	
	// 
	void loadMyFlights() {
		try { 
			
			// Execute SQL command 
			String query = String.format(SQLStatements.BOOKINGS, ssn);
			System.out.println("Query: " + query);
			ResultSet rs = DataAccess.sqlCmd(query);
			
			if (!rs.isBeforeFirst()) {
				// Check if bookings are available
				showErrorAlert("Information", "No Bookings Found");

			}
			else {
				// Begin loading bookings
				tblView.getItems().clear();
				tblView.getColumns().clear();
				ObservableList<Booking> bookingResultsList = FXCollections.observableArrayList();
				            
	            while(rs.next()){
					
	            	LocalDate localDDate = LocalDate.parse(rs.getString("DepartureDate"));
	            	LocalDate localADate = LocalDate.parse(rs.getString("ArrivalDate"));
	            	
	            	bookingResultsList.addAll(new Booking(
							rs.getInt("BookingID"),
							rs.getString("Airline"),
							rs.getString("FlightNumber"),
							rs.getString("Origination"),
							rs.getString("Destination"),
							localDDate,
							rs.getString("DepartureTime"),
							localADate,
							rs.getString("ArrivalTime"),
							rs.getInt("Price")
                		));
	            }
				rs.close();
				
				// Create table columns
				TableColumn<Booking, Integer> colBookingID = new TableColumn<>("BookingID");
				colBookingID.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
				colBookingID.setVisible(false);				
		    	
	            TableColumn<Booking, String> colAirline = new TableColumn<>("Airline");
	            colAirline.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
		    	
		    	TableColumn<Booking, String> colFlightNumber = new TableColumn<>("FlightNumber");
		    	colFlightNumber.setCellValueFactory(new PropertyValueFactory<>("FlightNumber"));
		    	
	            TableColumn<Booking, String> colOrigination = new TableColumn<>("Origination");
	            colOrigination.setCellValueFactory(new PropertyValueFactory<>("Origination"));
	            colOrigination.setPrefWidth(160);
	            colOrigination.setMaxWidth(colOrigination.getPrefWidth());
	            colOrigination.setMinWidth(colOrigination.getPrefWidth());
		    	
		    	TableColumn<Booking, String> colDestination = new TableColumn<>("Destination");
	            colDestination.setCellValueFactory(new PropertyValueFactory<>("Destination"));
	            colDestination.setPrefWidth(155);
	            colDestination.setMaxWidth(colDestination.getPrefWidth());
	            colDestination.setMinWidth(colDestination.getPrefWidth());
		    	
	            TableColumn<Booking, LocalDate> colDepartureDate = new TableColumn<>("DepartureDate");
	            colDepartureDate.setCellValueFactory(new PropertyValueFactory<>("DepartureDate"));
		    	
		    	TableColumn<Booking, String> colDepartureTime = new TableColumn<>("DepartureTime");
		    	colDepartureTime.setCellValueFactory(new PropertyValueFactory<>("DepartureTime"));
		    	
	            TableColumn<Booking, LocalDate> colArrivalDate = new TableColumn<>("ArrivalDate");
	            colArrivalDate.setCellValueFactory(new PropertyValueFactory<>("ArrivalDate"));
	            colArrivalDate.setPrefWidth(100);
	            colArrivalDate.setMaxWidth(colArrivalDate.getPrefWidth());
	            colArrivalDate.setMinWidth(colArrivalDate.getPrefWidth());
		    	
		    	TableColumn<Booking, String> colArrivalTime = new TableColumn<>("ArrivalTime");
		    	colArrivalTime.setCellValueFactory(new PropertyValueFactory<>("ArrivalTime"));

		    	TableColumn<Booking, Integer> colPrice = new TableColumn<>("Price");
		    	colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
		    	colPrice.setPrefWidth(70);
		    	colPrice.setMaxWidth(colPrice.getPrefWidth());
		    	colPrice.setMinWidth(colPrice.getPrefWidth());
	            
		    	// populate table 
				tblView.setItems(bookingResultsList);
				tblView.getColumns().addAll(colBookingID, colAirline, colFlightNumber, colOrigination, colDestination, colDepartureDate, colDepartureTime, colArrivalDate, colArrivalTime, colPrice);
				
				addButtonToTable();
				System.out.println("ResultList: " + bookingResultsList.size());
			
			}
		
		} catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	    }
			
		
	}
	
	private void addButtonToTable() {
	        TableColumn<Booking, Void> colBtn = new TableColumn<Booking, Void>("");
	
	        try {
		        Callback<TableColumn<Booking, Void>, TableCell<Booking, Void>> cellFactory = new Callback<TableColumn<Booking, Void>, TableCell<Booking, Void>>() {
		            @Override
		            public TableCell<Booking, Void> call(final TableColumn<Booking, Void> param) {
		                final TableCell<Booking, Void> cell = new TableCell<Booking, Void>() {
		
		                    private final Button btn = new Button("Cancel Booking");
		                    {
		                        btn.setOnAction((ActionEvent event) -> {                      	
		                        	Booking booking = getTableView().getItems().get(getIndex());
		                            System.out.println( "Delete selectedData: " + booking);                 	
		                            deleteBooking(booking);	
		                        });
		                    }
		
		                    @Override
		                    public void updateItem(Void item, boolean empty) {
		                        super.updateItem(item, empty);
		                        if (empty) {
		                            setGraphic(null);
		                        } else {
		                            setGraphic(btn);
		                            btn.setPrefWidth(130);
		                            btn.setMaxWidth(btn.getPrefWidth());
		                            btn.setMinWidth(btn.getPrefWidth());
		                        }
		                    }
		                };
		                return cell;
		            }
		        };
		
		        colBtn.setCellFactory(cellFactory);
		        colBtn.setPrefWidth(140);
		        colBtn.setMaxWidth(colBtn.getPrefWidth());
		        colBtn.setMinWidth(colBtn.getPrefWidth());
		
		        tblView.getColumns().add(colBtn);
	        } catch (Exception e) {
	 	        System.out.println(e);
	 	        e.printStackTrace();
	 	    }
	    }

	private void deleteBooking(Booking booking) {
    	System.out.println("Booking:" + booking);
    	currentBooking = booking;
    	try {
    		
    		// Delete booking
			int result = dbUpdate();
	    	System.out.println("Delete result:" + result);
	    	if (result == 1) {
	    		showInfoAlert("Information","Flight has been deleted.");
	    		loadMyFlights();
			
	    	}
	    	else {
	    		showErrorAlert("Deletion Error","Record Not Deleted");
	    	}
        	
    	} catch (Exception e) {
	        System.out.println(e);
	        e.printStackTrace();
	        showInfoAlert("Information", "Error Occured! Booking " + booking.getBookingID() + " was not deleted"); 
 	        
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
			showErrorAlert("Database Connection Error", "Booking could not be deleted.");
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
	
	public void showInfoAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.show();

	}

}
