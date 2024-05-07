package inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnector;

public class Inventory extends DBConnector {

    private int productId, quantity;
    private float price;
    private String item;

    // Constructor to initialize product details
    public Inventory(int productId, int quantity, float price, String item) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.item = item;
    }

    // Empty constructor
    public Inventory() {};

    // Method to read product details from the database
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
                item = result.getString("item");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a new product to the database
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

    // Method to update product details in the database
    public void update() {
        Connection connect = connectInventoryDB();
        String query = "UPDATE product SET item = ?, price = ?, quantity = ? WHERE product_id = ?";
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

    // Method to delete a product from the database
    public void delete() {
        Connection connect = connectInventoryDB();
        String query = "DELETE FROM product WHERE product_id = ?";
        try (PreparedStatement st = connect.prepareStatement(query)) {
            st.setInt(1, getProductId());
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
    
    // Method to retrieve all products from the database
    public List<Object[]> showProducts() {
        List<Object[]> productList = new ArrayList<>();
        Connection connect = connectInventoryDB();
        String query = "SELECT * FROM product";
        try (PreparedStatement st = connect.prepareStatement(query);
             ResultSet result = st.executeQuery()) {
            while (result.next()) {
                int productId = result.getInt("product_id");
                String item = result.getString("item");
                int quantity = result.getInt("quantity");
                float price = result.getFloat("price");
                
                //Create an array to store the product details
                Object[] row = {productId, item, quantity, price};
                productList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    // Method to deduct a quantity from the product stock
    public int deduct(int reduction) {
        Connection connect = connectInventoryDB();
        String query = "UPDATE product SET quantity = quantity - ? WHERE product_id = ?";
        int rowsAffected = 0;
        try (PreparedStatement st = connect.prepareStatement(query)) {
            st.setInt(1, reduction);
            st.setInt(2, getProductId());
            rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully Updated");
            } else {
                System.out.println("Update Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    // Getters & Setters

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
