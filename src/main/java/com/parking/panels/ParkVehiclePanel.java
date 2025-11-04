package com.parking.panels;

import com.parking.MainFrame;
import com.parking.dao.VehicleDAO;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class ParkVehiclePanel extends JPanel {

    private MainFrame mainFrame;
    private JTextField vehicleNumberField;
    private JComboBox<String> vehicleTypeBox;

    public ParkVehiclePanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 248, 252));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(33, 150, 243));
        header.setPreferredSize(new Dimension(0, 70));

        JLabel title = new JLabel("🅿️ Park Vehicle");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(new EmptyBorder(0, 20, 0, 0));
        header.add(title, BorderLayout.WEST);

        JButton backBtn = new JButton("← Back");
        backBtn.setBackground(new Color(255, 87, 34));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.setBorder(new EmptyBorder(10, 20, 10, 20));
        header.add(backBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new CompoundBorder(
                new EmptyBorder(50, 100, 50, 100),
                new LineBorder(new Color(220, 220, 220), 1, true)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblVehicleNumber = new JLabel("Vehicle Number:");
        lblVehicleNumber.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(lblVehicleNumber, gbc);

        gbc.gridx = 1;
        vehicleNumberField = new JTextField(15);
        vehicleNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(vehicleNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblVehicleType = new JLabel("Vehicle Type:");
        lblVehicleType.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(lblVehicleType, gbc);

        gbc.gridx = 1;
        vehicleTypeBox = new JComboBox<>(new String[]{"2-Wheeler", "4-Wheeler"});
        vehicleTypeBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(vehicleTypeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton parkBtn = new JButton("Park Vehicle");
        parkBtn.setBackground(new Color(0, 200, 83));
        parkBtn.setForeground(Color.WHITE);
        parkBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        parkBtn.setFocusPainted(false);
        parkBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        parkBtn.setBorder(new EmptyBorder(10, 20, 10, 20));
        formPanel.add(parkBtn, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ---------- ACTIONS ----------
        backBtn.addActionListener(e -> mainFrame.showPanel("Dashboard"));

        parkBtn.addActionListener(e -> {
            String number = vehicleNumberField.getText().trim();
            String type = (String) vehicleTypeBox.getSelectedItem();

            if (number.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter vehicle number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            VehicleDAO dao = new VehicleDAO();

            // Check if vehicle already parked
            if (dao.isVehicleAlreadyParked(number)) {
                JOptionPane.showMessageDialog(this, "This vehicle is already parked!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = dao.parkVehicle(number, type);

            if (success) {
                JOptionPane.showMessageDialog(this, "Vehicle parked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                vehicleNumberField.setText("");
                mainFrame.showPanel("Dashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Error while parking vehicle!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
