package inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnector;


// Class to manage orders in the inventory system
public class Orders extends DBConnector {

    // Instance variables
    private int orderId, productId, orderQty;
    private String username, item;
    private float price;
    
    // Default constructor
    public Orders() {};

    // Parameterized constructor
    public Orders(String username, int productId, int orderQty, String item, float price) {
        this.username = username;
        this.productId = productId;
        this.orderQty = orderQty;
        this.item = item;
        this.price = price;
    }

    // Method to add a new order to the database
    public void add() {
        Connection connect = connectInventoryDB(); // Establish database connection

        // SQL INSERT query
        String query = "INSERT into orders (order_id, username, product_id, item, order_qty, price) VALUES (?,?,?,?,?,?)";
        
        try (PreparedStatement st = connect.prepareStatement(query)) {
            // Set values for placeholders in the query
            st.setInt(1, getOrderId());
            st.setString(2, getUsername());
            st.setInt(3, getProductId());
            st.setString(4, getItem());
            st.setInt(5, getOrderQty());
            st.setFloat(6, getPrice());

            int rowsAffected = st.executeUpdate(); // Execute the query

            // Check if the order was successfully added
            if (rowsAffected > 0) {
                System.out.println("Order Successfully Added");
            } else {
                System.out.println("Order Failed");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to retrieve and return a list of all orders from the database
    public List<Object[]> showOrders() {
        List<Object[]> orderList = new ArrayList<>();
        Connection connect = connectInventoryDB(); // Establish database connection
        String query = "SELECT * FROM orders"; // SQL SELECT query

        try (PreparedStatement st = connect.prepareStatement(query);
             ResultSet result = st.executeQuery()) {
            // Iterate over the result set
            while (result.next()) {
                int orderId = result.getInt("order_id");
                String username = result.getString("username");
                int productId = result.getInt("product_id");
                String item = result.getString("item");
                int quantity = result.getInt("order_qty");
                float price = result.getFloat("price");

                // Create an array containing order details
                Object[] row = {orderId, username, productId, item, quantity, price};
                orderList.add(row); // Add the array to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return orderList;
        }
        return orderList; // Return the list of orders
    }
    
    // Getter and Setter Methods
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
