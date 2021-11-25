package com.bookit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import com.bookit.db.PassEncTech1;
import com.bookit.common.Customer;
import com.bookit.common.Flight;
import com.bookit.exceptions.LoginException;


public class DBCommand {
	
	public static Connection GetConnecton() throws SQLException {
		
		Connection conn = null;
		
		try {
		
			// Connect to Google Cloud MySQL DB
			conn = DriverManager.getConnection("jdbc:mysql://35.237.105.213","cisuser","B00kit!");
			System.out.println("Database connected");
		}
		catch (SQLException e) {
			
			System.out.println(e);
			throw e;
		}
		
		return conn;
		
	}
	
	public static ResultSet sqlCmd(String query, String[] args) throws SQLException {
		
		Connection connection = GetConnecton();
		ResultSet resultSet;
		
		try {
			
			// Create the statement
			PreparedStatement statement = connection.prepareStatement(query);
			int len = args.length;
			
			// Add the string variables values
			if (len > 0) {
				for (int i=1;i<=args.length;i++) {
					statement.setString(i,args[i]);
				}
			}
			
			// Execute a statement
			resultSet = statement.executeQuery();

		}
		catch (SQLException e) {
			
			System.out.println(e);
			throw e;
		}
		finally {
			
			connection.close();
		}
		
		return resultSet;
	}
	
	public static void login(Customer co) throws Exception {
		
		try {
			
			// Encrypt the Password
//			String pwd = co.getPassword();
//			PassEncTech1 encrypt = new PassEncTech1();
//			String ePass = encrypt.encryptPassword(pwd);
//			String[] args = {co.getUserName(), ePass};
			
			// Build String array
			String[] args = {co.getUserName(), co.getPassword()};
			
			// Execute SQL command
			ResultSet resultSet = sqlCmd(SQLStmt.LOGIN, args);
				    
			int count = 0;

			// Iterate through the result set
			while (resultSet.next()) {
				System.out.println("Number of Users:" + resultSet.getInt(1));
				count = resultSet.getInt(1);
			}
			
			if (count == 0)
				throw new LoginException("Invalid UserName or Password!");
				     
		} catch (SQLException e) {
			
			System.out.println(e);
			throw e;
		}

	}    
	
	public static void bookFlight(Customer co, Flight fl) throws Exception {
		
		// can I run a query on reservation table that select all the rows with customerID comes from co
		
		// Build String array
		String[] args = {co.getUserName(), co.getPassword()};
		
		// Execute SQL command
		ResultSet resultSet = sqlCmd(SQLStmt.LOGIN, args);
		
		
		Flight f1 = new Flight();
		f1.setAirlineName("Delta 303");
		
		Flight f2 = new Flight();
		f2.setAirlineName("Delta 500");
		
		Flight f3 = new Flight();
		f3.setAirlineName("Delta 777");
		
		co.getFlights().add(f1);
		co.getFlights().add(f2);
		co.getFlights().add(f3);
		
		/* Error returned from DB when user tries to book the same flight twice:
		 * 		SQL Error [1062] [23000]: Duplicate entry '123456789-1' for key 'SSN'
		 */
		
	}

	public static void getFlights(Customer co) throws Exception {
		
		// can I run a query on reservation table that select all the rows with customerID comes from co
		
		// Build String array
		String[] args = {co.getUserName(), co.getPassword()};
		
		// Execute SQL command
		ResultSet resultSet = sqlCmd(SQLStmt.LOGIN, args);
		
		Flight f1 = new Flight();
		f1.setAirlineName("Delta 303");
		
		Flight f2 = new Flight();
		f2.setAirlineName("Delta 500");
		
		Flight f3 = new Flight();
		f3.setAirlineName("Delta 777");
		
		co.getFlights().add(f1);
		co.getFlights().add(f2);
		co.getFlights().add(f3);
		
	}

	public static void main(String[] args) throws Exception {
		
		Customer c0 = new Customer();
		login(c0);
	}

}
