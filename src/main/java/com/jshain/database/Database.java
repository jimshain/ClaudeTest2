package com.jshain.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static final String URL = System.getProperty("db.url", "jdbc:sqlite:ddaproduct.db");

	/**
	 * Returns a JDBC connection to the database.
	 *
	 * @return a Connection object
	 * @throws SQLException if a database access error occurs
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL);
	}

} // Class end
