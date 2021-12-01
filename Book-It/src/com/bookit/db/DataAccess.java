package com.bookit.db;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.time.LocalDate;
import java.util.ArrayList;

import com.bookit.common.Flight;
import com.bookit.common.SearchFlight;
import com.bookit.exceptions.LoginException;
import com.bookit.gui.User;


public class DataAccess {
	
	//************************ Charles Code **************************************
	
	// Check username and password from database and validate if true.
	public static boolean validUser(User user) throws SQLException {
		Connection con = GetConnecton();
		try {
			
	        if (user.getUsername() != null && user.getPassword() != null) {
	        	PreparedStatement preparedStmt = con.prepareStatement(SQLStatements.LOGIN);		
			    preparedStmt.setString(1, user.getUsername());
			    preparedStmt.setString(2, user.getPassword());
			     
			      ResultSet data = preparedStmt.executeQuery(); 
			      
			      if (data.next()) {
			    	  return true;
			      }        
	        }   
	    } 
		catch (SQLException err) {
	    	System.out.println(err.getMessage());
	    }
			con.close();	
		return false;
	}
	// check if security answer is true.
	public static boolean validSecurityAnswer(User user) {
		try {
	        if (user.getUsername() != null && !user.getUsername().isBlank() && !user.getSecurityQuestion().isBlank() && user.getSecurityQuestion() != null) {
	        	Connection con = GetConnecton();      	
			    PreparedStatement preparedStmt = con.prepareStatement(SQLStatements.GETPASSWORD);			    
			    preparedStmt.setString(1, user.getUsername());
			    preparedStmt.setString(2, user.getSecurityAnswer());
			    
			    ResultSet rs = preparedStmt.executeQuery(); 
			      
			    if (rs.next()) {
			    	  user.setPassword(rs.getString(1));
			    	  return true;
			    }        
	        }
	        
	        
	    } catch (SQLException err) {
	    	System.out.println(err.getMessage());
	    }
		return false;	
	}
	//Insert new user and login information into USER and LOGIN table.
	public static boolean UserSignup(User user) throws SQLException{
		Connection con = GetConnecton();
		
		try {
	            PreparedStatement preparedStmt = con.prepareStatement(SQLStatements.INSERTUSER);			    		     
			    preparedStmt.setString(1, user.getSsn());
			    preparedStmt.setString(2, user.getFirstname());
			    preparedStmt.setString(3, user.getLastname());
			    preparedStmt.setString(4, user.getStreetAddress());
			    preparedStmt.setString(5, user.getCity());
			    preparedStmt.setString(6, user.getState());
			    preparedStmt.setString(7, user.getZipcode());
			    preparedStmt.setString(8, user.getEmailAddress());
			    preparedStmt.setString(9, user.getUsername());
			    preparedStmt.executeUpdate(); 
			    
			    preparedStmt = con.prepareStatement(SQLStatements.INSERTLOGIN);
			    preparedStmt.setString(1, user.getUsername());
			    preparedStmt.setString(2, user.getPassword());
			    preparedStmt.setString(3, user.getSecurityQuestion());
			    preparedStmt.setString(4, user.getSecurityAnswer());
			    preparedStmt.setBoolean(5, user.isAdminStatus());
			    preparedStmt.executeUpdate(); 
			    return true;	        
	        
	    } 
		catch (SQLException err) {
	    	System.out.println(err.getMessage());
	    	con.close();
            return false;  
	    }				
	}
	
	
	//************************ Ron Code **************************************
	
	//Establish a connection to the database
	public static Connection GetConnecton() throws SQLException {	
			
		Connection conn = null;
		
		try {
		
			// Connect to Google Cloud MySQL DB
			conn = DriverManager.getConnection("jdbc:mysql://35.237.105.213/cis3270bookit","cisuser","B00kit!");
			System.out.println("Database connected");
		}
		catch (SQLException e) {
			
			System.out.println(e);
			throw e;
		}
		
		return conn;
	}
	
	public static ResultSet sqlCmd(String query) throws SQLException {
		String[] args = new String[0];
		return sqlCmd(query, args);
	}
		
	public static ResultSet sqlCmd(String query, String[] args) throws SQLException {
		
		Connection connection = GetConnecton();
		ResultSet resultSet;
		
		try {
			
			// Create the statement
			PreparedStatement statement = connection.prepareStatement(query);
			int len = args.length;
			System.out.println("Args length: " + len);
			// Add the string variables values
			if (len > 0) {
				for (int i=0;i<=len;i++) {
					System.out.println("Args values: " + i+1 + args[i]);
					statement.setString(i+1,args[i]);
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
			
			//connection.close();
		}
		
		return resultSet;
	}
	
	public static ResultSet sqlCmd(String query, ArrayList<Object> args) throws SQLException {
		
		Connection connection = GetConnecton();
		ResultSet resultSet;
		
		try {
			
			// Create the statement
			PreparedStatement statement = connection.prepareStatement(query);
			int len = args.size();
			System.out.println("Args length: " + len);
			
			// Add the object variables values
			if (len > 0) {
				for (int i=0;i<=len;i++) {
					int count = i +1;
					System.out.println("Args values: " + count + args.get(i));
					if (args.get(i).getClass() == Integer.class) {
						statement.setInt(count, (Integer)args.get(i));
						
					}
					else {
						statement.setString(count,args.get(i).toString());
					}
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
			
			//connection.close();
		}
		
		return resultSet;
	}
	
	public static int sqlCmdUpdate(String query) throws SQLException {
		
		ArrayList<Object> args = new ArrayList<Object>();
		return sqlCmdUpdate(query, args);
	}

	public static int sqlCmdUpdate(String query, ArrayList<Object> args) throws SQLException {
		
		Connection connection = GetConnecton();
		int result;
		
		try {
			
			// Add the object parameters to query
			int len = args.size();
			if (len > 0) {
				for (int i=0;i<=len;i++) {
					
					if (args.get(i).getClass() == Integer.class) {
						query = query + String.format(query, (Integer)args.get(i));
					}
					else {
						query = query + String.format(query, args.get(i).toString());
					}
					
				}
			}

			// Create the statement
			PreparedStatement statement = connection.prepareStatement(query);

			// Execute a statement
			result = statement.executeUpdate();

		}
		catch (SQLException e) {
			
			System.out.println(e);
			throw e;
		}
		finally {
			
			//connection.close();
		}
		return result;
	}

}

