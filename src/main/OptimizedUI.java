package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import database.DBConnector;
import inventory.CartItem;

public class OptimizedUI extends DBConnector {

	protected JFrame frame;
	protected JPanel panel;
	protected JButton loginButton;
	protected JButton registerButton;
	protected JButton clientButton;
	protected JButton adminButton;
	protected JTextArea displayArea;
	protected boolean isLoggedIn = false;
	protected List<CartItem> cartItems = new ArrayList<>();

	
	//---------------------Main---------------------------
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
				ClientUI clientUI = new ClientUI();
				clientUI.loginButtons();
			}
		});

		adminButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Redirect to admin window
				frame.getContentPane().removeAll();
				frame.repaint();
				AdminUI adminUI = new AdminUI();
				adminUI.loginButtons();
			}
		});

		frame.add(panel);
		frame.setVisible(true);
	}

	protected void styleButton(JButton button) {
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


	// ----------Functional Methods--------------------------------

	protected void addToCart(int productId, String item, int quantity, float totalPrice) {
		// Create a new CartItem and add it to the cartItems list
		CartItem cartItem = new CartItem(productId, item, quantity, totalPrice);
		cartItems.add(cartItem);
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

	public void logout() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.dispose(); // Close the current window

        // 2. Open a login screen or another appropriate screen
        new OptimizedUI();
	}
	
}
