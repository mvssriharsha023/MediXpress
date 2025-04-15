package com.medixpress.service;

import com.medixpress.dto.LoginRequest;
import com.medixpress.dto.LoginResponse;
import com.medixpress.dto.UserDTO;
import com.medixpress.model.User;

import java.util.List;

public interface UserService {
    User registerUser(UserDTO userDTO);
    LoginResponse loginUser(LoginRequest loginRequest);
    List<User> getAllUsers();
    User getUserById(String id);
    User updateUser(String id, UserDTO userDTO);
    void deleteUser(String id);
}
