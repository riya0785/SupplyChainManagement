package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.table.DefaultTableModel;

import inventory.Inventory;
import inventory.Orders;
import users.AdminUser;

public class AdminUI extends OptimizedUI implements UIActions{

	private String username;
	
	@Override
	public void loginButtons() {
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
					loginFields();
				} else {
					JOptionPane.showMessageDialog(frame, "You are already logged in!");
					frame.getContentPane().removeAll();
					frame.repaint();
					dashboard(username);
				}
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
		styleButton(login);

		panel.add(usernamePanel);
		panel.add(passwordPanel);
		panel.add(login);

		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				// Authenticate user
				if (authenticateAdminUser(username, password)) {
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
		styleButton(register);

		panel.add(usernameLabel);
		panel.add(usernameField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(register);

		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				// Implement registration functionality here
				if (register(username, password)== true) {
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
		frame.setSize(700,400);
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.setLayout(new BorderLayout());

		JButton addAdmin = new JButton("Add Admin");
		styleDashboardButton(addAdmin);
		JButton addItemButton = new JButton("Add Item");
		styleDashboardButton(addItemButton);
		JButton updateItemButton = new JButton("Update Item");
		styleDashboardButton(updateItemButton);
		JButton deleteItemButton = new JButton("Delete Item");
		styleDashboardButton(deleteItemButton);
		JButton orderHistoryButton = new JButton("Order History");
		styleDashboardButton(orderHistoryButton);
		orderHistoryButton.setPreferredSize(new Dimension(150,50));
		JButton logoutButton = new JButton("Logout");
		styleLogoutButton(logoutButton);
		
		

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
				registrationFields();
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
//					String productId = JOptionPane.showInputDialog(frame, "Enter Product ID:");
					String item = JOptionPane.showInputDialog(frame, "Enter Item:");
					String quantity = JOptionPane.showInputDialog(frame, "Enter Quantity:");
					String price = JOptionPane.showInputDialog(frame, "Enter Price:");

					// Updating Table
					String productId =  tableModel.getValueAt(selectedRow, 0).toString();
					tableModel.setValueAt(item, selectedRow, 1);
					tableModel.setValueAt(quantity, selectedRow, 2);
					tableModel.setValueAt(price, selectedRow, 3);

					// Updating Inventory
					i.setProductId(Integer.parseInt(productId));
					i.setItem(item);
					i.setQuantity(Integer.parseInt(quantity));
					i.setPrice(Integer.parseInt(price));
					i.update();
					refreshInventoryTable(username);

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

	@Override
	public boolean register(String username, String password) {
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

	@Override
	public void refreshInventoryTable(String username) {
		frame.getContentPane().removeAll();
		frame.repaint();
		dashboard(username);
		
	}
	
	private boolean authenticateAdminUser(String username, String password) {
		// Connect to the database and authenticate user
		AdminUser user = new AdminUser();
		user.setUsername(username);
		user.setPassword(password);
		boolean exist = user.authenticate();
		return exist;
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



}
