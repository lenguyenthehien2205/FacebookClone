package com.project.facebook.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.project.facebook.dtos.UserCreateDTO;
import com.project.facebook.dtos.UserLoginDTO;
import com.project.facebook.responses.ResponseObject;
import com.project.facebook.responses.user.LoginResponse;
import com.project.facebook.responses.user.UserResponse;
import com.project.facebook.components.LocalizationUtils;
import com.project.facebook.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.project.facebook.models.User;
import com.project.facebook.services.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${api.base-path}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final LocalizationUtils localizationUtils;

    // ok
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(UserResponse::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ResponseObject.builder()
                        .data(users)
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.GET_USER_LIST_SUCCESSFULLY))
                        .status(HttpStatus.OK)
                .build());
    }
    // ok
    @PostMapping("/register")
    public ResponseEntity<ResponseObject> createUser(
            @Valid @RequestBody UserCreateDTO userCreateDTO,
            BindingResult result
    ) {
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(error -> localizationUtils.getLocalizedMessage(error.getDefaultMessage()))
                        .toList();
                String errorMessage = String.join(", ", errorMessages);
                return ResponseEntity.badRequest().body(ResponseObject.builder()
                                .data(null)
                                .status(HttpStatus.BAD_REQUEST)
                                .message(errorMessage)
                        .build());
            }
            User newUser = userService.createUser(userCreateDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.CREATED)
                            .data(UserResponse.fromUser(newUser))
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY))
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(e.getMessage())
                    .build());
        }
    }
    // ok
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            HttpServletRequest request
            ){
        try{
            String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
            LoginResponse loginResponse = LoginResponse
                    .builder()
                    .token(token)
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                    .build();
            return ResponseEntity.ok(ResponseObject.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                            .data(loginResponse)
                            .status(HttpStatus.OK)
                            .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .data(null)
                            .status(HttpStatus.BAD_REQUEST)
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED))
                            .build());
        }
    }
    // ok
    @GetMapping("/{phone}")
    public ResponseEntity<ResponseObject> getUserByPhoneNumber(
            @PathVariable("phone") String phoneNumber
    ) {
        try{
            Optional<User> user = userService.getUserByPhoneNumber(phoneNumber);
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .data(user)
                            .status(HttpStatus.OK)
                            .message("Get user successfully")
                            .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .data(null)
                            .status(HttpStatus.BAD_REQUEST)
                            .message(e.getMessage())
                            .build());
        }
    }

}
