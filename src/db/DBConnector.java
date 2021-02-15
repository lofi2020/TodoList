package db;

import dao.DBException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

	private static final String DB_URL = "jdbc:mysql://localhost/java2";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD =  "";
	
	private Connection conn = null;

	private static DBConnector instance ;

	public DBConnector() {
		super();
	}

	public Connection getConnection() throws DBException {

			try {
				if (conn == null || conn.isClosed()) {
					conn = DriverManager.getConnection(DBConnector.DB_URL, DBConnector.DB_USER, DBConnector.DB_PASSWORD);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new DBException(e.toString());
			}
		return conn;
	}
	
	public void closeConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static DBConnector instance() {
		if (instance == null) {
			instance = new DBConnector();
		}
		return instance;
	}

}
