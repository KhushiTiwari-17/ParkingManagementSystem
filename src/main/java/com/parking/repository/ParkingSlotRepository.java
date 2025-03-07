package com.parking.repository;

import com.parking.entities.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    ParkingSlot findBySlotNumber(int slotNumber);
}