package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import database.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import inventory.*;

public class OrganicAuraUI extends DBConnector{
    private JFrame frame;
    private JPanel panel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton clientButton;
    private JButton adminButton;
    private JButton buyButton;
    private JTextArea displayArea;
    private JTable table;
    private DefaultTableModel tableModel;
    private boolean isLoggedIn = false;
    private List<CartItem> cartItems = new ArrayList<>();

    public OrganicAuraUI() {
        frame = new JFrame("Supply Chain Management System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JLabel projectLabel = new JLabel("Supply Chain Management System");
        projectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        projectLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
        panel.add(projectLabel);

        // Create client and admin buttons with styling
        clientButton = new JButton("Client");
        styleButton(clientButton); // Apply styling to client button

        adminButton = new JButton("Admin");
        styleButton(adminButton); // Apply styling to admin button

        // Add client and admin buttons to a sub-panel with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(clientButton);
        buttonPanel.add(adminButton);

        panel.add(buttonPanel);

        // Add action listeners to client and admin buttons
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to client window
                frame.getContentPane().removeAll();
                frame.repaint();
                addLoginButtons();
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to admin window
                frame.getContentPane().removeAll();
                frame.repaint();
                addLoginButtons();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(52, 152, 219)); // Set background color
        button.setForeground(Color.WHITE); // Set font color
        button.setFocusPainted(false); // Remove focus border
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        button.setPreferredSize(new Dimension(200, 50)); // Set button size
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); // Set padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set cursor
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 152, 219));
            }
        });
    }


    public void addLoginButtons() {
        panel.removeAll();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JLabel projectLabel = new JLabel("Supply Chain Management System");
        projectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        projectLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
        panel.add(projectLabel);

        // Create login and register buttons with styling
        loginButton = new JButton("Login");
        styleButton(loginButton); // Apply styling to login button

        registerButton = new JButton("Register");
        styleButton(registerButton); // Apply styling to register button

        // Add login and register buttons to a sub-panel with FlowLayout
        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel2.add(loginButton);
        buttonPanel2.add(registerButton);

        panel.add(buttonPanel2);

        // Add action listeners to login and register buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isLoggedIn) {
                    frame.getContentPane().removeAll();
                    frame.repaint();
                    addLoginFields();
                } else {
                    JOptionPane.showMessageDialog(frame, "You are already logged in!");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                addRegistrationFields();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
    public void addAdminLoginButton() {
        panel.removeAll();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JLabel projectLabel = new JLabel("Supply Chain Management System");
        projectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        projectLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
        panel.add(projectLabel);

        // Create login button for admin with styling
        loginButton = new JButton("Login");
        styleButton(loginButton); // Apply styling to login button

        // Add login button to a sub-panel with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);

        panel.add(buttonPanel);

        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isLoggedIn) {
                    frame.getContentPane().removeAll();
                    frame.repaint();
                    addLoginFields();
                } else {
                    JOptionPane.showMessageDialog(frame, "You are already logged in!");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
    public void addLoginFields() {
        panel.removeAll();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        JLabel projectLabel = new JLabel("Supply Chain Management System");
        projectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(projectLabel);

        // Create a panel for username components
        JPanel usernamePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(12);
        gbc.gridx = 0;
        gbc.gridy = 0;
        usernamePanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        usernamePanel.add(usernameField, gbc);

        // Create a panel for password components
        JPanel passwordPanel = new JPanel(new GridBagLayout());

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(12);
        gbc.gridx = 0;
        gbc.gridy = 0;
        passwordPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        passwordPanel.add(passwordField, gbc);

        JButton login = new JButton("Login");

        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(login);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // Authenticate user
                if (authenticateUser(username, password)) {
                    isLoggedIn = true;
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.getContentPane().removeAll();
                    frame.repaint();
                    addClientOptions();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password!");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private boolean authenticateUser(String username, String password) {
        // Connect to the database and authenticate user
        Connection c = connectUserDB(); // Replace with your database connection method
        if (c != null) {
            String query = "SELECT * FROM clients WHERE uname = ? AND upassword = ?";
            try (PreparedStatement statement = c.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // User authenticated successfully
                        return true;
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    c.close(); // Close the database connection
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    public void addRegistrationFields() {
        panel.removeAll();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        JLabel projectLabel = new JLabel("Supply Chain Management System");
        projectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(projectLabel);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton register = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(register);

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // Implement registration functionality here
                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "User Registered Successfully!");
                    frame.getContentPane().removeAll();
                    frame.repaint();
                    addLoginButtons(); // Revert back to the login screen
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to register user!");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private boolean registerUser(String username, String password) {
        // Connect to the database and insert user information into the clients table
        Connection c = connectUserDB();
        if (c != null) {
            String query = "INSERT INTO clients (uname, upassword) VALUES (?, ?)";
            try (PreparedStatement statement = c.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    c.close(); // Close the database connection
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    public void addClientOptions() {
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setLayout(new BorderLayout());

        buyButton = new JButton("Buy");
        JButton checkoutButton = new JButton("Checkout"); // New button for checkout

        topPanel.add(buyButton);
        topPanel.add(checkoutButton); // Add the checkout button

        displayArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Fetch product details from the products table in the database
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Define columns for the table
        String[] columns = {"Product ID", "Item", "Quantity", "Price"};
        tableModel.setColumnIdentifiers(columns);

        // Fetch data from the products table and populate the table
        try (Connection connection = connectInventoryDB();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM product");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String item = resultSet.getString("item");
                int quantity = resultSet.getInt("quantity");
                float price = resultSet.getFloat("price");

                Object[] row = {productId, item, quantity, price};
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to fetch product data from the database.");
        }

        bottomPanel.add(scrollPane, BorderLayout.NORTH);
        bottomPanel.add(tableScrollPane, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row from the table
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the product details from the selected row
                    String productId = table.getValueAt(selectedRow, 0).toString();
                    String item = table.getValueAt(selectedRow, 1).toString();
                    float price = Float.parseFloat(table.getValueAt(selectedRow, 3).toString());

                    // Prompt a dialog box for quantity
                    String quantityString = JOptionPane.showInputDialog(frame, "Enter Quantity:");
                    if (quantityString != null && !quantityString.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityString);
                            // Calculate total price
                            float totalPrice = price * quantity;

                            // Add the order to the cart
                            addToCart(productId, item, quantity, totalPrice);

                            // Display cart contents
                            displayCart();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a product!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener for the checkout button
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculate total price of the cart
                float totalPrice = 0;
                for (CartItem item : cartItems) {
                    totalPrice += item.getTotalPrice();
                    // Update inventory quantity
                    updateInventory(Integer.parseInt(item.getProductId()), item.getQuantity());
                }

                // Display total price and order placed message
                JOptionPane.showMessageDialog(frame, "Order has been placed.\nTotal Price: Rs." + totalPrice, "Checkout", JOptionPane.INFORMATION_MESSAGE);

                // Clear the cart after checkout
                cartItems.clear();
                displayCart(); // Update the cart display
                // Refresh inventory table display
                refreshInventoryTable();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }


    public void addAdminOptions() {
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setLayout(new BorderLayout());

        JButton addItemButton = new JButton("Add Item");
        JButton updateItemButton = new JButton("Update Item");
        JButton deleteItemButton = new JButton("Delete Item");

        topPanel.add(addItemButton);
        topPanel.add(updateItemButton);
        topPanel.add(deleteItemButton);

        displayArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        String[] columns = {"Product ID", "Item", "Quantity", "Price"};
        Object[][] data = {
                {"1", "Item 1", "10", "100"},
                {"2", "Item 2", "20", "200"},
                {"3", "Item 3", "30", "300"}
        };
        tableModel = new DefaultTableModel(data, columns);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        bottomPanel.add(scrollPane, BorderLayout.NORTH);
        bottomPanel.add(tableScrollPane, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement add item functionality here
                String productId = JOptionPane.showInputDialog(frame, "Enter Product ID:");
                String item = JOptionPane.showInputDialog(frame, "Enter Item:");
                String quantity = JOptionPane.showInputDialog(frame, "Enter Quantity:");
                String price = JOptionPane.showInputDialog(frame, "Enter Price:");

                String[] row = {productId, item, quantity, price};
                tableModel.addRow(row);
            }
        });

        updateItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement update item functionality here
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String productId = JOptionPane.showInputDialog(frame, "Enter Product ID:");
                    String item = JOptionPane.showInputDialog(frame, "Enter Item:");
                    String quantity = JOptionPane.showInputDialog(frame, "Enter Quantity:");
                    String price = JOptionPane.showInputDialog(frame, "Enter Price:");

                    tableModel.setValueAt(productId, selectedRow, 0);
                    tableModel.setValueAt(item, selectedRow, 1);
                    tableModel.setValueAt(quantity, selectedRow, 2);
                    tableModel.setValueAt(price, selectedRow, 3);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a product!");
                }
            }
        });

        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement delete item functionality here
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a product!");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private boolean updateInventory(int productId, int reduction) {
        // Call deduct() method from Inventory class
        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.deduct(reduction);
        // You can return true/false based on the success of the deduct operation
        return true; // Placeholder, replace with actual logic
    }

    private void addToCart(String productId, String item, int quantity, float totalPrice) {
        // Create a new CartItem and add it to the cartItems list
        CartItem cartItem = new CartItem(productId, item, quantity, totalPrice);
        cartItems.add(cartItem);
        displayCart();
    }

    public void refreshInventoryTable() {
        // Clear the table
        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.setRowCount(0);

        // Fetch data from the products table and populate the table
        try (Connection connection = connectInventoryDB();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM product");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String item = resultSet.getString("item");
                int quantity = resultSet.getInt("quantity");
                float price = resultSet.getFloat("price");

                Object[] row = {productId, item, quantity, price};
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to fetch product data from the database.");
        }
    }

    protected void displayCart() {
        StringBuilder cartDetails = new StringBuilder();
        cartDetails.append("<html><body><h2>Your Cart:</h2><table border='1'><tr><th>Product ID</th><th>Item</th><th>Quantity</th><th>Total Price</th></tr>");

        for (CartItem item : cartItems) {
            cartDetails.append("<tr><td>").append(item.getProductId()).append("</td>");
            cartDetails.append("<td>").append(item.getItem()).append("</td>");
            cartDetails.append("<td>").append(item.getQuantity()).append("</td>");
            cartDetails.append("<td>").append(item.getTotalPrice()).append("</td></tr>");
        }

        cartDetails.append("</table></body></html>");

        JOptionPane.showMessageDialog(frame, cartDetails.toString(), "Cart", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OrganicAuraUI();
            }
        });
    }
    
    
    public class CartItem {
        private String productId;
        private String item;
        private int quantity;
        private float totalPrice;

        public CartItem(String productId, String item, int quantity, float totalPrice) {
            this.productId = productId;
            this.item = item;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
        }

        public String getProductId() {
            return productId;
        }

        public String getItem() {
            return item;
        }

        public int getQuantity() {
            return quantity;
        }

        public float getTotalPrice() {
            return totalPrice;
        }
    }

}
