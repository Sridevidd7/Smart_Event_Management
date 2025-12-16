package com.college.eventclub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * QRCheckInFrame - Frame for QR-based attendance check-in
 */
public class QRCheckInFrame extends JFrame {
    private User currentUser;
    private RegistrationDAO registrationDAO;
    private AttendanceDAO attendanceDAO;
    private UserDAO userDAO;

    private JTextField qrTokenField;
    private JTextArea resultArea;

    public QRCheckInFrame(User user) {
        this.currentUser = user;
        this.registrationDAO = new RegistrationDAO();
        this.attendanceDAO = new AttendanceDAO();
        this.userDAO = new UserDAO();

        setTitle("QR Check-In");
        setSize(600, 500);
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
        JLabel titleLabel = new JLabel("QR Code Check-In");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // QR Token Input
        JLabel qrLabel = new JLabel("QR Token:");
        qrLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        formPanel.add(qrLabel, gbc);

        qrTokenField = new JTextField(30);
        qrTokenField.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.75;
        formPanel.add(qrTokenField, gbc);

        // Result Area
        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.25;
        gbc.anchor = GridBagConstraints.NORTH;
        formPanel.add(resultLabel, gbc);

        resultArea = new JTextArea(10, 30);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 11));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(new Color(255, 255, 255));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.75;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(scrollPane, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));

        JButton checkInButton = new JButton("Check-In");
        checkInButton.setFont(new Font("Arial", Font.BOLD, 12));
        checkInButton.setBackground(new Color(0, 102, 204));
        checkInButton.setForeground(Color.WHITE);
        checkInButton.addActionListener(e -> performCheckIn());
        buttonPanel.add(checkInButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearButton.setBackground(new Color(128, 128, 128));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> clearFields());
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(20, 8, 8, 8);
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void performCheckIn() {
        String qrToken = qrTokenField.getText().trim();

        if (qrToken.isEmpty()) {
            resultArea.setText("Error: Please enter QR token");
            return;
        }

        try {
            Registration registration = registrationDAO.getRegistrationByQRToken(qrToken);

            if (registration == null) {
                resultArea.setText("Error: Invalid QR token. Registration not found.");
                return;
            }

            if (attendanceDAO.isAttendanceMarked(registration.getRegistrationId())) {
                LocalDateTime checkInTime = attendanceDAO.getCheckInTime(registration.getRegistrationId());
                resultArea.setText("Warning: This user has already checked in.\n" +
                        "Check-in time: " + checkInTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                return;
            }

            // Add attendance record
            if (attendanceDAO.addAttendance(registration.getRegistrationId())) {
                // Update registration status
                registrationDAO.updateRegistrationStatus(registration.getRegistrationId(), "CHECKED_IN");

                User user = userDAO.getUserById(registration.getUserId());
                Event event = new EventDAO().getEventById(registration.getEventId());

                LocalDateTime checkInTime = LocalDateTime.now();
                resultArea.setText("✓ Check-In Successful!\n\n" +
                        "Student: " + (user != null ? user.getName() : "Unknown") + "\n" +
                        "Email: " + (user != null ? user.getEmail() : "N/A") + "\n" +
                        "Event: " + (event != null ? event.getEventTitle() : "Unknown") + "\n" +
                        "Check-In Time: " + checkInTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                qrTokenField.setText("");
            } else {
                resultArea.setText("Error: Failed to record attendance");
            }
        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        qrTokenField.setText("");
        resultArea.setText("");
    }
}
