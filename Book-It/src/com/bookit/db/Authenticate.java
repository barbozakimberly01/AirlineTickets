package com.bookit.db;

public class Authenticate {
	
	public static final String LOGIN = "SELECT count(*) as num  FROM login_credentials WHERE username = ? AND password =?";

}
