package com.project.facebook.controllers;

import com.project.facebook.components.FileUtils;
import com.project.facebook.dtos.MediaDTO;
import com.project.facebook.dtos.PostDTO;
import com.project.facebook.dtos.PostFetchDTO;
import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.*;
import com.project.facebook.repositories.PageRepository;
import com.project.facebook.repositories.ProfileRepository;
import com.project.facebook.responses.ResponseObject;
import com.project.facebook.responses.media.MediaPostResponse;
import com.project.facebook.services.IMediaService;
import com.project.facebook.services.IPostService;
import com.project.facebook.services.MediaService;
import com.project.facebook.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.base-path}/medias")
@RequiredArgsConstructor
public class MediaController {
    private final IPostService postService;
    private final ProfileRepository profileRepository;
    private final PageRepository pageRepository;
    private final IMediaService mediaService;
    private final FileUtils fileUtils;
    // ok
    @GetMapping("image_post/{image_name}")
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
    // ok
    @GetMapping("video_post/{video_name}")
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
    // ok
    @PostMapping(value = "upload_medias_post/{post_id}/{note}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMediasPost(
            @PathVariable("post_id") Long postId,
            @PathVariable("note") String note,
            @ModelAttribute("files") List<MultipartFile> files,
            Authentication authentication
    ){
        try{
            Post existingPost = postService.getPostById(postId);
            User currentUser = (User) authentication.getPrincipal();
            // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
            if(existingPost.getAuthorType().equals(User.PROFILE)){
                Profile existingProfile = profileRepository.findById(existingPost.getAuthorId())
                        .orElseThrow(() -> new DataNotFoundException("Profile not found"));
                if (!currentUser.getUserId().equals(existingProfile.getUser().getUserId())) {
                    return ResponseEntity.ok(ResponseObject.builder()
                            .message("Unauthorized")
                            .status(HttpStatus.FORBIDDEN).build());
                }
            }else if(existingPost.getAuthorType().equals(User.PAGE)){
                Page existingPage = pageRepository.findById(existingPost.getAuthorId())
                        .orElseThrow(() -> new DataNotFoundException("Page not found"));
                if (!currentUser.getUserId().equals(existingPage.getUser().getUserId())) {
                    return ResponseEntity.ok(ResponseObject.builder()
                            .message("Unauthorized")
                            .status(HttpStatus.FORBIDDEN).build());
                }
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
                    filename = fileUtils.storeFile(file, "uploads/post_medias/images");
                }else {
                    filename = fileUtils.storeFile(file, "uploads/post_medias/videos");
                }
                Media postMedia = postService.createPostMedia(
                        postId,
                        MediaDTO.builder()
                                .postId(postId)
                                .url(filename)
                                .note(note)
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
    @GetMapping("/post/{post_id}")
    public ResponseEntity<ResponseObject> getMediaByPostId(@PathVariable("post_id") Long postId) {
        try {
            MediaPostResponse mediaPostResponse = mediaService.getMediaByPostId(postId);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Get post media successfully")
                    .data(mediaPostResponse)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }

}
