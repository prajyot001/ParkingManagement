package com.parking.dao;

import java.sql.*;
import java.util.*;
import com.parking.utils.DBConnection;

public class VehicleDAO {

    public boolean parkVehicle(String vehicleNumber, String vehicleType) {
        String sql = "INSERT INTO vehicles (vehicle_number, vehicle_type) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicleNumber);
            ps.setString(2, vehicleType);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isVehicleAlreadyParked(String vehicleNumber) {
        String sql = "SELECT * FROM vehicles WHERE vehicle_number = ? AND status = 'Parked'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicleNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Fetch all currently parked vehicles
    public List<Map<String, String>> getParkedVehicles() {
        List<Map<String, String>> list = new ArrayList<>();
        String sql = "SELECT vehicle_number, vehicle_type, DATE_FORMAT(entry_time, '%d-%m-%Y %H:%i:%s') AS entry_time " +
                     "FROM vehicles WHERE status = 'Parked' ORDER BY entry_time DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("number", rs.getString("vehicle_number"));
                map.put("type", rs.getString("vehicle_type"));
                map.put("entry", rs.getString("entry_time"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Remove vehicle (set exit time and update status)
    public boolean removeVehicle(String vehicleNumber) {
        String sql = "UPDATE vehicles SET status = 'Exited', exit_time = NOW() WHERE vehicle_number = ? AND status = 'Parked'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicleNumber);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
