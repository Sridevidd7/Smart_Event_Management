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
 * ViewEventsFrame - Frame for viewing all events
 */
public class ViewEventsFrame extends JFrame {
    private User currentUser;
    private EventDAO eventDAO;
    private JTable eventTable;
    private DefaultTableModel tableModel;

    public ViewEventsFrame(User user) {
        this.currentUser = user;
        this.eventDAO = new EventDAO();

        setTitle("View Events");
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
        JLabel titleLabel = new JLabel("All Events");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(titleLabel);

        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(new Color(240, 240, 240));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Event ID", "Title", "Club ID", "Date", "Venue", "Status"};
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

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 11));
        refreshButton.setBackground(new Color(0, 102, 204));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(e -> loadEvents());
        buttonPanel.add(refreshButton);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 11));
        deleteButton.setBackground(new Color(220, 50, 50));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> deleteEvent());
        buttonPanel.add(deleteButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadEvents() {
        tableModel.setRowCount(0);
        List<Event> events = eventDAO.getAllEvents();
        for (Event event : events) {
            tableModel.addRow(new Object[]{
                event.getEventId(),
                event.getEventTitle(),
                event.getClubId(),
                event.getEventDate(),
                event.getVenue(),
                event.getStatus()
            });
        }
    }

    private void deleteEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an event to delete", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int eventId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this event?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (eventDAO.deleteEvent(eventId)) {
                JOptionPane.showMessageDialog(this, "Event deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadEvents();
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting event", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
