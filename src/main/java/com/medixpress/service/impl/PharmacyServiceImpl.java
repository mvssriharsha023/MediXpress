package com.medixpress.service.impl;

import com.medixpress.dto.LoginRequest;
import com.medixpress.dto.LoginResponse;
import com.medixpress.dto.PharmacyDTO;
import com.medixpress.exception.InvalidAddressException;
import com.medixpress.exception.InvalidCredentialsException;
import com.medixpress.exception.PharmacyAlreadyExistsException;
import com.medixpress.exception.PharmacyNotFoundException;
import com.medixpress.model.Pharmacy;
import com.medixpress.repository.PharmacyRepository;
import com.medixpress.security.JwtUtil;
import com.medixpress.service.GeoLocationService;
import com.medixpress.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final GeoLocationService geoLocationService;
    @Autowired
    private final JwtUtil jwtUtil;

    @Override
    public Pharmacy createPharmacy(PharmacyDTO pharmacyDto) {

        Optional<Pharmacy> opPharmacyByEmail = pharmacyRepository.findByEmail(pharmacyDto.getEmail());
        Optional<Pharmacy> opPharmacyByContactNumber = pharmacyRepository.findByContactNumber(pharmacyDto.getContactNumber());

        if (opPharmacyByEmail.isPresent() || opPharmacyByContactNumber.isPresent()) {
            throw new PharmacyAlreadyExistsException("Pharmacy with this email id or password already exists");
        }

        double[] latLong = geoLocationService
                .getLatLongFromAddress(pharmacyDto.getAddress())
                .orElseThrow(() -> new InvalidAddressException("Invalid address"));


        System.out.println(latLong[0] + " " + latLong[1]);

        Pharmacy pharmacy = Pharmacy.builder()
                .name(pharmacyDto.getName())
                .address(pharmacyDto.getAddress())
                .password(new BCryptPasswordEncoder().encode(pharmacyDto.getPassword()))
                .contactNumber(pharmacyDto.getContactNumber())
                .email(pharmacyDto.getEmail())
                .latitude(latLong[0])
                .longitude(latLong[1])
                .build();

        System.out.println(pharmacy);
        return pharmacyRepository.save(pharmacy);
    }

    @Override
    public List<Pharmacy> getAllPharmacies() {
        return pharmacyRepository.findAll();
    }

    @Override
    public Pharmacy getPharmacyById(String id) {
        return pharmacyRepository.findById(id)
                .orElseThrow(() -> new PharmacyNotFoundException("Pharmacy not found with id: " + id));
    }

    @Override
    public Pharmacy updatePharmacy(String id, PharmacyDTO dto) {

        Optional<Pharmacy> opPharmacyByEmail = pharmacyRepository.findByEmail(dto.getEmail());
        Optional<Pharmacy> opPharmacyByContactNumber = pharmacyRepository.findByContactNumber(dto.getContactNumber());

        if (opPharmacyByEmail.isPresent() || opPharmacyByContactNumber.isPresent()) {
            throw new PharmacyAlreadyExistsException("Pharmacy with this email id or phone number already exists");
        }

        double[] latLong = geoLocationService
                .getLatLongFromAddress(dto.getAddress())
                .orElseThrow(() -> new InvalidAddressException("Invalid address"));


        Pharmacy existing = getPharmacyById(id);
        existing.setName(dto.getName());
        existing.setAddress(dto.getAddress());
        existing.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        existing.setContactNumber(dto.getContactNumber());
        existing.setEmail(dto.getEmail());
        existing.setLatitude(latLong[0]);
        existing.setLongitude(latLong[1]);
        return pharmacyRepository.save(existing);
    }

    @Override
    public void deletePharmacy(String id) {
        pharmacyRepository.deleteById(id);
    }

    @Override
    public LoginResponse loginPharmacy(LoginRequest loginRequest) {
        Pharmacy pharmacy = pharmacyRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginRequest.getPassword(), pharmacy.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(pharmacy.getId());

        return new LoginResponse(
                token,
                "Login successful",
                "pharmacy"
        );
    }
}
