package com.parking.repository;

import com.parking.entities.Vehicle;
import com.parking.entities.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

//    @Query("SELECT v FROM Vehicle v WHERE v.vehicleType = :type")
//    List<Vehicle> findByVehicleType(@Param("type") VehicleType type);

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.owner.id = :ownerId")
    long countByOwnerId(@Param("ownerId") Long ownerId);

    Vehicle findByNumberPlate(String numberPlate);
}
