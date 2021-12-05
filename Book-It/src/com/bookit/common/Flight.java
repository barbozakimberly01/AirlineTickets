package com.bookit.common;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

public class Flight extends Flights{
		
	public Flight() {
	}

//	public Flight (int flightID, String airlineName, String flightNumber, String origination, String destination, String departureDate,
//			String departureTime, String arrivalDate, String arrivalTime, int price, int totalSeats){
//		this.flightID = flightID;
//		this.airlineName = airlineName;
//		this.flightNumber = flightNumber;
//		this.origination = origination;
//		this.destination = destination;
//		this.departureDate = departureDate;
//		this.departureTime = departureTime;
//		this.arrivalDate = arrivalDate;
//		this.arrivalTime = arrivalTime;
//		this.price = price;
//		this.totalSeats = totalSeats;
//	}
	
	public Flight (int flightID, String airlineName, String flightNumber, String origination, String destination, LocalDate departureDate,
			String departureTime, LocalDate arrivalDate, String arrivalTime, int price, int totalSeats){
		this.flightID = flightID;
		this.airlineName = airlineName;
		this.flightNumber = flightNumber;
		this.origination = origination;
		this.destination = destination;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.arrivalDate = arrivalDate;
		this.arrivalTime = arrivalTime;
		this.price = price;
		this.totalSeats = totalSeats;
	}
		
	public String toString(Object obj) {
		String value="";
		System.out.println("Object passed in: " + obj.getClass());
		if (obj.getClass() == Integer.class) {
			value = Integer.toString((int) obj);
			System.out.println("String value passed back: " + value);
		} 
		return value;
	}

}
