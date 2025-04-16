package com.medixpress.service.impl;

import com.medixpress.dto.LoginRequest;
import com.medixpress.dto.LoginResponse;
import com.medixpress.dto.UserDTO;
import com.medixpress.exception.InvalidAddressException;
import com.medixpress.exception.InvalidCredentialsException;
import com.medixpress.exception.UserAlreadyExistsException;
import com.medixpress.exception.UserNotExistException;
import com.medixpress.model.User;
import com.medixpress.repository.UserRepository;
import com.medixpress.security.JwtUtil;
import com.medixpress.service.GeoLocationService;
import com.medixpress.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GeoLocationService geoLocationService;
    @Autowired
    private final JwtUtil jwtUtil;

    @Override
    public User registerUser(UserDTO userDTO) {

        Optional<User> opUserByEmail = userRepository.findByEmail(userDTO.getEmail());
        Optional<User> opUserByContactNumber = userRepository.findByContactNumber(userDTO.getContactNumber());

        if (opUserByEmail.isPresent() || opUserByContactNumber.isPresent()) {
            throw new UserAlreadyExistsException("User with this email id or phone number already exists");
        }
        double[] latLong = geoLocationService
                .getLatLongFromAddress(userDTO.getAddress())
                .orElseThrow(() -> new InvalidAddressException("Invalid address"));


        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(new BCryptPasswordEncoder().encode(userDTO.getPassword()))
                .contactNumber(userDTO.getContactNumber())
                .address(userDTO.getAddress())
                .latitude(latLong[0])
                .longitude(latLong[1])
                .build();

        return userRepository.save(user);
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getId());

        return new LoginResponse(
                token,
                "Login successful",
                "user"
        );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotExistException("User not found"));
    }

    @Override
    public User updateUser(String id, UserDTO dto) {

        Optional<User> opUserByEmail = userRepository.findByEmail(dto.getEmail());
        Optional<User> opUserByContactNumber = userRepository.findByContactNumber(dto.getContactNumber());

        if (opUserByEmail.isPresent() || opUserByContactNumber.isPresent()) {
            throw new UserAlreadyExistsException("User with this email id or phone number already exists");
        }

        double[] latLong = geoLocationService
                .getLatLongFromAddress(dto.getAddress())
                .orElseThrow(() -> new InvalidAddressException("Invalid address"));


        User existing = getUserById(id);
        existing.setName(dto.getName());
        existing.setAddress(dto.getAddress());
        existing.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        existing.setContactNumber(dto.getContactNumber());
        existing.setEmail(dto.getEmail());
        existing.setLatitude(latLong[0]);
        existing.setLongitude(latLong[1]);
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }


}
