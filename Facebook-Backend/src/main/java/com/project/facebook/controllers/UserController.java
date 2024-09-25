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
                                .message(errorMessage)
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

    @PostMapping(value = "/upload_avatar/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar(
            @PathVariable("user_id") Long userId,
            @ModelAttribute("file") MultipartFile file
            ){
        try{
            User existingUser = userService.getUserById(userId);
            if(file.getSize() > 10 * 1024 * 1024){
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGE_FILE_LARGE));
            }
            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/")){
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGE_FILE_MUST_BE_IMAGE));
            }
            String fileName = storeFile(file, "uploads/user_images/avatars");
            User userAfterUpdate = userService.updateUserAvatar(userId, fileName);
            return ResponseEntity.ok(userAfterUpdate);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private String storeFile(MultipartFile file, String path) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        // kiem tra có null không và sau đó làm sạch dữ liệu
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get(path);
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích file.getInputStream() -> destination
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
