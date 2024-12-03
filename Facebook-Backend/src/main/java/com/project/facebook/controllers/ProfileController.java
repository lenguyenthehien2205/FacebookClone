package com.project.facebook.controllers;

import com.project.facebook.components.FileUtils;
import com.project.facebook.components.LocalizationUtils;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;
import com.project.facebook.models.User;
import com.project.facebook.responses.ResponseObject;
import com.project.facebook.responses.user.UserResponse;
import com.project.facebook.services.IProfileService;
import com.project.facebook.services.ProfileService;
import com.project.facebook.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.base-path}/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final IProfileService profileService;
    private final LocalizationUtils localizationUtils;
    private final FileUtils fileUtils;

    // ok
    @PostMapping(value = "/upload_avatar/{profile_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> uploadProfileAvatar(
            @PathVariable("profile_id") Long profileId,
            @ModelAttribute("file") MultipartFile file,
            Authentication authentication
    ){
        try{
            User currentUser = (User) authentication.getPrincipal();
            Profile currentProfile = profileService.getProfileById(profileId);
            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            if (!currentUser.getUserId().equals(currentProfile.getUser().getUserId())) {
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN).build());
            }
            Profile existingProfile = profileService.getProfileById(profileId);
            if(file.getSize() > 10 * 1024 * 1024){
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGE_FILE_LARGE))
                        .build());
            }
            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/")){
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGE_FILE_MUST_BE_IMAGE))
                        .build());
            }
            String fileName = fileUtils.storeFile(file, "uploads/user_images/avatars/profiles/avatar");
            PageBase pageBase = profileService.updateProfileAvatar(profileId, fileName);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .data(pageBase)
                    .message("Upload avatar successfully")
                    .build());
        }catch (Exception e){
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }
    // ok
    @PostMapping(value = "/upload_cover_photo/{profile_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> uploadProfileCoverPhoto(
            @PathVariable("profile_id") Long profileId,
            @ModelAttribute("file") MultipartFile file,
            Authentication authentication
    ){
        try{
            User currentUser = (User) authentication.getPrincipal();
            Profile currentProfile = profileService.getProfileById(profileId);
            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            if (!currentUser.getUserId().equals(currentProfile.getUser().getUserId())) {
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN).build());
            }
            Profile existingProfile = profileService.getProfileById(profileId);
            if(file.getSize() > 10 * 1024 * 1024){
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGE_FILE_LARGE))
                        .build());
            }
            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/")){
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGE_FILE_MUST_BE_IMAGE))
                        .build());
            }
            String fileName = fileUtils.storeFile(file, "uploads/user_images/cover_photos/profiles/cover_photo");
            PageBase pageBase = profileService.updateProfileCoverPhoto(profileId, fileName);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .data(pageBase)
                    .message("Upload cover photo successfully")
                    .build());
        }catch (Exception e){
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }
    // ok
    @GetMapping("avatar_image/{image_name}")
    public ResponseEntity<?> viewAvatarImage(@PathVariable("image_name") String imageName){
        try {
            Path imagePath = Paths.get("uploads/user_images/avatars/profiles/avatar/"+imageName);
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
    // ok
    @GetMapping("cover_photo_image/{image_name}")
    public ResponseEntity<?> viewCoverPhotoImage(@PathVariable("image_name") String imageName){
        try {
            Path imagePath = Paths.get("uploads/user_images/cover_photos/profiles/cover_photo/"+imageName);
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

}
