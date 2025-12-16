package com.college.eventclub;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ManageClubsFrame - Frame for managing clubs
 */
public class ManageClubsFrame extends JFrame {
    private User currentUser;
    private ClubDAO clubDAO;
    private JTable clubTable;
    private DefaultTableModel tableModel;
    private JTextField clubNameField;
    private JTextArea descriptionArea;

    public ManageClubsFrame(User user) {
        this.currentUser = user;
        this.clubDAO = new ClubDAO();

        setTitle("Manage Clubs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initComponents();
        loadClubs();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 102, 204));
        JLabel titleLabel = new JLabel("Manage Clubs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Club Name
        JLabel nameLabel = new JLabel("Club Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(nameLabel, gbc);

        clubNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        formPanel.add(clubNameField, gbc);

        // Description
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        formPanel.add(descLabel, gbc);

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 11));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(descScrollPane, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton addButton = new JButton("Add Club");
        addButton.setFont(new Font("Arial", Font.BOLD, 11));
        addButton.setBackground(new Color(0, 102, 204));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> addClub());
        buttonPanel.add(addButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 11));
        clearButton.setBackground(new Color(128, 128, 128));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> clearFields());
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 10, 5);
        formPanel.add(buttonPanel, gbc);

        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(new Color(240, 240, 240));
        tablePanel.setBorder(BorderFactory.createTitledBorder("Existing Clubs"));

        String[] columnNames = {"Club ID", "Club Name", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0);
        clubTable = new JTable(tableModel);
        clubTable.setFont(new Font("Arial", Font.PLAIN, 11));
        clubTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        clubTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableScrollPane = new JScrollPane(clubTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Main layout
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadClubs() {
        tableModel.setRowCount(0);
        List<Club> clubs = clubDAO.getAllClubs();
        for (Club club : clubs) {
            tableModel.addRow(new Object[]{
                club.getClubId(),
                club.getClubName(),
                club.getDescription()
            });
        }
    }

    private void addClub() {
        String clubName = clubNameField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (clubName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter club name",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (clubDAO.clubNameExists(clubName)) {
            JOptionPane.showMessageDialog(this,
                "Club name already exists",
                "Duplicate Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Club club = new Club(clubName, description, currentUser.getUserId());
        if (clubDAO.addClub(club)) {
            JOptionPane.showMessageDialog(this,
                "Club added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            loadClubs();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error adding club",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        clubNameField.setText("");
        descriptionArea.setText("");
    }
}
