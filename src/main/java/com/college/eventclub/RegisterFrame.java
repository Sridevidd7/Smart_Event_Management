package com.college.eventclub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * RegisterFrame - User registration form for new account creation
 */
public class RegisterFrame extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> roleComboBox;
    private UserDAO userDAO;

    public RegisterFrame() {
        this.userDAO = new UserDAO();
        setTitle("Smart Event Management System - Register");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 102, 204));
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(confirmLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        confirmPasswordField = new JPasswordField(20);
        formPanel.add(confirmPasswordField, gbc);

        // Role Selection
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(roleLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        // Check if any admin exists - if yes, only allow STUDENT registration
        int adminCount = userDAO.countAdminUsers();
        if (adminCount > 0) {
            roleComboBox = new JComboBox<>(new String[]{"STUDENT"});
        } else {
            // First admin can be created
            roleComboBox = new JComboBox<>(new String[]{"STUDENT", "ADMIN"});
        }
        roleComboBox.setSelectedIndex(0); // Default to STUDENT
        formPanel.add(roleComboBox, gbc);

        // Buttons Panel
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 12));
        registerButton.setBackground(new Color(0, 102, 204));
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(100, 35));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(this::registerAction);

        JButton backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setBackground(new Color(128, 128, 128));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(120, 35));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            this.dispose();
            new LoginFrame();
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(backButton);
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void registerAction(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        // Validation
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full name cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if email already exists
        if (userDAO.getUserByEmail(email) != null) {
            JOptionPane.showMessageDialog(this, "Email already registered", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Handle ADMIN role registration
        if ("ADMIN".equals(role)) {
            // Check if admin already exists
            if (userDAO.countAdminUsers() > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Admin account already exists!\n\n" +
                    "Only one administrator can be registered in the system.\n" +
                    "Please register as a STUDENT instead.", 
                    "Admin Limit Reached", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Create new user
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(role);

        // Add to database
        if (userDAO.addUser(newUser)) {
            JOptionPane.showMessageDialog(this, 
                "Registration successful!\nEmail: " + email + "\nRole: " + role + "\n\nYou can now login.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);

            // Clear fields
            nameField.setText("");
            emailField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");

            // Close and open login
            this.dispose();
            new LoginFrame();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
