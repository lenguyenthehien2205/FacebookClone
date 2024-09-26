package com.project.facebook.controllers;

import com.project.facebook.components.LocalizationUtils;
import com.project.facebook.dtos.MediaDTO;
import com.project.facebook.dtos.PostDTO;
import com.project.facebook.models.Media;
import com.project.facebook.models.Post;
import com.project.facebook.models.User;
import com.project.facebook.responses.ResponseObject;
import com.project.facebook.responses.post.PostResponse;
import com.project.facebook.services.PostService;
import com.project.facebook.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.base-path}/posts")
@RequiredArgsConstructor
public class PostController {
    private final LocalizationUtils localizationUtils;
    private final PostService postService;
    @PostMapping("")
    public ResponseEntity<ResponseObject> createPost(
            @Valid @RequestBody PostDTO postDTO,
            Authentication authentication){
        try {
            User currentUser = (User) authentication.getPrincipal();
            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            if (!currentUser.getUserId().equals(postDTO.getAuthorId())) {
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN).build());
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
    @PutMapping("/{post_id}")
    public ResponseEntity<ResponseObject> updatePost(
            @PathVariable("post_id") Long postId,
            @Valid @RequestBody PostDTO postDTO,
            Authentication authentication
    ){
        try{
            User currentUser = (User) authentication.getPrincipal();
            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            if (!currentUser.getUserId().equals(postDTO.getAuthorId())) {
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN).build());
            }
            Post updatedPost = postService.updatePost(postId, postDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .data(updatedPost)
                    .status(HttpStatus.OK)
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_POST_SUCCESSFULLY))
                    .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }
    @DeleteMapping("/{post_id}")
    public ResponseEntity<ResponseObject> deletePost(@PathVariable("post_id") Long postId, Authentication authentication){
        try{
            User currentUser = (User) authentication.getPrincipal();
            Post existingPost = postService.getPostById(postId);
            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            if (!currentUser.getUserId().equals(existingPost.getAuthor().getUserId())) {
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN).build());
            }
            postService.deletePost(postId);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_POST_SUCCESSFULLY))
                    .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }
    @GetMapping("/friend_posts/{user_id}")
    public ResponseEntity<ResponseObject> getFriendPosts(
        @PathVariable("user_id") Long userId,
        Authentication authentication
    ){
        try{
            User currentUser = (User) authentication.getPrincipal();
            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            if (!currentUser.getUserId().equals(userId)) {
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN).build());
            }
            List<PostResponse> postList = postService.getFriendPosts(userId);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .data(postList)
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.GET_FRIEND_POSTS_SUCCESSFULLY))
                    .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("images/{image_name}")
    public ResponseEntity<?> viewImage(@PathVariable("image_name") String imageName){
        try {
            Path imagePath = Paths.get("uploads/post_medias/images/"+imageName);
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
    @GetMapping("videos/{video_name}")
    public ResponseEntity<?> viewVideo(@PathVariable("video_name") String videoName){
        try {
            Path videoPath = Paths.get("uploads/post_medias/videos/"+videoName);
            // dung de truy cap tep hinh anh
            UrlResource resource = new UrlResource(videoPath.toUri());
            if(resource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("video/mp4"))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "upload_medias/{post_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMedias(
            @PathVariable("post_id") Long postId,
            @ModelAttribute("files") List<MultipartFile> files,
            Authentication authentication
    ){
        try{
            Post existingPost = postService.getPostById(postId);

            User currentUser = (User) authentication.getPrincipal();
            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            if (!currentUser.getUserId().equals(existingPost.getAuthor().getUserId())) {
                return ResponseEntity.ok(ResponseObject.builder()
                        .message("Unauthorized")
                        .status(HttpStatus.FORBIDDEN).build());
            }

            files = files == null ? new ArrayList<MultipartFile>() : files;
            if(files.size() > Media.MAXIMUM_MEDIA_PER_POST){
                return ResponseEntity.badRequest().body("You can only upload maximum 10 media files");
            }
            List<Media> postMedias = new ArrayList<>();
            for(MultipartFile file : files){
                if(file.getSize() == 0){
                    continue;
                }
                String folder;
                String contentType = file.getContentType();
                if (contentType.startsWith("image/")) {
                    // Check file size for images
                    if (file.getSize() > 10 * 1024 * 1024) { // > 10MB
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .body("Image file is too large! Maximum size is 10MB");
                    }
                    folder = "images";
                } else if (contentType.startsWith("video/")) {
                    // Check file size for videos
                    if (file.getSize() > 200 * 1024 * 1024) { // > 50MB
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .body("Video file is too large! Maximum size is 200MB");
                    }
                    folder = "videos";
                } else {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image or a video");
                }
                String filename;
                if(folder.equals("images")){
                    filename = storeFile(file, "post_medias/images");
                }else {
                    filename = storeFile(file, "post_medias/videos");
                }
                Media postMedia = postService.createPostMedia(
                        postId,
                        MediaDTO.builder()
                                .postId(postId)
                                .url(filename)
                                .mediaType(folder.substring(0, folder.length() - 1))
                                .build()
                );
                postMedias.add(postMedia);
            }
            return ResponseEntity.ok(postMedias);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private String storeFile(MultipartFile file, String folder) throws IOException {
        if(!isImageOrVideoFile(file) && file.getOriginalFilename() == null){
            throw new IOException("Invalid media format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Generate a unique filename to prevent conflicts
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Path to the desired folder
        java.nio.file.Path uploadDir = Paths.get("uploads", folder);
        if (!Files.exists(uploadDir)) { // Create the directory if it does not exist
            Files.createDirectories(uploadDir);
        }
        // Full path to the destination file
        java.nio.file.Path destination = uploadDir.resolve(uniqueFilename);
        // Copy the file to the destination
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    private boolean isImageOrVideoFile(MultipartFile file){
        String contentType = file.getContentType();
        return contentType != null && (contentType.startsWith("image/") || contentType.startsWith("video/"));
    }
}
