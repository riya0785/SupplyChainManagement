package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrganicAuraUI {
    private JFrame frame;
    private JPanel panel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton clientButton;
    private JButton adminButton;
    private JButton buyButton;
    private JButton dashboardButton;
    private JButton addItemButton;
    private JButton updateItemButton;
    private JButton deleteItemButton;
    private JTextArea displayArea;
    private JTable table;
    private DefaultTableModel tableModel;

    public OrganicAuraUI() {
        frame = new JFrame("Supply Chain Management System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel projectLabel = new JLabel("Supply Chain Management System");
        projectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(projectLabel);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        panel.add(loginButton);
        panel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                addLoginButtons();
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

    public void addLoginButtons() {
        panel.removeAll();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        clientButton = new JButton("Client");
        adminButton = new JButton("Admin");
        panel.add(clientButton);
        panel.add(adminButton);

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                addClientOptions();
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                addAdminOptions();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
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
                JOptionPane.showMessageDialog(frame, "User Registered Successfully!");
                frame.getContentPane().removeAll();
                frame.repaint();
                addLoginButtons();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public void addClientOptions() {
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setLayout(new BorderLayout());

        buyButton = new JButton("Buy");
        dashboardButton = new JButton("Dashboard");

        topPanel.add(buyButton);
        topPanel.add(dashboardButton);

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

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement buy functionality here
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String quantity = JOptionPane.showInputDialog(frame, "Enter quantity:");
                    int qty = Integer.parseInt(quantity);
                    int price = Integer.parseInt((String) tableModel.getValueAt(selectedRow, 3));
                    int totalPrice = qty * price;
                    JOptionPane.showMessageDialog(frame, "Total Price: " + totalPrice);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a product!");
                }
            }
        });

        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement dashboard functionality here
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

        addItemButton = new JButton("Add Item");
        updateItemButton = new JButton("Update Item");
        deleteItemButton = new JButton("Delete Item");

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OrganicAuraUI();
            }
        });
    }
}