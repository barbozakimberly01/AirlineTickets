package com.bookit.common;

public class Booking extends Flight {
	private int bookingID;
	
	private String ssn;
	
	public int getBookingID() {
		return bookingID;
	}
	
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
	
	public String getSsn() {
		return ssn;
	}
	
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

}
