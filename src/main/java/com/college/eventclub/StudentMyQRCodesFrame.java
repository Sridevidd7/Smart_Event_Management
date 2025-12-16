package com.college.eventclub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * StudentMyQRCodesFrame - View all QR codes for student's registrations
 */
public class StudentMyQRCodesFrame extends JFrame {
    private User currentUser;
    private RegistrationDAO registrationDAO;
    private EventDAO eventDAO;
    private JPanel qrPanel;

    public StudentMyQRCodesFrame(User user) {
        this.currentUser = user;
        this.registrationDAO = new RegistrationDAO();
        this.eventDAO = new EventDAO();

        setTitle("My QR Codes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initComponents();
        loadQRCodes();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 102, 204));
        JLabel titleLabel = new JLabel("My QR Codes");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(titleLabel);

        // QR Panel
        qrPanel = new JPanel();
        qrPanel.setLayout(new GridLayout(2, 2, 15, 15));
        qrPanel.setBackground(new Color(240, 240, 240));
        qrPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(qrPanel);
        scrollPane.setBackground(new Color(240, 240, 240));

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadQRCodes() {
        qrPanel.removeAll();
        List<Registration> registrations = registrationDAO.getRegistrationsByUserId(currentUser.getUserId());

        if (registrations.isEmpty()) {
            JLabel noRegLabel = new JLabel("No registrations found");
            noRegLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            qrPanel.add(noRegLabel);
        } else {
            for (Registration reg : registrations) {
                Event event = eventDAO.getEventById(reg.getEventId());
                JPanel cardPanel = createQRCard(reg, event);
                qrPanel.add(cardPanel);
            }
        }

        qrPanel.revalidate();
        qrPanel.repaint();
    }

    private JPanel createQRCard(Registration registration, Event event) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        // Event info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);

        String eventName = event != null ? event.getEventTitle() : "Unknown Event";
        JLabel eventLabel = new JLabel(eventName);
        eventLabel.setFont(new Font("Arial", Font.BOLD, 12));
        eventLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton showQRButton = new JButton("Show QR");
        showQRButton.setFont(new Font("Arial", Font.BOLD, 10));
        showQRButton.setBackground(new Color(0, 102, 204));
        showQRButton.setForeground(Color.WHITE);
        String token = registration.getQrToken();
        showQRButton.addActionListener(e -> QRCodeUtil.displayQRCode(token, "QR Code for " + eventName));

        infoPanel.add(eventLabel, BorderLayout.WEST);
        infoPanel.add(showQRButton, BorderLayout.EAST);

        // Status
        JLabel statusLabel = new JLabel("Status: " + registration.getStatus());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        statusLabel.setForeground(new Color(100, 100, 100));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        card.add(infoPanel, BorderLayout.NORTH);
        card.add(statusLabel, BorderLayout.SOUTH);

        return card;
    }
}
