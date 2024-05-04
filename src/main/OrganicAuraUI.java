package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OrganicAuraUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardsPanel;

    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;

    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    public OrganicAuraUI() {
        setTitle("OrganicAura");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        // Initial Page
        JPanel initialPanel = new JPanel(new GridLayout(2, 1));
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "register");
            }
        });
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "login");
            }
        });
        initialPanel.add(registerButton);
        initialPanel.add(loginButton);
        cardsPanel.add(initialPanel, "initial");

        // Registration Page
        JPanel registerPanel = new JPanel(new GridLayout(3, 1));
        registerUsernameField = new JTextField();
        registerUsernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        registerPasswordField = new JPasswordField();
        registerPasswordField.setBorder(BorderFactory.createTitledBorder("Password"));
        JButton registerSubmitButton = new JButton("Register");
        registerSubmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add user to database
                register();
            }
        });
        registerPanel.add(registerUsernameField);
        registerPanel.add(registerPasswordField);
        registerPanel.add(registerSubmitButton);
        cardsPanel.add(registerPanel, "register");

        // Login Page
        JPanel loginPanel = new JPanel(new GridLayout(3, 1));
        JButton clientLoginButton = new JButton("Client");
        clientLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "clientLogin");
            }
        });
        JButton adminLoginButton = new JButton("Admin");
        // Add ActionListener for admin login if needed
        loginPanel.add(clientLoginButton);
        loginPanel.add(adminLoginButton);
        cardsPanel.add(loginPanel, "login");

        // Client Login Page
        JPanel clientLoginPanel = new JPanel(new GridLayout(3, 1));
        loginUsernameField = new JTextField();
        loginUsernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        loginPasswordField = new JPasswordField();
        loginPasswordField.setBorder(BorderFactory.createTitledBorder("Password"));
        JButton clientLoginSubmitButton = new JButton("Login");
        clientLoginSubmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Validate user and show dashboard
                clientLogin();
            }
        });
        clientLoginPanel.add(loginUsernameField);
        clientLoginPanel.add(loginPasswordField);
        clientLoginPanel.add(clientLoginSubmitButton);
        cardsPanel.add(clientLoginPanel, "clientLogin");

        add(cardsPanel);

        cardLayout.show(cardsPanel, "initial");
        setVisible(true);
    }

    private void register() {
        // Implement registration logic here
        String username = registerUsernameField.getText();
        String password = new String(registerPasswordField.getPassword());
        // Add user to database
        // Show success or failure message
    }

    private void clientLogin() {
        // Implement client login logic here
        String username = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());
        // Validate user and show dashboard
        // If validation fails, show error message
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OrganicAuraUI();
            }
        });
    }
}
