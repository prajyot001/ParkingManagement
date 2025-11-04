package com.parking;

import com.parking.panels.*;
import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Parking Management System");
        setSize(1200, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add all panels

        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new SignupPanel(this), "Signup");
        mainPanel.add(new DashboardPanel(this), "Dashboard");
        mainPanel.add(new ParkVehiclePanel(this), "ParkVehicle");
        mainPanel.add(new RemoveVehiclePanel(this), "RemoveVehicle");

        add(mainPanel);
        showPanel("Login");
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
}
