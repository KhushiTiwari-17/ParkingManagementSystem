package com.parking.service;

import com.parking.entities.Vehicle;
import com.parking.entities.ParkingSlot;
import com.parking.repository.ParkingSlotRepository;
import com.parking.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingSlotRepository parkingSlotRepository;
    private final VehicleRepository vehicleRepository;

    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    public String parkVehicle(Vehicle vehicle, int slotNumber) {
        ParkingSlot slot = parkingSlotRepository.findBySlotNumber(slotNumber);
        if (slot != null && !slot.isOccupied()) {
            slot.setVehicle(vehicle);
            vehicleRepository.save(vehicle);
            parkingSlotRepository.save(slot);
            return "Vehicle parked successfully!";
        } else {
            return "Slot is occupied or does not exist.";
        }
    }
}