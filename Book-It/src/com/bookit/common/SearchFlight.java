package com.bookit.common;
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
	private LocalDate departureDate;
	//******
	private String flightID;
	private String airline;
	private String flightNumber;
	private LocalTime departureTime;
	private LocalDate arrivalDate;
	private LocalTime arrivalTime;
	private int price;
	private int totalSeats;
	//******
	//private Flight flight; //Composition
	
	
	public SearchFlight() {
		
	}
	
	public SearchFlight(String origination, String destination, LocalDate departureDate) {
		this.origination = origination;
		this.destination = destination;
		this.departureDate = departureDate;
		
	}
	public SearchFlight(String origination) {
		this.origination = origination;
		this.destination = destination;
		this.departureDate = departureDate;
		
	}
	public SearchFlight(String flightID, String airline, String flightNumber, String origination, String destination/*, LocalDate departureDate*/) {
		this.flightID = flightID;
		this.airline = airline;
		//this.airline = flight.airlineName; //Composition
		this.flightNumber = flightNumber;
		//this.flightNumber = flight.flightNo
		this.origination = origination;
		this.destination = destination;
		this.departureDate = departureDate;
		
		
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

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
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
	public LocalDate getDepartureDate() {
		return  departureDate;
	}
	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

}
