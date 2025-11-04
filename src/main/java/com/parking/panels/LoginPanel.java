package com.parking.panels;

import com.parking.MainFrame;
import com.parking.dao.UserDAO;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class LoginPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private MainFrame mainFrame;

    public LoginPanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 244, 248)); // soft background

        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(400, 380));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(30, 40, 30, 40)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel title = new JLabel("Parking Management Login", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(60, 63, 65));
        gbc.gridwidth = 2;
        card.add(title, gbc);

        // --- Username ---
        gbc.gridy++;
        gbc.gridwidth = 1;
        card.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        card.add(usernameField, gbc);

        // --- Password ---
        gbc.gridx = 0;
        gbc.gridy++;
        card.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        card.add(passwordField, gbc);

        // --- Login Button ---
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        card.add(loginButton, gbc);

        // --- Sign Up Link ---
        gbc.gridy++;
        JButton signupButton = new JButton("Create new account");
        signupButton.setFocusPainted(false);
        signupButton.setBorderPainted(false);
        signupButton.setContentAreaFilled(false);
        signupButton.setForeground(new Color(0, 123, 255));
        signupButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        card.add(signupButton, gbc);

        // Switch to signup page
        signupButton.addActionListener(e -> mainFrame.showPanel("Signup"));

        // add card to center
        add(card);

        // --- Login Action ---
        loginButton.addActionListener(e -> authenticate());
        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    authenticate();
                }
            }
        });
    }

    private void authenticate() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO dao = new UserDAO();
        boolean success = dao.authenticate(username, password);

        if (success) {
            JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.showPanel("Dashboard");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
