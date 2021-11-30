package com.bookit.common;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

public class Flight {

	private int flightID;
	private String airlineName;
	private String flightNumber;
	private String origination;
	private String destination;
	private String departureDate;
	private String departureTime;
	private String arrivalDate;
	private String arrivalTime;
	private int price;
	private int totalSeats;
//	private ImageIcon deleteIcon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon("images/deleteIcon.png");
//	private ImageIcon editIcon;
		
	public Flight() {
	}
	
//	public Flight (int flightID, String origination, String destination, String departureDate, int price){
//		this.flightID = flightID;
//		this.origination = origination;
//		this.destination = destination;
//		this.departureDate = departureDate;
//		this.price = price;
////		this.deleteIcon = new ImageIcon("images/deleteIcon.png");
//	}

	public Flight (int flightID, String airlineName, String flightNumber, String origination, String destination, String departureDate,
			String departureTime, String arrivalDate, String arrivalTime, int price, int totalSeats){
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
//		this.deleteIcon = new ImageIcon("images/deleteIcon.png");
//		this.editIcon = new ImageIcon("images/editIcon.png");
	}
	
//	public Flight (String flightID, String airlineName, String flightNumber, String origination, String destination, String departureDate,
//			String departureTime, String arrivalDate, String arrivalTime, String price, String totalSeats){
//		this.flightID = Integer.valueOf(flightID);
//		this.airlineName = airlineName;
//		this.flightNumber = flightNumber;
//		this.origination = origination;
//		this.destination = destination;
//		this.departureDate = departureDate;
//		this.departureTime = departureTime;
//		this.arrivalDate = arrivalDate;
//		this.arrivalTime = arrivalTime;
//		this.price = Integer.valueOf(price);
//		this.totalSeats = Integer.valueOf(totalSeats);
////		this.deleteIcon = new ImageIcon("images/deleteIcon.png");
////		this.editIcon = new ImageIcon("images/editIcon.png");
//	}
	
//	public Icon getDeleteIcon() {
//		return deleteIcon;
//	}
//	public Icon getEditIcon() {
//		return editIcon;
//	}
	public int getFlightID() {
		return flightID;
	}
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
