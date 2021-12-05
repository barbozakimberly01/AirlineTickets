package com.bookit.common;

import java.time.LocalDate;

import javafx.scene.control.DatePicker;

public abstract class Flights {
	
	protected int flightID;
	protected String airlineName;
	protected String flightNumber;
	protected String origination;
	protected String destination;
	//protected String departureDate;
	protected LocalDate departureDate;
	protected String departureTime;
	//protected String arrivalDate;
	protected LocalDate arrivalDate;
	protected String arrivalTime;
	protected int price;
	protected int totalSeats;
	
//	public int getFlightID() {
//		return flightID;
//	}
	public void setFlightID(int flightID) {
		this.flightID = flightID;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
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
//	public LocalDate getDepartureDate() {
//		return departureDate;
//	}
	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
//	public LocalDate getArrivalDate() {
//		return arrivalDate;
//	}
	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
//	public int getPrice() {
//		return price;
//	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}



}
