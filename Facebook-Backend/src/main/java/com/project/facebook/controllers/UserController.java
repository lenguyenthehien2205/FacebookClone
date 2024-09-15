package com.project.facebook.controllers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.project.facebook.dtos.UserDTO;
import com.project.facebook.dtos.UserLoginDTO;
import com.project.facebook.responses.ResponseObject;
import com.project.facebook.responses.user.LoginResponse;
import com.project.facebook.responses.user.RegisterResponse;
import com.project.facebook.responses.user.UserResponse;
import com.project.facebook.components.LocalizationUtils;
import com.project.facebook.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @GetMapping("images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName){
        try {
            Path imagePath = Paths.get("uploads/user_images/avatars/"+imageName);
            // dung de truy cap tep hinh anh
            UrlResource resource = new UrlResource(imagePath.toUri());
            if(resource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

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

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> createUser(
            @Valid @RequestBody UserDTO userDTO,
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
                                .message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_FAILED))
                        .build());
            }
            User newUser = userService.createUser(userDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.CREATED)
                            .data(UserResponse.fromUser(newUser))
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY))
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_FAILED))
                    .build());
        }
    }

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
