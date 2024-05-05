package inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DataBaseMethods;

public class Orders extends DataBaseMethods {

	private int orderId, productId, orderQty;
	private String username, item;
	private float price;
	
	

	public Orders() {};

	public Orders(String username, int productId, int orderQty, String item, float price) {
		this.username = username;
		this.productId = productId;
		this.orderQty = orderQty;
		this.item = item;
		this.price = price;
	}

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
	
	public List<Object[]> showOrders() {
	    List<Object[]> orderList = new ArrayList<>();
	    Connection connect = connectInventoryDB();
	    String query = "SELECT * FROM orders";

	    try (PreparedStatement st = connect.prepareStatement(query);
	         ResultSet result = st.executeQuery()) {
	        while (result.next()) {
	        	int orderId = result.getInt("order_id");
	        	String username = result.getString("username");
	            int productId = result.getInt("product_id");
	            String item = result.getString("item");
	            int quantity = result.getInt("order_qty");
	            float price = result.getFloat("price");

	            Object[] row = {orderId,username, productId, item, quantity, price};
	            orderList.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return orderList;
	    }
	    return orderList;

	}
	
//	public void show() {
//		Connection connect = connectInventoryDB();
//		
//		String query = "SELECT * from orders";
//		
//		ResultSet result = null;
//		
//		try (PreparedStatement st = connect.prepareStatement(query)) {
//			
//			result = st.executeQuery();
//			
//			while(result.next()) {
//				System.out.println("------------------------");
//				System.out.println("Order ID: "+ result.getInt("order_id"));
//				System.out.println("Username: "+ result.getString("username"));
//				System.out.println("Product ID: "+ result.getInt("product_id"));
//				System.out.println("Item: "+ result.getString("item"));
//				System.out.println("Order Quantity: "+ result.getInt("order_qty"));
//				System.out.println("Price: "+result.getFloat("price"));
//			}
//			System.out.println("------------------------\n");
//			
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}
//	}

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
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
