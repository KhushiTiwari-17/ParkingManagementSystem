package com.parking.controller;

import com.parking.entities.ParkingSlot;
import com.parking.exception.InvalidInputException;
import com.parking.exception.SlotNotFoundException;
import com.parking.exception.SlotAlreadyExistsException;
import com.parking.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parking")
public class ParkingController {
    private final ParkingSlotRepository parkingSlotRepository;

    @Value("${parking.max-slots}")
    private int maxSlots;

    public ParkingController(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    @PostMapping("/add-slot")
    public ResponseEntity<String> addSlot(@RequestParam int slotNumber) {
        if (slotNumber <= 0) {
            throw new InvalidInputException("Slot number must be positive.");
        }
        if (parkingSlotRepository.findBySlotNumber(slotNumber) != null) {
            throw new SlotAlreadyExistsException("Slot already exists with number: " + slotNumber);
        }
        long currentSlotCount = parkingSlotRepository.count();
        if (currentSlotCount >= maxSlots) {
            throw new InvalidInputException("Maximum number of slots reached. Cannot add more slots.");
        }
        ParkingSlot slot = new ParkingSlot(slotNumber, false);
        parkingSlotRepository.save(slot);
        return ResponseEntity.ok("Slot added successfully!");
    }

    @PutMapping("/update-slot/{id}")
    public ResponseEntity<String> updateSlot(@PathVariable Long id, @RequestParam int slotNumber) {
        if (slotNumber <= 0) {
            throw new InvalidInputException("Slot number must be positive.");
        }
        Optional<ParkingSlot> existingSlot = parkingSlotRepository.findById(id);
        if (existingSlot.isPresent()) {
            ParkingSlot slot = existingSlot.get();
            slot.setSlotNumber(slotNumber);
            parkingSlotRepository.save(slot);
            return ResponseEntity.ok("Slot updated successfully!");
        }
        throw new SlotNotFoundException("Parking slot not found with id: " + id);
    }

    @GetMapping("/slots")
    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSlot(@PathVariable Long id) {
        Optional<ParkingSlot> existingSlot = parkingSlotRepository.findById(id);
        if (existingSlot.isPresent()) {
            parkingSlotRepository.deleteById(id);
            return ResponseEntity.ok("Slot deleted successfully!");
        }
        throw new SlotNotFoundException("Parking slot not found with id: " + id);
    }
}