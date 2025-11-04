package com.parking.model;

import java.time.LocalDateTime;

public class ParkingSlot {
    private String vehicleNo;
    private String vehicleType;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double billAmount;

    public ParkingSlot(String vehicleNo, String vehicleType, LocalDateTime entryTime) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.entryTime = entryTime;
    }

    // Getters & setters
    public String getVehicleNo() { return vehicleNo; }
    public String getVehicleType() { return vehicleType; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }
    public double getBillAmount() { return billAmount; }
    public void setBillAmount(double billAmount) { this.billAmount = billAmount; }
}
