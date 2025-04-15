package com.medixpress.repository;

import com.medixpress.model.Pharmacy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PharmacyRepository extends MongoRepository<Pharmacy, String> {
    Optional<Pharmacy> findByEmail(String email);
    Optional<Pharmacy> findByContactNumber(String contactNumber);
}
