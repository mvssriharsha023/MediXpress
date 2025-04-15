package com.medixpress.controller;

import com.medixpress.dto.MedicineDTO;
import com.medixpress.model.Medicine;
import com.medixpress.security.JwtUtil;
import com.medixpress.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    public ResponseEntity<Medicine> addMedicine(@RequestHeader String token, @RequestBody MedicineDTO dto) {
        String pharmacyId = jwtUtil.extractId(token);
        dto.setPharmacyId(pharmacyId);
        return ResponseEntity.ok(medicineService.addOrUpdateMedicine(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable String id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.ok("Medicine deleted successfully");
    }

    @PutMapping("/reduce/{id}")
    public ResponseEntity<Optional<Medicine>> reduceMedicine(@PathVariable String id, @RequestBody Integer quantity) {
        Optional<Medicine> medicine = medicineService.reduceMedicineQuantity(id, quantity);
        return ResponseEntity.ok(medicine);
    }

    @GetMapping
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @GetMapping("/{pharmacyId}")
    public ResponseEntity<List<Medicine>> getMedicine(@PathVariable String pharmacyId) {
        return ResponseEntity.ok(medicineService.getAllMedicineByPharmacy(pharmacyId));
    }
}
