package com.parking.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parking_slots")
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private int slotNumber;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Getter
    @Column(nullable = false)
    private boolean occupied;

    public ParkingSlot() {}

    public ParkingSlot(int slotNumber, boolean occupied) {
        this.slotNumber = slotNumber;
        this.occupied = occupied;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.occupied = (vehicle != null);
    }

}

