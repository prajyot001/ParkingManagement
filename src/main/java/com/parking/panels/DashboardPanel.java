package com.parking.panels;

import com.parking.MainFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class DashboardPanel extends JPanel {

    private MainFrame mainFrame;

    public DashboardPanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 248, 252));

        // ---------- HEADER ----------
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(33, 150, 243));
        header.setPreferredSize(new Dimension(0, 70));

        JLabel title = new JLabel("🚗 Parking Management Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(new EmptyBorder(0, 20, 0, 0));
        header.add(title, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(255, 77, 77));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.setBorder(new EmptyBorder(10, 20, 10, 20));
        header.add(logoutBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ---------- CENTER AREA ----------
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(245, 248, 252));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);

        // Buttons setup
        JButton parkVehicleBtn = createCardButton("Park Vehicle", new Color(0, 200, 83));
        JButton removeVehicleBtn = createCardButton("Remove Vehicle", new Color(255, 193, 7));
        JButton reportBtn = createCardButton("Reports", new Color(3, 169, 244));

        gbc.gridx = 0;
        gbc.gridy = 0;
        center.add(parkVehicleBtn, gbc);

        gbc.gridx = 1;
        center.add(removeVehicleBtn, gbc);

        gbc.gridx = 2;
        center.add(reportBtn, gbc);

        add(center, BorderLayout.CENTER);

        // ---------- BUTTON ACTIONS ----------
        logoutBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Logout from Dashboard?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                mainFrame.showPanel("Login");
            }
        });

        parkVehicleBtn.addActionListener(e -> mainFrame.showPanel("ParkVehicle"));
        removeVehicleBtn.addActionListener(e -> mainFrame.showPanel("RemoveVehicle"));
        reportBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Reports feature coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE));
    }

    private JButton createCardButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(200, 150));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBorder(new LineBorder(new Color(230, 230, 230), 2, true));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // hover animation
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }
}
