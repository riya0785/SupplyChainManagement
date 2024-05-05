package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import inventory.CartItem;
import inventory.Inventory;
import inventory.Orders;
import users.ClientUser;

public class ClientUI extends OptimizedUI implements UIActions {

	private String username;
	
	@Override
	public void loginButtons() {
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
					loginFields();
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
				registrationFields();
			}
		});

		frame.add(panel);
		frame.setVisible(true);
		
	}

	@Override
	public void loginFields() {
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
				username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				// Authenticate user
				if (authenticateClientUser(username, password)) {
					isLoggedIn = true;
					JOptionPane.showMessageDialog(frame, "Login successful!");
					frame.getContentPane().removeAll();
					frame.repaint();
					dashboard(username);
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid username or password!");
				}
			}
		});

		frame.add(panel);
		frame.setVisible(true);
		
	}

	@Override
	public void registrationFields() {
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
				if (register(username, password)) {
					JOptionPane.showMessageDialog(frame, "User Registered Successfully!");
					frame.getContentPane().removeAll();
					frame.repaint();
					loginButtons(); // Revert back to the login screen
				} else {
					JOptionPane.showMessageDialog(frame, "Failed to register user!");
				}
			}
		});

		frame.add(panel);
		frame.setVisible(true);
		
	}

	@Override
	public void dashboard(String username) {
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.setLayout(new BorderLayout());

		JButton buyButton = new JButton("Buy");
		JButton checkoutButton = new JButton("Checkout"); // New button for checkout
		JButton logoutButton = new JButton("Logout");

		topPanel.add(buyButton);
		topPanel.add(checkoutButton); // Add the checkout button
		topPanel.add(logoutButton);

		displayArea = new JTextArea(10, 40);
		JScrollPane scrollPane = new JScrollPane(displayArea);

		// Fetch product details from the products table in the database
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		JScrollPane tableScrollPane = new JScrollPane(table);

		// Define columns for the table
		String[] columns = { "Product ID", "Item", "Quantity", "Price" };
		tableModel.setColumnIdentifiers(columns);

		// Creating an object of Inventory Class
		Inventory i = new Inventory();

		// Fetch data from the products table to populate the table
		try {
			List<Object[]> productList = i.showProducts();
			for (Object[] row : productList) {
				tableModel.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Failed to fetch product data from the database.");
		}

		bottomPanel.add(scrollPane, BorderLayout.NORTH);
		bottomPanel.add(tableScrollPane, BorderLayout.CENTER);

		panel.add(topPanel, BorderLayout.NORTH);
		panel.add(bottomPanel, BorderLayout.CENTER);
		

		cartItems.clear();
		

		buyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the selected row from the table
				float totalPrice = 0;
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					// Get the product details from the selected row
					int productId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
					String item = table.getValueAt(selectedRow, 1).toString();
					float price = Float.parseFloat(table.getValueAt(selectedRow, 3).toString());
					int quantity = Integer.parseInt(table.getValueAt(selectedRow, 2).toString());

					// Prompt a dialog box for quantity
					String quantityString = JOptionPane.showInputDialog(frame, "Enter Quantity:");
					
					if (quantityString != null && !quantityString.isEmpty()) {
						try{
							int orderQty = Integer.parseInt(quantityString);
							
							if(orderQty > quantity) {
								throw new QuantityOutOfBoundsException("Quantity Exceeds the Stock");
							}else if(orderQty < 1) {
								throw new QuantityOutOfBoundsException("Order Quantity cannot be less than 1");
							}
							// Calculate total price
							
							totalPrice = price * orderQty;

							// Add the order to the cart
							addToCart(productId, item, orderQty, totalPrice);
							
							// Updating the order in the database
							Orders order = new Orders(username, productId, orderQty, item, totalPrice);
							order.add();

							// Display cart contents
							displayCart();
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(frame, "Invalid quantity!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}catch(QuantityOutOfBoundsException e1) {
							JOptionPane.showMessageDialog(frame, e1.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please select a product!", "Error",
							JOptionPane.ERROR_MESSAGE);
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
					updateInventory(item.getProductId(), item.getQuantity());
				}

				// Display total price and order placed message
				JOptionPane.showMessageDialog(frame, "Order has been placed.\nTotal Price: Rs." + totalPrice,
						"Checkout", JOptionPane.INFORMATION_MESSAGE);

				// Clear the cart after checkout
				cartItems.clear();
				displayCart(); // Update the cart display
				// Refresh inventory table display
				refreshInventoryTable(username);
			}
		});

		logoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Logout successful!");
				logout();
			}
		});

		frame.add(panel);
		frame.setVisible(true);
		
	}

	@Override
	public boolean register(String username, String password) {
		// Connect to the database and insert user information into the clients table
		ClientUser user = new ClientUser();
		user.setUsername(username);
		user.setPassword(password);
		int success = user.add();

		if (success > 1) {
			return true;
		} else
			return false;
	}


	@Override
	public void refreshInventoryTable(String username) {
		frame.getContentPane().removeAll();
		frame.repaint();
		dashboard(username);
		
	}
	
	
	private boolean authenticateClientUser(String username, String password) {
		// Connect to the database and authenticate user
		ClientUser user = new ClientUser();
		user.setUsername(username);
		user.setPassword(password);
		boolean exist = user.authenticate();
		return exist;
	}

	
	private boolean updateInventory(int productId, int reduction) {
		// Call deduct() method from Inventory class
		Inventory inventory = new Inventory();
		inventory.setProductId(productId);
		int status = inventory.deduct(reduction);

		if (status > 1)
			return true;
		else
			return false;
	}
	
	public static class QuantityOutOfBoundsException extends Exception {
		private static final long serialVersionUID = 1L;

		public QuantityOutOfBoundsException() {
			super();
		}

		public QuantityOutOfBoundsException(String message) {
			super(message);
		}
	}



}
