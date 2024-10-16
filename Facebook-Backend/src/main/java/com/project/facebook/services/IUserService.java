package com.project.facebook.services;

import com.project.facebook.dtos.UserCreateDTO;
import com.project.facebook.dtos.UserDTO;
import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.Profile;
import com.project.facebook.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();
    Optional<User> getUserByPhoneNumber(String phoneNumber) throws DataNotFoundException;
    User getUserById(Long id) throws DataNotFoundException;
    User createUser(UserCreateDTO userCreateDTO) throws Exception;
    String login(String phoneNumber, String password) throws Exception;
    User updateUser(Long userId, UserDTO userDTO) throws Exception;
}
