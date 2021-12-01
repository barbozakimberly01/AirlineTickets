package com.bookit.common;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.bookit.db.*;
import com.bookit.gui.*;
import java.time.LocalDate;

//Encapsulation of Bookings Class
public class Bookings {
	private int bookingID;
	private String ssn;
	private String flightID;
	private String nameOnCard;
	private String creditCardNumber;
	private String expirationDate;
	private String cVV;
	
	
	public Bookings(){
		
	}
	
	public Bookings(int bookingID, String ssn, String flightID, String nameOnCard, String creditCardNumber, String expirationDate, String cVV) {
		this.bookingID = bookingID;
		this.ssn = ssn;
		this.flightID = flightID;
		this.nameOnCard = nameOnCard;
		this.creditCardNumber = creditCardNumber;
		this.expirationDate = expirationDate;
		this.cVV = cVV;
	}

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

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getcVV() {
		return cVV;
	}

	public void setcVV(String cVV) {
		this.cVV = cVV;
	}
	

}

