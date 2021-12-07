package com.bookit.common;

import java.time.LocalDate;

public class Booking extends Flight {
	
	private int bookingID;

	public Booking (int bookingID, String airlineName, String flightNumber, String origination, String destination, LocalDate departureDate,
			String departureTime, LocalDate arrivalDate, String arrivalTime, int price){
		this.bookingID = bookingID;
		this.airlineName = airlineName;
		this.flightNumber = flightNumber;
		this.origination = origination;
		this.destination = destination;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.arrivalDate = arrivalDate;
		this.arrivalTime = arrivalTime;
		this.price = price;
	}

	public int getBookingID() {
		return bookingID;
	}

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
}
