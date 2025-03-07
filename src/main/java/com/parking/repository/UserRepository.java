package com.parking.repository;

import com.parking.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByContactNumber(String contactNumber);
}
