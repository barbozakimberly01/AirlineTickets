package com.bookit.db;

import java.sql.Connection;
import java.sql.SQLException;
import com.bookit.common.User;

public interface UserInterface {
	static Connection GetConnecton() {
		Connection conn = null;
		return conn;
	};	
	
	boolean validUser(User user) throws SQLException;
}
