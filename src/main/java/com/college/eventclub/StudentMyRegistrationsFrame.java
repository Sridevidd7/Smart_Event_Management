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
 * StudentMyRegistrationsFrame - View student's registrations
 */
public class StudentMyRegistrationsFrame extends JFrame {
    private User currentUser;
    private RegistrationDAO registrationDAO;
    private EventDAO eventDAO;
    private AttendanceDAO attendanceDAO;
    private JTable registrationTable;
    private DefaultTableModel tableModel;

    public StudentMyRegistrationsFrame(User user) {
        this.currentUser = user;
        this.registrationDAO = new RegistrationDAO();
        this.eventDAO = new EventDAO();
        this.attendanceDAO = new AttendanceDAO();

        setTitle("My Registrations");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initComponents();
        loadRegistrations();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 102, 204));
        JLabel titleLabel = new JLabel("My Registrations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(titleLabel);

        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(new Color(240, 240, 240));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Event Title", "Date", "Venue", "Status", "Checked In"};
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
        registrationTable.setRowHeight(25);

        JScrollPane tableScrollPane = new JScrollPane(registrationTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton viewQRButton = new JButton("View QR Code");
        viewQRButton.setFont(new Font("Arial", Font.BOLD, 11));
        viewQRButton.setBackground(new Color(0, 102, 204));
        viewQRButton.setForeground(Color.WHITE);
        viewQRButton.addActionListener(e -> viewQRCode());
        buttonPanel.add(viewQRButton);

        JButton cancelButton = new JButton("Cancel Registration");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 11));
        cancelButton.setBackground(new Color(220, 50, 50));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> cancelRegistration());
        buttonPanel.add(cancelButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadRegistrations() {
        tableModel.setRowCount(0);
        List<Registration> registrations = registrationDAO.getRegistrationsByUserId(currentUser.getUserId());
        
        for (Registration reg : registrations) {
            Event event = eventDAO.getEventById(reg.getEventId());
            boolean checkedIn = attendanceDAO.isAttendanceMarked(reg.getRegistrationId());
            
            if (event != null) {
                tableModel.addRow(new Object[]{
                    event.getEventTitle(),
                    event.getEventDate(),
                    event.getVenue(),
                    reg.getStatus(),
                    checkedIn ? "Yes" : "No"
                });
            }
        }
    }

    private void viewQRCode() {
        int selectedRow = registrationTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a registration", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Registration> registrations = registrationDAO.getRegistrationsByUserId(currentUser.getUserId());
            if (selectedRow < registrations.size()) {
                Registration reg = registrations.get(selectedRow);
                Event event = eventDAO.getEventById(reg.getEventId());
                String info = "Event: " + (event != null ? event.getEventTitle() : "Unknown") + "\n" +
                             "QR Token: " + reg.getQrToken();
                QRCodeUtil.displayQRCodeWithInfo(reg.getQrToken(), "Your QR Code", info);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error displaying QR Code: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelRegistration() {
        int selectedRow = registrationTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a registration to cancel", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to cancel this registration?",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                List<Registration> registrations = registrationDAO.getRegistrationsByUserId(currentUser.getUserId());
                if (selectedRow < registrations.size()) {
                    Registration reg = registrations.get(selectedRow);
                    if (registrationDAO.updateRegistrationStatus(reg.getRegistrationId(), "CANCELLED")) {
                        JOptionPane.showMessageDialog(this, "Registration cancelled successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadRegistrations();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error cancelling registration", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
