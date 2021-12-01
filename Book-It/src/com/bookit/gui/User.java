package com.bookit.gui;
import java.util.List;
import com.bookit.db.*;
import com.bookit.gui.*;

//Encapsulation of the User Class
public class User {
	//TODO:Move to common.
	private int id;
	private String firstname;
	private String lastname;
	private String streetAddress;
	private String city;
	private String state;
	private String zipcode;
	private String username;
	private String password;
	private String emailAddress;
	private String ssn;
	private String securityQuestion;
	private String securityAnswer;
	private boolean adminStatus;
	
	public User() {
    }
	
	public User(int id, String firstname, String lastname, String streetAddress, String city, String state, 
				String zipcode, String username, String password, String emailAddress, String ssn, String securityQuestion, 
				String securityAnswer, boolean adminStatus) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
		this.ssn = ssn;
		this.securityQuestion = securityQuestion;
		this.securityAnswer = securityAnswer;
        this.adminStatus = adminStatus;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreet_address(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getSecurityQuestion() {
		return "What was your favorite food as a child?";
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public boolean isAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(boolean adminStatus) {
		this.adminStatus = adminStatus;
	}

}
