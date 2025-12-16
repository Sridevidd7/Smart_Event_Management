package com.college.eventclub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AdminDashboardFrame - Main dashboard for admin users
 */
public class AdminDashboardFrame extends JFrame {
    private User currentUser;
    private JLabel welcomeLabel;
    private JPanel mainPanel;

    public AdminDashboardFrame(User user) {
        this.currentUser = user;
        setTitle("Smart Event Management System - Admin Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 102, 204));
        titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(welcomeLabel, BorderLayout.EAST);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 15, 15));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add Event Button
        JButton addEventButton = createButton("Add Event");
        addEventButton.addActionListener(e -> openAddEventFrame());
        buttonPanel.add(addEventButton);

        // Manage Clubs Button
        JButton manageClubsButton = createButton("Manage Clubs");
        manageClubsButton.addActionListener(e -> openManageClubsFrame());
        buttonPanel.add(manageClubsButton);

        // View Events Button
        JButton viewEventsButton = createButton("View Events");
        viewEventsButton.addActionListener(e -> openViewEventsFrame());
        buttonPanel.add(viewEventsButton);

        // QR Check-In Button
        JButton qrCheckInButton = createButton("QR Check-In");
        qrCheckInButton.addActionListener(e -> openQRCheckInFrame());
        buttonPanel.add(qrCheckInButton);

        // View Registrations Button
        JButton viewRegistrationsButton = createButton("View Registrations");
        viewRegistrationsButton.addActionListener(e -> openViewRegistrationsFrame());
        buttonPanel.add(viewRegistrationsButton);

        // Logout Button
        JButton logoutButton = createButton("Logout");
        logoutButton.setBackground(new Color(220, 50, 50));
        logoutButton.addActionListener(e -> logout());
        buttonPanel.add(logoutButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void openAddEventFrame() {
        new AddEventFrame(currentUser);
    }

    private void openManageClubsFrame() {
        new ManageClubsFrame(currentUser);
    }

    private void openViewEventsFrame() {
        new ViewEventsFrame(currentUser);
    }

    private void openQRCheckInFrame() {
        new QRCheckInFrame(currentUser);
    }

    private void openViewRegistrationsFrame() {
        new ViewRegistrationsFrame(currentUser);
    }

    private void logout() {
        int result = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginFrame();
        }
    }
}
