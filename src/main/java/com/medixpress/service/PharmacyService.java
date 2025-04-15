package com.medixpress.service;

import com.medixpress.dto.LoginRequest;
import com.medixpress.dto.LoginResponse;
import com.medixpress.dto.PharmacyDTO;
import com.medixpress.model.Pharmacy;

import java.util.List;

public interface PharmacyService {
    Pharmacy createPharmacy(PharmacyDTO pharmacyDTO);
    List<Pharmacy> getAllPharmacies();
    Pharmacy getPharmacyById(String id);
    Pharmacy updatePharmacy(String id, PharmacyDTO pharmacyDTO);
    void deletePharmacy(String id);
    LoginResponse loginPharmacy(LoginRequest loginRequest);
}
