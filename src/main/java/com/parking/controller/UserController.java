package com.parking.controller;

import com.parking.entities.User;
import com.parking.exception.InvalidInputException;
import com.parking.exception.UserAlreadyExistsException;
import com.parking.exception.UserNotFoundException;
import com.parking.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getContactNumber() == null || user.getContactNumber().isEmpty()) {
            throw new InvalidInputException("User name and contact number must not be empty.");
        }
        if (userRepository.findByContactNumber(user.getContactNumber()) != null) {
            throw new UserAlreadyExistsException("User already exists with this contact number!");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if (updatedUser.getName() == null || updatedUser.getName().isEmpty() || updatedUser.getContactNumber() == null || updatedUser.getContactNumber().isEmpty()) {
            throw new InvalidInputException("User name and contact number must not be empty.");
        }
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setContactNumber(updatedUser.getContactNumber());
            userRepository.save(user);
            return ResponseEntity.ok("User updated successfully!");
        }
        throw new UserNotFoundException("User not found with id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully!");
        }
        throw new UserNotFoundException("User not found with id: " + id);
    }

}