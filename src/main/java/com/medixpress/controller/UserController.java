package com.medixpress.controller;

import com.medixpress.dto.LoginRequest;
import com.medixpress.dto.LoginResponse;
import com.medixpress.dto.PharmacyDTO;
import com.medixpress.dto.UserDTO;
import com.medixpress.model.Pharmacy;
import com.medixpress.model.User;
import com.medixpress.security.JwtUtil;
import com.medixpress.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestHeader String token, @RequestBody UserDTO userDTO) {
        String id = jwtUtil.extractId(token);
        User updated = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updated);
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get")
    public ResponseEntity<User> getUserByIdForUser(@RequestHeader String token) {
        String pharmacyId = jwtUtil.extractId(token);
        User user = userService.getUserById(pharmacyId);
        return ResponseEntity.ok(user);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestHeader String token) {
        String id = jwtUtil.extractId(token);
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Deleted User successfully");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
