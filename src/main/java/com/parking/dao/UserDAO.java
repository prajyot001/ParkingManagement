package com.parking.dao;

import com.parking.utils.DBConnection;
import java.sql.*;

public class UserDAO {

    // --- Register User ---
    public boolean registerUser(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password); // Later: hash before storing

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Duplicate username/email!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Check if username or email exists ---
    public boolean isUserExists(String username, String email) {
        String query = "SELECT COUNT(*) FROM users WHERE username=? OR email=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- Verify Login ---
    public boolean authenticate(String username, String password) {
        String query = "SELECT password_hash FROM users WHERE username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password_hash");
                return password.equals(storedPassword); // Later: use hashing
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
