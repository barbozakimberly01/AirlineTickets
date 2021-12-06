package com.bookit.db;

import java.sql.Connection;

import com.bookit.common.User;

public interface UserInterface {
	static Connection GetConnecton() {
		Connection conn = null;
		return conn;
	};	
	
	static boolean validUser(User user) {
		return false;
	}


}
