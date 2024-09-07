package com.project.facebook.services;

import java.util.List;

import com.project.facebook.exceptions.DataNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.project.facebook.dtos.UserDTO;
import com.project.facebook.exceptions.PermissionDenyException;
import com.project.facebook.models.Role;
import com.project.facebook.models.User;
import com.project.facebook.repositories.RoleRepository;
import com.project.facebook.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Created At: " + user.getCreatedAt());
            System.out.println("Updated At: " + user.getUpdatedAt());
        }
        return users;
    }

    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
            .orElseThrow(() -> new DataNotFoundException("Role not found"));
        if(role.getRoleName().equals("ADMIN")){
            throw new PermissionDenyException("Permission denied");
        }
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .avatar(userDTO.getAvatar())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();
        user.setRole(role);
        return userRepository.save(user);
    }

}
