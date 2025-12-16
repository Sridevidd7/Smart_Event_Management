package com.college.eventclub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * StudentViewEventsFrame - View available events for students
 */
public class StudentViewEventsFrame extends JFrame {
    private User currentUser;
    private EventDAO eventDAO;
    private RegistrationDAO registrationDAO;
    private JTable eventTable;
    private DefaultTableModel tableModel;

    public StudentViewEventsFrame(User user) {
        this.currentUser = user;
        this.eventDAO = new EventDAO();
        this.registrationDAO = new RegistrationDAO();

        setTitle("View Available Events");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initComponents();
        loadEvents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 102, 204));
        JLabel titleLabel = new JLabel("Available Events");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(titleLabel);

        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(new Color(240, 240, 240));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Event ID", "Title", "Date", "Venue", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        eventTable = new JTable(tableModel);
        eventTable.setFont(new Font("Arial", Font.PLAIN, 11));
        eventTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventTable.setRowHeight(25);

        JScrollPane tableScrollPane = new JScrollPane(eventTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton registerButton = new JButton("Register for Event");
        registerButton.setFont(new Font("Arial", Font.BOLD, 11));
        registerButton.setBackground(new Color(0, 102, 204));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(e -> registerForEvent());
        buttonPanel.add(registerButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 11));
        refreshButton.setBackground(new Color(128, 128, 128));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(e -> loadEvents());
        buttonPanel.add(refreshButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadEvents() {
        tableModel.setRowCount(0);
        List<Event> events = eventDAO.getUpcomingEvents();
        for (Event event : events) {
            tableModel.addRow(new Object[]{
                event.getEventId(),
                event.getEventTitle(),
                event.getEventDate(),
                event.getVenue(),
                event.getStatus()
            });
        }
    }

    private void registerForEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an event to register", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int eventId = (int) tableModel.getValueAt(selectedRow, 0);

        // Check if already registered
        if (registrationDAO.isUserRegisteredForEvent(eventId, currentUser.getUserId())) {
            JOptionPane.showMessageDialog(this, "You are already registered for this event", "Already Registered", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Generate QR token and register
            String qrToken = QRCodeUtil.generateQRToken();
            Registration registration = new Registration(eventId, currentUser.getUserId(), qrToken);
            int registrationId = registrationDAO.addRegistration(registration);

            if (registrationId > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Registration successful! Your QR Token: " + qrToken,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                loadEvents();
            } else {
                JOptionPane.showMessageDialog(this, "Error registering for event", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
