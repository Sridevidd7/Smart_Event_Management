package com.college.eventclub;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * AddEventFrame - Frame for adding new events
 */
public class AddEventFrame extends JFrame {
    private User currentUser;
    private EventDAO eventDAO;
    private ClubDAO clubDAO;

    private JTextField eventTitleField;
    private JComboBox<Club> clubComboBox;
    private JTextField eventDateField;
    private JTextField venueField;
    private JTextArea descriptionArea;

    public AddEventFrame(User user) {
        this.currentUser = user;
        this.eventDAO = new EventDAO();
        this.clubDAO = new ClubDAO();

        setTitle("Add Event");
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
        JLabel titleLabel = new JLabel("Add New Event");
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

        // Event Title
        JLabel titleFieldLabel = new JLabel("Event Title:");
        titleFieldLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        formPanel.add(titleFieldLabel, gbc);

        eventTitleField = new JTextField(25);
        eventTitleField.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.75;
        formPanel.add(eventTitleField, gbc);

        // Club Selection
        JLabel clubLabel = new JLabel("Club:");
        clubLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.25;
        formPanel.add(clubLabel, gbc);

        clubComboBox = new JComboBox<>();
        clubComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        loadClubs();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.75;
        formPanel.add(clubComboBox, gbc);

        // Event Date
        JLabel dateLabel = new JLabel("Event Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.25;
        formPanel.add(dateLabel, gbc);

        eventDateField = new JTextField(25);
        eventDateField.setFont(new Font("Arial", Font.PLAIN, 12));
        eventDateField.setText(LocalDate.now().toString());
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.75;
        formPanel.add(eventDateField, gbc);

        // Venue
        JLabel venueLabel = new JLabel("Venue:");
        venueLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.25;
        formPanel.add(venueLabel, gbc);

        venueField = new JTextField(25);
        venueField.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.75;
        formPanel.add(venueField, gbc);

        // Description
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTH;
        formPanel.add(descLabel, gbc);

        descriptionArea = new JTextArea(4, 25);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(scrollPane, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));

        JButton saveButton = new JButton("Save Event");
        saveButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveButton.setBackground(new Color(0, 102, 204));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> saveEvent());
        buttonPanel.add(saveButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearButton.setBackground(new Color(128, 128, 128));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> clearFields());
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadClubs() {
        clubComboBox.removeAllItems();
        clubComboBox.addItem(new Club(0, "-- Select Club --", ""));
        for (Club club : clubDAO.getAllClubs()) {
            clubComboBox.addItem(club);
        }
    }

    private void saveEvent() {
        String eventTitle = eventTitleField.getText().trim();
        Club selectedClub = (Club) clubComboBox.getSelectedItem();
        String eventDateStr = eventDateField.getText().trim();
        String venue = venueField.getText().trim();
        String description = descriptionArea.getText().trim();

        // Validation
        if (eventTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter event title", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selectedClub == null || selectedClub.getClubId() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a club", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (eventDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter event date", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (venue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter venue", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LocalDate eventDate = LocalDate.parse(eventDateStr);
            Event event = new Event(eventTitle, selectedClub.getClubId(), eventDate, venue, description, currentUser.getUserId());
            
            if (eventDAO.addEvent(event)) {
                JOptionPane.showMessageDialog(this, "Event added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Error adding event", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        eventTitleField.setText("");
        clubComboBox.setSelectedIndex(0);
        eventDateField.setText(LocalDate.now().toString());
        venueField.setText("");
        descriptionArea.setText("");
    }
}
