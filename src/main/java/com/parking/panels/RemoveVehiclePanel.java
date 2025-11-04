package com.parking.panels;

import com.parking.utils.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class RemoveVehiclePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JButton btnRemove, btnRefresh;

    public RemoveVehiclePanel(JFrame parent) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Remove Vehicle", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Table model
        model = new DefaultTableModel(new String[]{"ID", "Vehicle Number", "Type", "Entry Time", "Status"}, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Button panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        btnRefresh = new JButton("Refresh");
        btnRemove = new JButton("Remove Vehicle");

        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnRemove);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load data
        loadParkedVehicles();

        // Actions
        btnRefresh.addActionListener(e -> loadParkedVehicles());
        btnRemove.addActionListener(e -> openRemovePopup());
    }

    private void loadParkedVehicles() {
        model.setRowCount(0);
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM vehicles WHERE status='Parked'")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_type"),
                        rs.getTimestamp("entry_time"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading vehicles: " + e.getMessage());
        }
    }

    private void openRemovePopup() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to remove.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String number = model.getValueAt(row, 1).toString();
        String type = model.getValueAt(row, 2).toString();
        Timestamp entry = (Timestamp) model.getValueAt(row, 3);

        // Calculate duration
        long durationMinutes = (System.currentTimeMillis() - entry.getTime()) / (1000 * 60);
        long hours = durationMinutes / 60;
        long mins = durationMinutes % 60;

        // Custom dialog
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Vehicle Number: " + number));
        panel.add(new JLabel("Vehicle Type: " + type));
        panel.add(new JLabel("Entry Time: " + entry));
        panel.add(new JLabel("Duration: " + hours + " hrs " + mins + " mins"));
        panel.add(new JLabel("Exit Time will be current system time."));

        int confirm = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Confirm Vehicle Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            removeVehicleFromDB(id, number);
        }
    }

    private void removeVehicleFromDB(int id, String number) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE vehicles SET exit_time = NOW(), status = 'Exited' WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int updated = ps.executeUpdate();

            if (updated > 0) {
                JOptionPane.showMessageDialog(this,
                        "✅ Vehicle " + number + " removed successfully.\nExit time recorded: current time.");
                loadParkedVehicles();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Vehicle not found or already exited.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error removing vehicle: " + e.getMessage());
        }
    }
}
