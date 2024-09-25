package com.project.facebook.controllers;

import com.project.facebook.components.LocalizationUtils;
import com.project.facebook.dtos.PostDTO;
import com.project.facebook.models.Post;
import com.project.facebook.models.User;
import com.project.facebook.responses.ResponseObject;
import com.project.facebook.services.PostService;
import com.project.facebook.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/posts")
@RequiredArgsConstructor
public class PostController {
    private final LocalizationUtils localizationUtils;
    private final PostService postService;
    @PostMapping("")
    public ResponseEntity<ResponseObject> createPost(
            @Valid @RequestBody PostDTO postDTO,
            Authentication authentication,
            BindingResult result){
        try {
            User currentUser = (User) authentication.getPrincipal();
            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            if (!currentUser.getUserId().equals(postDTO.getAuthorId())) {
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN).build());
            }
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
            Post newPost = postService.createPost(postDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .data(newPost)
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.CREATE_POST_SUCCESSFULLY))
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }
}
