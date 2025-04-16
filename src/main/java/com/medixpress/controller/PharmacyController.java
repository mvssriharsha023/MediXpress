package com.medixpress.controller;

import com.medixpress.dto.LoginRequest;
import com.medixpress.dto.LoginResponse;
import com.medixpress.dto.PharmacyDTO;
import com.medixpress.model.Pharmacy;
import com.medixpress.security.JwtUtil;
import com.medixpress.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;
    @Autowired
    private JwtUtil jwtUtil;

    // Create
    @PostMapping
    public ResponseEntity<Pharmacy> createPharmacy(@RequestBody PharmacyDTO pharmacyDTO) {
        Pharmacy created = pharmacyService.createPharmacy(pharmacyDTO);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse pharmacy = pharmacyService.loginPharmacy(request);
        return ResponseEntity.ok(pharmacy);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Pharmacy>> getAllPharmacies() {
        List<Pharmacy> pharmacies = pharmacyService.getAllPharmacies();
        return ResponseEntity.ok(pharmacies);
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Pharmacy> getPharmacyById(@PathVariable String id) {
        Pharmacy pharmacy = pharmacyService.getPharmacyById(id);
        return ResponseEntity.ok(pharmacy);
    }

    @GetMapping("/get")
    public ResponseEntity<Pharmacy> getPharmacyByIdForPharmacy(@RequestHeader String token) {
        String pharmacyId = jwtUtil.extractId(token);
        Pharmacy pharmacy = pharmacyService.getPharmacyById(pharmacyId);
        return ResponseEntity.ok(pharmacy);
    }

    // Update
    @PutMapping
    public ResponseEntity<Pharmacy> updatePharmacy(@RequestHeader String token, @RequestBody PharmacyDTO pharmacyDTO) {
        String id = jwtUtil.extractId(token);
        Pharmacy updated = pharmacyService.updatePharmacy(id, pharmacyDTO);
        return ResponseEntity.ok(updated);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<String> deletePharmacy(@RequestHeader String token) {
        String id = jwtUtil.extractId(token);
        pharmacyService.deletePharmacy(id);
        return ResponseEntity.ok("Pharmacy Account deleted Successfully");
    }
}
