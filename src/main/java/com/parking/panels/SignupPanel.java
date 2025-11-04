package com.parking.panels;

import com.parking.MainFrame;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.parking.dao.UserDAO;

public class SignupPanel extends JPanel {

    private JTextField usernameField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton signupButton, backToLoginButton;
    private MainFrame mainFrame;

    public SignupPanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 244, 248));

        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(400, 450));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            new EmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        gbc.gridx = 0;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        JLabel title = new JLabel("Create New Account", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        card.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = y++;
        card.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        card.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = y++;
        card.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(15);
        card.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = y++;
        card.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        card.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = y++;
        card.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(15);
        card.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        signupButton = new JButton("Sign Up");
        signupButton.setBackground(new Color(40, 167, 69));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signupButton.setFocusPainted(false);
        card.add(signupButton, gbc);

        gbc.gridy = y++;
        backToLoginButton = new JButton("Already have an account? Login");
        backToLoginButton.setFocusPainted(false);
        backToLoginButton.setBorderPainted(false);
        backToLoginButton.setContentAreaFilled(false);
        backToLoginButton.setForeground(new Color(0, 123, 255));
        card.add(backToLoginButton, gbc);

        add(card);

        // Actions
        signupButton.addActionListener(e -> registerUser());
        backToLoginButton.addActionListener(e -> mainFrame.showPanel("Login"));
    }

    private void registerUser() {
    String username = usernameField.getText();
    String email = emailField.getText();
    String password = new String(passwordField.getPassword());
    String confirm = new String(confirmPasswordField.getPassword());

    if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (!password.equals(confirm)) {
        JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    UserDAO dao = new UserDAO();
    if (dao.isUserExists(username, email)) {
        JOptionPane.showMessageDialog(this, "Username or Email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    boolean success = dao.registerUser(username, email, password);
    if (success) {
        JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        mainFrame.showPanel("Login");
    } else {
        JOptionPane.showMessageDialog(this, "Error creating account!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
