package users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import database.DBConnector;
import inventory.Orders;

public class ClientUser extends DBConnector{
	
	private String username;
	private String password;
	
	Connection connect = connectUserDB();
	Scanner scan = new Scanner(System.in);
	
	public void add() {
		System.out.println("Enter Username: ");
		username =  scan.next();
		System.out.println("Enter Password: ");
		setPassword(scan.next());
		
		int rowsAffected = 0;
		
		String query = "INSERT INTO clients (uname, upassword) VALUES (?,?)";
		
		try(PreparedStatement st = connect.prepareStatement(query)){
			st.setString(1, username);
			st.setString(2, getPassword());
			
			rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				System.out.println("Successfully Registered");
			}
			else {
				System.out.println("Registration Failed. User Already Exists");
			}
		} catch (SQLException e) {
			e.getMessage();
			System.err.println("Registration Failed. User Already Exists");
		}	
		
	}
	

	public int authenticate() {
		int exist = 0;
				
		String query = "SELECT * FROM clients where uname = ? and upassword = ?";
		
		ResultSet result = null;
			
		try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
			// Set parameter for the prepared statement
			preparedStatement.setString(1, getUsername());
			preparedStatement.setString(2, getPassword());

			// Execute the select statement
			result = preparedStatement.executeQuery();

			// Display user information
			if (result.next()) {
				exist = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exist;	
	}
	
//	------------------------ Getters and Setters -------------------------
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = encryptor(password);
	}
	
	public String encryptor(String pass) {
		String encryptedpass = null;
		try {
			/* MessageDigest instance for MD5. */
			MessageDigest m = MessageDigest.getInstance("MD5");

			/* Add plain-text password bytes to digest using MD5 update() method. */
			m.update(pass.getBytes());

			/* Convert the hash value into bytes */
			byte[] bytes = m.digest();

			/*
			 * The bytes array has bytes in decimal form. Converting it into hexadecimal
			 * format.
			 */
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			/* Complete hashed password in hexadecimal format */
			encryptedpass = s.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encryptedpass;
	}

}
