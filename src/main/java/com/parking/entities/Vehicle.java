package com.parking.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numberPlate;

    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = VehicleTypeDeserializer.class)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

}

