package com.medixpress.service;

import com.medixpress.dto.MedicineDTO;
import com.medixpress.model.Medicine;

import java.util.List;
import java.util.Optional;

public interface MedicineService {

    Medicine addOrUpdateMedicine(MedicineDTO medicineDTO);

    void deleteMedicine(String medicineId);

    Optional<Medicine> reduceMedicineQuantity(String medicineId, Integer quantity);

    List<Medicine> getAllMedicines();

    List<Medicine> getAllMedicineByPharmacy(String pharmacyId);

    Medicine getMedicineById(String id);

    int getAvailableQuantity(String medicineId, String pharmacyId);
}
