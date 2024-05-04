package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	private String url = "jdbc:mysql://localhost:3306/";
	private String username = "root";
	private String password = REMOVED;
	private String userDB = "users";
	private String inventoryDB = "inventory";
	
	
	public Connection connectUserDB() {
		
		/*
		 * Load Driver - Driver gets automatically loaded if connector .jar file is
		 * added in reference library (in VS Code) or by adding an external jar file in
		 * java build path of the project's properties. here: SCM -> properties -> Java
		 * Build Path -> Libraries -> Class Path -> Add External Jars -> Select the
		 * Connector Jar File
		 */
		
		Connection connect = null;
		try {
			// Establish database connection
			connect = DriverManager.getConnection(url+userDB, username, password);
//			System.out.println("Connection Success");
			return connect;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}
	
public Connection connectInventoryDB() {
		
		/*
		 * Load Driver - Driver gets automatically loaded if connector .jar file is
		 * added in reference library (in VS Code) or by adding an external jar file in
		 * java build path of the project's properties. here: SCM -> properties -> Java
		 * Build Path -> Libraries -> Class Path -> Add External Jars -> Select the
		 * Connector Jar File
		 */
		
		Connection connect = null;
		try {
			// Establish database connection
			connect = DriverManager.getConnection(url+inventoryDB, username, password);
//			System.out.println("Connection Success"); 
			return connect;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}
}
