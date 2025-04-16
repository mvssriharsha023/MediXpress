package com.medixpress.service.impl;

import com.medixpress.dto.MedicineDTO;
import com.medixpress.exception.MedicineNotFoundException;
import com.medixpress.exception.OutOfStockException;
import com.medixpress.exception.PharmacyNotFoundException;
import com.medixpress.model.Medicine;
import com.medixpress.model.Pharmacy;
import com.medixpress.repository.MedicineRepository;
import com.medixpress.repository.PharmacyRepository;
import com.medixpress.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Override
    public Medicine addOrUpdateMedicine(MedicineDTO medicineDTO) {
        Pharmacy pharmacy = pharmacyRepository.findById(medicineDTO.getPharmacyId())
                .orElseThrow(() -> new PharmacyNotFoundException("Pharmacy not found"));

        // Check if the medicine already exists for this pharmacy
        Medicine existingMedicine = medicineRepository.findByNameAndPharmacyId(medicineDTO.getName(), medicineDTO.getPharmacyId());
        if (existingMedicine != null) {
            existingMedicine.setQuantity(existingMedicine.getQuantity() + medicineDTO.getQuantity());
            return medicineRepository.save(existingMedicine);
        }
        // Create a new medicine
        Medicine newMedicine = new Medicine();
        newMedicine.setName(medicineDTO.getName());
        newMedicine.setPrice(medicineDTO.getPrice());
        newMedicine.setQuantity(medicineDTO.getQuantity());
        newMedicine.setPharmacyId(medicineDTO.getPharmacyId());
        return medicineRepository.save(newMedicine);

    }

    @Override
    public void deleteMedicine(String medicineId) {
        medicineRepository.deleteById(medicineId);
    }

    @Override
    public Optional<Medicine> reduceMedicineQuantity(String medicineId, Integer quantity) {
        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(() -> new MedicineNotFoundException("Medicine not found"));
        int newQuantity = medicine.getQuantity() - quantity;
        if (newQuantity < 0) {
            throw new OutOfStockException("Not enough quantity to reduce");
        }
        if (newQuantity == 0) {
            return Optional.empty();
        }
        medicine.setQuantity(newQuantity);
        medicineRepository.save(medicine);
        return medicineRepository.findById(medicineId);
    }

    @Override
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    @Override
    public List<Medicine> getAllMedicineByPharmacy(String pharmacyId) {
        pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new PharmacyNotFoundException("Pharmacy not found"));
        return medicineRepository.findByPharmacyId(pharmacyId);
    }

    @Override
    public Medicine getMedicineById(String id) {
        return medicineRepository.findById(id).orElseThrow(() -> new MedicineNotFoundException("Medicine not found"));
    }

    @Override
    public int getAvailableQuantity(String pharmacyId, String medicineId) {
        List<Medicine> medicines = medicineRepository.findByIdAndPharmacyId(medicineId, pharmacyId);

        if (medicines.isEmpty()) {
            throw new MedicineNotFoundException("Medicine not found in this pharmacy");
        }

        return medicines.getFirst().getQuantity(); // or .getStock() if that's your field
    }

}
