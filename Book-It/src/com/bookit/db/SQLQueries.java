package com.bookit.db;

public class SQLQueries {
	
	public static final String LOGIN = "SELECT count(*) as num FROM LOGINS WHERE username = ? AND password =?";
	
	public static final String NEWUSER = "INSERT INTO USERS (SSN,FirstName,LastName,Address,State,Zip,Email,LoginID)"
			+ " VALUES (?,?,?,?,?,?,?";

}
