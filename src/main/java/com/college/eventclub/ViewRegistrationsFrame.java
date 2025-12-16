package com.college.eventclub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * ViewRegistrationsFrame - Frame for viewing event registrations
 */
public class ViewRegistrationsFrame extends JFrame {
    private User currentUser;
    private EventDAO eventDAO;
    private RegistrationDAO registrationDAO;
    private AttendanceDAO attendanceDAO;
    private JComboBox<Event> eventComboBox;
    private JTable registrationTable;
    private DefaultTableModel tableModel;

    public ViewRegistrationsFrame(User user) {
        this.currentUser = user;
        this.eventDAO = new EventDAO();
        this.registrationDAO = new RegistrationDAO();
        this.attendanceDAO = new AttendanceDAO();

        setTitle("View Registrations");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

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
        JLabel titleLabel = new JLabel("View Registrations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(titleLabel);

        // Filter Panel
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(new Color(240, 240, 240));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel eventLabel = new JLabel("Select Event:");
        eventLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        filterPanel.add(eventLabel);

        eventComboBox = new JComboBox<>();
        eventComboBox.setFont(new Font("Arial", Font.PLAIN, 11));
        filterPanel.add(eventComboBox);

        // Table Panel (created BEFORE loading events to avoid null tableModel)
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(new Color(240, 240, 240));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Registration ID", "User ID", "QR Token", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        registrationTable = new JTable(tableModel);
        registrationTable.setFont(new Font("Arial", Font.PLAIN, 11));
        registrationTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        registrationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableScrollPane = new JScrollPane(registrationTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // NOW load events (after tableModel is created)
        eventComboBox.addActionListener(e -> loadRegistrations());
        loadEvents();

        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(new Color(240, 240, 240));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel statsLabel = new JLabel();
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statsPanel.add(statsLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(filterPanel, BorderLayout.PAGE_START);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(statsPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadEvents() {
        eventComboBox.removeAllItems();
        eventComboBox.addItem(new Event());
        for (Event event : eventDAO.getAllEvents()) {
            eventComboBox.addItem(event);
        }
    }

    private void loadRegistrations() {
        if (tableModel == null) return; // Guard against null tableModel
        
        tableModel.setRowCount(0);
        Event selectedEvent = (Event) eventComboBox.getSelectedItem();

        if (selectedEvent == null || selectedEvent.getEventId() == 0) {
            return;
        }

        List<Registration> registrations = registrationDAO.getRegistrationsByEventId(selectedEvent.getEventId());
        for (Registration reg : registrations) {
            tableModel.addRow(new Object[]{
                reg.getRegistrationId(),
                reg.getUserId(),
                reg.getQrToken().substring(0, Math.min(10, reg.getQrToken().length())) + "...",
                reg.getStatus()
            });
        }
    }
}
