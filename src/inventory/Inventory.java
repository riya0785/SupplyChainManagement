package inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DataBaseMethods;

public class Inventory extends DataBaseMethods {

	private int productId, quantity;
	private float price;
	private String item;
	
	
	public void read() {
		Connection connect = connectInventoryDB();
		
		String query = "SELECT * FROM product WHERE product_id = ?";
		
		ResultSet result = null;
		
		try (PreparedStatement st = connect.prepareStatement(query)){
			
			st.setInt(1, getProductId());
			
			result = st.executeQuery();
			
			if(result.next()) {
				productId = result.getInt("product_id");
				quantity = result.getInt("quantity");
				price = result.getFloat("price");
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	public void add() {
		Connection connect = connectInventoryDB();

		String query = "INSERT into product (product_id, item, quantity, price) VALUES (?,?,?,?)";

		try (PreparedStatement st = connect.prepareStatement(query)) {

			st.setInt(1, getProductId());
			st.setString(2, getItem());
			st.setInt(3, getQuantity());
			st.setFloat(4, getPrice());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully Added");
			} else {
				System.out.println("Addition Failed");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void update() {
		Connection connect = connectInventoryDB();

		String query = "UPDATE product " + "SET item = ?, price = ?, quantity = ? " + "WHERE product_id = ?";

		try (PreparedStatement st = connect.prepareStatement(query)) {

			st.setString(1, getItem());
			st.setFloat(2, getPrice());
			st.setInt(3, getQuantity());
			st.setInt(4, getProductId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully Updated");
			} else {
				System.out.println("Update Failed");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public void delete() {

		Connection connect = connectInventoryDB();

		// Query
		String query = "DELETE FROM product WHERE product_id = ?";

		try (
				// Prepare Query Statement
				PreparedStatement st = connect.prepareStatement(query);) {
			// Setting product ID to Query Statement
			st.setInt(1, getProductId());

			// Execute the delete statement
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Product deleted successfully.");
			} else {
				System.out.println("No product found with the specified ID.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void show() {

		Connection connect = connectInventoryDB();
		String query = "SELECT * FROM product";

		ResultSet result = null;

		try (PreparedStatement st = connect.prepareStatement(query)) {
			result = st.executeQuery();

			while (result.next()) {

				productId = result.getInt("product_id");
				item = result.getString("item");
				price = result.getFloat("price");
				quantity = result.getInt("quantity");

				System.out.println("-----------------------------");
				System.out.println("Product ID: " + productId);
				System.out.println("Product Name: " + item);
				System.out.println("Price: " + price);
				System.out.println("Quantity in Stock: " + quantity);
				System.out.println("-----------------------------");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deduct(int reduction) {
		
		Connection connect = connectInventoryDB();
		String query = "UPDATE product " + "SET quantity = ? " + "WHERE product_id = ?";
		
		try (PreparedStatement st = connect.prepareStatement(query)) {
			
			quantity = quantity - reduction;
			
			st.setInt(1, getQuantity());
			st.setInt(2, getProductId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully Updated");
			} else {
				System.out.println("Update Failed");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

//	------------------------Getters & Setters --------------------------

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	
}
