package inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DataBaseMethods;

public class Orders extends DataBaseMethods {

	private int orderId, productId, orderQty;
	private String username, item;
	private float price;

	public void add() {
		Connection connect = connectInventoryDB();

		String query = "INSERT into orders (order_id, username, product_id, item, order_qty, price) VALUES (?,?,?,?,?,?)";
		
		try (PreparedStatement st = connect.prepareStatement(query)) {
			st.setInt(1, getOrderId());
			st.setString(2, getUsername());
			st.setInt(3, getProductId());
			st.setString(4, getItem());
			st.setInt(5, getOrderQty());
			st.setFloat(6, getPrice());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Order Successfully Added");
			} else {
				System.out.println("Order Failed");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public float getPrice() {
		return getOrderQty()* price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
