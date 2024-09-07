package com.project.facebook.controllers;

import java.util.List;

import com.project.facebook.dtos.UserDTO;
import com.project.facebook.responses.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.project.facebook.models.User;
import com.project.facebook.services.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.base-path}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            User newUser = userService.createUser(userDTO);
            return ResponseEntity.ok(UserResponse.fromUser(newUser));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
