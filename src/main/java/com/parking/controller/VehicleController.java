package com.parking.controller;

import com.parking.entities.User;
import com.parking.entities.Vehicle;
import com.parking.entities.ParkingSlot;
import com.parking.exception.InvalidInputException;
import com.parking.exception.UserNotFoundException;
import com.parking.exception.VehicleAlreadyExistsException;
import com.parking.exception.VehicleNotFoundException;
import com.parking.repository.UserRepository;
import com.parking.repository.VehicleRepository;
import com.parking.repository.ParkingSlotRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ParkingSlotRepository parkingSlotRepository;

    public VehicleController(VehicleRepository vehicleRepository, UserRepository userRepository, ParkingSlotRepository parkingSlotRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.parkingSlotRepository = parkingSlotRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerVehicle(@RequestParam String contactNumber, @RequestBody Vehicle vehicle) {
        if (vehicle.getNumberPlate() == null || vehicle.getNumberPlate().isEmpty() || vehicle.getVehicleType() == null) {
            throw new InvalidInputException("Vehicle number plate must not be empty and vehicle type must be valid.");
        }

        User owner = userRepository.findByContactNumber(contactNumber);
        if (owner == null) {
            throw new UserNotFoundException("Owner not found with contact number: " + contactNumber);
        }

        long vehicleCount = vehicleRepository.countByOwnerId(owner.getId());
        if (vehicleCount >= 3) {
            return ResponseEntity.badRequest().body("User cannot park more than 5 vehicles. Please unregister a vehicle first.");
        }

        if (vehicleRepository.findByNumberPlate(vehicle.getNumberPlate()) != null) {
            throw new VehicleAlreadyExistsException("Vehicle already registered with this number plate!");
        }

        // Find an available parking slot
        ParkingSlot availableSlot = parkingSlotRepository.findAll()
                .stream()
                .filter(slot -> !slot.isOccupied())
                .findFirst()
                .orElse(null);

        if (availableSlot == null) {
            return ResponseEntity.badRequest().body("No available parking slot found.");
        }

        vehicle.setOwner(owner);
        vehicleRepository.save(vehicle);

        // Assign the vehicle to the parking slot
        availableSlot.setVehicle(vehicle);
        parkingSlotRepository.save(availableSlot);

        return ResponseEntity.ok("Vehicle registered and parked successfully in slot " + availableSlot.getSlotNumber());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVehicle(@PathVariable Long id, @RequestBody Vehicle updatedVehicle) {
        if (updatedVehicle.getNumberPlate() == null || updatedVehicle.getNumberPlate().isEmpty() || updatedVehicle.getVehicleType() == null) {
            throw new InvalidInputException("Vehicle number plate must not be empty and vehicle type must be valid.");
        }
        Optional<Vehicle> existingVehicle = vehicleRepository.findById(id);
        if (existingVehicle.isPresent()) {
            Vehicle vehicle = existingVehicle.get();
            vehicle.setNumberPlate(updatedVehicle.getNumberPlate());
            vehicle.setVehicleType(updatedVehicle.getVehicleType());
            vehicle.setOwner(updatedVehicle.getOwner());
            vehicleRepository.save(vehicle);
            return ResponseEntity.ok("Vehicle updated successfully!");
        }
        throw new VehicleNotFoundException("Vehicle not found with id: " + id);
    }

    @GetMapping("/all")
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
        Optional<Vehicle> existingVehicle = vehicleRepository.findById(id);
        if (existingVehicle.isPresent()) {
            Vehicle vehicle = existingVehicle.get();
            User owner = vehicle.getOwner();
            if (owner == null) {
                throw new UserNotFoundException("Owner not found for vehicle with id: " + id);
            }
            vehicleRepository.deleteById(id);
            return ResponseEntity.ok("Vehicle deleted successfully!");
        }
        throw new VehicleNotFoundException("Vehicle not found with id: " + id);
    }
}

