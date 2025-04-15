package com.medixpress.repository;

import com.medixpress.model.Medicine;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MedicineRepository extends MongoRepository<Medicine, String> {

    Medicine findByNameAndPharmacyId(String name, String pharmacyId);

    List<Medicine> findByPharmacyId(String pharmacyId);

    List<Medicine> findByIdAndPharmacyId(String id, String pharmacyId);
    
}
