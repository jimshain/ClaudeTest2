package com.jshain.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static final String URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/ddaproduct");
	private static final String USER = System.getProperty("db.user", "root");
	private static final String PASSWORD = System.getProperty("db.password", "");

	/**
	 * Returns a JDBC connection to the database.
	 *
	 * @return a Connection object
	 * @throws SQLException if a database access error occurs
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

} // Class end
