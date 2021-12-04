package com.bookit.common;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.bookit.db.*;
import com.bookit.gui.*;
import java.time.LocalDate;

import javafx.scene.control.DatePicker;

//Encapsulation of the SearchFlight Class
public class SearchFlight {
	private String origination;
	private String destination;
	private String departureDate;
	//******
	private String flightID;
	private String airline;
	private String flightNumber;
	private String departureTime;
	private String arrivalDate;
	private String arrivalTime;
	private String price;
	private int totalSeats;
	//******
	//private Flight flight; //Composition
	
	
	public SearchFlight() {
		
	}
	
	public SearchFlight(String origination, String destination, String departureDate) {
		this.origination = origination;
		this.destination = destination;
		this.departureDate = departureDate;
		
	}
	public SearchFlight(String origination) {
		this.origination = origination;
		this.destination = destination;
		this.departureDate = departureDate;
		
	}
	public SearchFlight(String flightID, String airline, String flightNumber, String origination, String destination, String departureDate,
			String departureTime, String arrivalDate, String arrivalTime, String price) {
		this.flightID = flightID;
		this.airline = airline;
		//this.airline = flight.airlineName; //Composition
		this.flightNumber = flightNumber;
		//this.flightNumber = flight.flightNo
		this.origination = origination;
		this.destination = destination;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.arrivalDate = arrivalDate;
		this.arrivalTime = arrivalTime;
		this.price = price;	
	}
	
	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	public String getOrigination() {
		return origination;
	}
	
	public void setOrigination(String origination) {
		this.origination = origination;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getDepartureDate() {
		return departureDate;
	}
	
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	
	/*public ComboBox getOnAction() {
		
	}*/
	

}
