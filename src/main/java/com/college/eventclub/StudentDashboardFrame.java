package com.college.eventclub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * StudentDashboardFrame - Dashboard for student users
 */
public class StudentDashboardFrame extends JFrame {
    private User currentUser;
    private JLabel welcomeLabel;

    public StudentDashboardFrame(User user) {
        this.currentUser = user;
        setTitle("Smart Event Management System - Student Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Student Dashboard");
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
        buttonPanel.setLayout(new GridLayout(2, 2, 15, 15));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // View Available Events Button
        JButton viewEventsButton = createButton("View Available Events");
        viewEventsButton.addActionListener(e -> openViewAvailableEventsFrame());
        buttonPanel.add(viewEventsButton);

        // My Registrations Button
        JButton myRegistrationsButton = createButton("My Registrations");
        myRegistrationsButton.addActionListener(e -> openMyRegistrationsFrame());
        buttonPanel.add(myRegistrationsButton);

        // View My QR Codes Button
        JButton qrCodesButton = createButton("View My QR Codes");
        qrCodesButton.addActionListener(e -> openMyQRCodesFrame());
        buttonPanel.add(qrCodesButton);

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

    private void openViewAvailableEventsFrame() {
        new StudentViewEventsFrame(currentUser);
    }

    private void openMyRegistrationsFrame() {
        new StudentMyRegistrationsFrame(currentUser);
    }

    private void openMyQRCodesFrame() {
        new StudentMyQRCodesFrame(currentUser);
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
