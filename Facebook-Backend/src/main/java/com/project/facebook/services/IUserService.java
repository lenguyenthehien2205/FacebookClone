package com.project.facebook.services;

import com.project.facebook.dtos.UserDTO;
import com.project.facebook.models.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    User createUser(UserDTO userDTO) throws Exception;
}
