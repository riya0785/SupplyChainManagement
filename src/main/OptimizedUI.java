package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import database.DBConnector;
import inventory.Inventory;
import inventory.Orders;
import users.AdminUser;
import users.ClientUser;

public class OptimizedUI extends DBConnector {

	private JFrame frame;
	private JPanel panel;
	private JButton loginButton;
	private JButton registerButton;
	private JButton clientButton;
	private JButton adminButton;
	private JTextArea displayArea;
	private boolean isLoggedIn = false;
	private List<CartItem> cartItems = new ArrayList<>();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new OptimizedUI();
			}
		});
	}

	public OptimizedUI() {
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
				clientLoginButtons();
			}
		});

		adminButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Redirect to admin window
				frame.getContentPane().removeAll();
				frame.repaint();
				adminLoginButton();
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

	public void clientLoginButtons() {
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
					clientLoginFields();
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
				clientRegistrationFields();
			}
		});

		frame.add(panel);
		frame.setVisible(true);
	}

	public void adminLoginButton() {
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
					adminLoginFields();
				} else {
					JOptionPane.showMessageDialog(frame, "You are already logged in!");
					frame.getContentPane().removeAll();
					frame.repaint();
					adminOptions();
				}
			}
		});

		frame.add(panel);
		frame.setVisible(true);
	}

	public void clientLoginFields() {
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
				if (authenticateClientUser(username, password)) {
					isLoggedIn = true;
					JOptionPane.showMessageDialog(frame, "Login successful!");
					frame.getContentPane().removeAll();
					frame.repaint();
					clientOptions();
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid username or password!");
				}
			}
		});

		frame.add(panel);
		frame.setVisible(true);
	}

	public void adminLoginFields() {
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
				if (authenticateAdminUser(username, password)) {
					isLoggedIn = true;
					JOptionPane.showMessageDialog(frame, "Login successful!");
					frame.getContentPane().removeAll();
					frame.repaint();
					adminOptions();
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid username or password!");
				}
			}
		});

		frame.add(panel);
		frame.setVisible(true);
	}

	public void clientRegistrationFields() {
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
				if (registerClientUser(username, password)) {
					JOptionPane.showMessageDialog(frame, "User Registered Successfully!");
					frame.getContentPane().removeAll();
					frame.repaint();
					clientLoginButtons(); // Revert back to the login screen
				} else {
					JOptionPane.showMessageDialog(frame, "Failed to register user!");
				}
			}
		});

		frame.add(panel);
		frame.setVisible(true);
	}

	public void adminRegistrationFields() {
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
				if (registerAdminUser(username, password)== true) {
					JOptionPane.showMessageDialog(frame, "User Registered Successfully!");
					frame.getContentPane().removeAll();
					frame.repaint();
					adminLoginButton(); // Revert back to the login screen
				} else {
					JOptionPane.showMessageDialog(frame, "Failed to register user!");
				}
			}
		});

		frame.add(panel);
		frame.setVisible(true);
	}

	public void clientOptions() {
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
							JOptionPane.showMessageDialog(frame, "Invalid quantity!", "Error",
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
					updateInventory(Integer.parseInt(item.getProductId()), item.getQuantity());
				}

				// Display total price and order placed message
				JOptionPane.showMessageDialog(frame, "Order has been placed.\nTotal Price: Rs." + totalPrice,
						"Checkout", JOptionPane.INFORMATION_MESSAGE);

				// Clear the cart after checkout
				cartItems.clear();
				displayCart(); // Update the cart display
				// Refresh inventory table display
				refreshClientInventoryTable();
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

	public void adminOptions() {
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		frame.setSize(700,400);
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.setLayout(new BorderLayout());

		JButton addAdmin = new JButton("Add Admin");
		JButton addItemButton = new JButton("Add Item");
		JButton updateItemButton = new JButton("Update Item");
		JButton deleteItemButton = new JButton("Delete Item");
		JButton orderHistoryButton = new JButton("Order History");
		JButton logoutButton = new JButton("Logout");

		topPanel.add(addAdmin);
		topPanel.add(addItemButton);
		topPanel.add(updateItemButton);
		topPanel.add(deleteItemButton);
		topPanel.add(orderHistoryButton);
		topPanel.add(logoutButton);

		displayArea = new JTextArea(10, 60);
		JScrollPane scrollPane = new JScrollPane(displayArea);

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

		addAdmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.repaint();
				adminRegistrationFields();
			}
		});

		addItemButton.addActionListener(new ActionListener() {
			Inventory i = new Inventory();

			@Override
			public void actionPerformed(ActionEvent e) {
				// Implement add item functionality here
				String productId = JOptionPane.showInputDialog(frame, "Enter Product ID:");
				String item = JOptionPane.showInputDialog(frame, "Enter Item:");
				String quantity = JOptionPane.showInputDialog(frame, "Enter Quantity:");
				String price = JOptionPane.showInputDialog(frame, "Enter Price:");

				// Adding into the Inventory
				i.setProductId(Integer.parseInt(productId));
				i.setItem(item);
				i.setQuantity(Integer.parseInt(quantity));
				i.setPrice(Integer.parseInt(price));

				String[] row = { productId, item, quantity, price };
				tableModel.addRow(row);
				i.add();
			}
		});

		updateItemButton.addActionListener(new ActionListener() {
			Inventory i = new Inventory();

			@Override
			public void actionPerformed(ActionEvent e) {
				// Implement update item functionality here
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					String productId = JOptionPane.showInputDialog(frame, "Enter Product ID:");
					String item = JOptionPane.showInputDialog(frame, "Enter Item:");
					String quantity = JOptionPane.showInputDialog(frame, "Enter Quantity:");
					String price = JOptionPane.showInputDialog(frame, "Enter Price:");

					// Updating Table
					tableModel.setValueAt(productId, selectedRow, 0);
					tableModel.setValueAt(item, selectedRow, 1);
					tableModel.setValueAt(quantity, selectedRow, 2);
					tableModel.setValueAt(price, selectedRow, 3);

					// Updating Inventory
					i.setProductId(Integer.parseInt(productId));
					i.setItem(item);
					i.setQuantity(Integer.parseInt(quantity));
					i.setPrice(Integer.parseInt(price));
					i.update();
					refreshAdminInventoryTable();

				} else {
					JOptionPane.showMessageDialog(frame, "Please select a product!");
				}
			}
		});

		deleteItemButton.addActionListener(new ActionListener() {
			Inventory i = new Inventory();

			@Override
			public void actionPerformed(ActionEvent e) {
				// Implement delete item functionality here
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					String productId = tableModel.getValueAt(selectedRow, 0).toString();
					tableModel.removeRow(selectedRow);
					i.setProductId(Integer.parseInt(productId));
					i.delete();

				} else {
					JOptionPane.showMessageDialog(frame, "Please select a product!");
				}
			}
		});

		orderHistoryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showOrderHistoryPopup(frame);
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

	// ----------Functional Methods--------------------------------

	private boolean registerClientUser(String username, String password) {
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

	private boolean registerAdminUser(String username, String password) {
		// Connect to the database and insert user information into the clients table
		AdminUser user = new AdminUser();
		user.setUsername(username);
		user.setPassword(password);
		int success = user.add();

		if (success > 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean authenticateAdminUser(String username, String password) {
		// Connect to the database and authenticate user
		AdminUser user = new AdminUser();
		user.setUsername(username);
		user.setPassword(password);
		boolean exist = user.authenticate();
		return exist;
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

	private void addToCart(String productId, String item, int quantity, float totalPrice) {
		// Create a new CartItem and add it to the cartItems list
		CartItem cartItem = new CartItem(productId, item, quantity, totalPrice);
		cartItems.add(cartItem);
	}

	public void refreshAdminInventoryTable() {
		frame.getContentPane().removeAll();
		frame.repaint();
		adminOptions();
		;
	}

	public void refreshClientInventoryTable() {
		frame.getContentPane().removeAll();
		frame.repaint();
		clientOptions();
		;
	}

	protected void displayCart() {
		StringBuilder cartDetails = new StringBuilder();
		cartDetails.append(
				"<html><body><h2>Your Cart:</h2><table border='1'><tr><th>Product ID</th><th>Item</th><th>Quantity</th><th>Total Price</th></tr>");

		for (CartItem item : cartItems) {
			cartDetails.append("<tr><td>").append(item.getProductId()).append("</td>");
			cartDetails.append("<td>").append(item.getItem()).append("</td>");
			cartDetails.append("<td>").append(item.getQuantity()).append("</td>");
			cartDetails.append("<td>").append(item.getTotalPrice()).append("</td></tr>");
		}

		cartDetails.append("</table></body></html>");

		JOptionPane.showMessageDialog(frame, cartDetails.toString(), "Cart", JOptionPane.INFORMATION_MESSAGE);
	}

	private void showOrderHistoryPopup(JFrame parentFrame) {
		// Create the order history pop-up dialog
		JDialog orderHistoryDialog = new JDialog(parentFrame, "Order History", true); // true makes it modal

		// Create a table model with the data and column names
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);

		// Add the table to a scroll pane for scrolling if necessary
		JScrollPane scrollPane = new JScrollPane(table);

		String[] columns = { "Order ID", "Username", "Product ID", "Item", "Quantity", "Price" };

		tableModel.setColumnIdentifiers(columns);

		Orders order = new Orders();
		// Fetch data from the products table to populate the table
		try {
			List<Object[]> orderList = order.showOrders();
			for (Object[] row : orderList) {
				tableModel.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Failed to fetch product data from the database.");
		}

		// Set layout for the pop-up dialog
		orderHistoryDialog.setLayout(new BorderLayout());

		// Add the scroll pane containing the table to the pop-up dialog
		orderHistoryDialog.add(scrollPane, BorderLayout.CENTER);

		// Set size and center the pop-up dialog
		orderHistoryDialog.setSize(600, 400);
		orderHistoryDialog.setLocationRelativeTo(parentFrame);

		// Make the dialog visible
		orderHistoryDialog.setVisible(true);
	}

	private void logout() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.dispose(); // Close the current window

        // 2. Open a login screen or another appropriate screen
        new OptimizedUI();
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
