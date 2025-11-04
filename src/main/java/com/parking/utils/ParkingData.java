package com.parking.utils;

import com.parking.model.ParkingSlot;
import java.util.*;

public class ParkingData {
    public static List<ParkingSlot> parkedVehicles = new ArrayList<>();

    public static void parkVehicle(ParkingSlot slot) {
        parkedVehicles.add(slot);
    }

    public static ParkingSlot removeVehicle(String vehicleNo) {
        for (ParkingSlot slot : parkedVehicles) {
            if (slot.getVehicleNo().equalsIgnoreCase(vehicleNo)) {
                parkedVehicles.remove(slot);
                return slot;
            }
        }
        return null;
    }

    public static List<ParkingSlot> getAll() {
        return parkedVehicles;
    }
}
