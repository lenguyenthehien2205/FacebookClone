package com.project.facebook.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.project.facebook.dtos.UserDTO;
import com.project.facebook.dtos.UserLoginDTO;
import com.project.facebook.responses.LoginResponse;
import com.project.facebook.responses.RegisterResponse;
import com.project.facebook.responses.UserResponse;
import com.project.facebook.components.LocalizationUtils;
import com.project.facebook.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
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
    private final LocalizationUtils localizationUtils;
    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(UserResponse::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(error -> localizationUtils.getLocalizedMessage(error.getDefaultMessage()))
                        .toList();
                String errorMessage = String.join(", ", errorMessages);
                registerResponse.setMessage(errorMessage);
                return ResponseEntity.badRequest().body(registerResponse);
            }
            User newUser = userService.createUser(userDTO);
            registerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY));
            registerResponse.setUser(newUser);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e){
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            HttpServletRequest request
            ){
        try{
            String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
            return ResponseEntity.ok(LoginResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                            .token(token)
                    .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED, e.getMessage()))
                            .build());
        }
    }
    @GetMapping("/{phone}")
    public ResponseEntity<?> getUserByPhoneNumber(
            @PathVariable("phone") String phoneNumber
    ) {
        try{
            Optional<User> user = userService.getUserByPhoneNumber(phoneNumber);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
