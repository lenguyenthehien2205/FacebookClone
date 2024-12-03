package com.project.facebook.controllers;

import com.project.facebook.models.*;
import com.project.facebook.responses.ResponseObject;
import com.project.facebook.responses.interaction.InteractionDetailResponse;
import com.project.facebook.responses.interaction.InteractionResponse;
import com.project.facebook.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("${api.base-path}/interactions")
@RequiredArgsConstructor
public class InteractionController {
    private final IPostService postService;
    private final IProfileService profileService;
    private final IPageService pageService;
    private final IUserService userService;
    private final IMediaService mediaService;
    private final IInteractionService interactionService;
    @GetMapping("/media/{media_id}")
    public ResponseEntity<ResponseObject> getInteractionMedia(@PathVariable("media_id") Long mediaId, Authentication authentication) {
        try {
            Media media = mediaService.getMediaById(mediaId);
            Post post = postService.getPostById(media.getPost().getId());
            User userPost = null;
            InteractionResponse interactionResponse = interactionService.getInteractionMedia(mediaId);
            if(post.getAuthorType().equals(User.PROFILE)){
                Profile profile = profileService.getProfileById(post.getAuthorId());
                userPost = userService.getUserById(profile.getUser().getUserId());
            }else if(post.getAuthorType().equals(User.PAGE)){
                Page page = pageService.getPageById(post.getAuthorId());
                userPost = userService.getUserById(page.getUser().getUserId());
            }
            if(post.getPrivacy().equals(Post.PRIVATE)){
                User currentUser = (User) authentication.getPrincipal();
                // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
                if (!currentUser.getUserId().equals(userPost.getUserId())) {
                    return ResponseEntity.badRequest().body((ResponseObject.builder()
                            .message("Unauthorized")
                            .status(HttpStatus.FORBIDDEN).build()));
                }
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Get media interaction successfully")
                        .data(interactionResponse)
                        .build());
            }else{
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Get media interaction successfully")
                        .data(interactionResponse)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }
    @GetMapping("/post/{post_id}")
    public ResponseEntity<ResponseObject> getInteractionPost(@PathVariable("post_id") Long postId, Authentication authentication) {
        try {
            Post post = postService.getPostById(postId);
            User userPost = null;
            InteractionResponse interactionResponse = interactionService.getInteractionPost(postId);
            if(post.getAuthorType().equals(User.PROFILE)){
                Profile profile = profileService.getProfileById(post.getAuthorId());
                userPost = userService.getUserById(profile.getUser().getUserId());
            }else if(post.getAuthorType().equals(User.PAGE)){
                Page page = pageService.getPageById(post.getAuthorId());
                userPost = userService.getUserById(page.getUser().getUserId());
            }
            if(post.getPrivacy().equals(Post.PRIVATE)){
                User currentUser = (User) authentication.getPrincipal();
                // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
                if (!currentUser.getUserId().equals(userPost.getUserId())) {
                    return ResponseEntity.badRequest().body((ResponseObject.builder()
                            .message("Unauthorized")
                            .status(HttpStatus.FORBIDDEN).build()));
                }
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Get post interaction successfully")
                        .data(interactionResponse)
                        .build());
            }else{
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Get post interaction successfully")
                        .data(interactionResponse)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }
    // lay ra nguoi tha cam xuc theo loại
    @GetMapping("/post/{post_id}/{interaction_type}")
    public ResponseEntity<ResponseObject> getInteractionDetailPostWithInteractionType(
            @PathVariable("post_id") Long postId,
            @PathVariable("interaction_type") String interactionType,
            Authentication authentication) {
        try {
//            Post post = postService.getPostById(postId);
//            User userPost = null;
            InteractionDetailResponse interactionDetailResponse = interactionService.getInteractionDetailPostWithInteractionType(postId, interactionType);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Get post interaction successfully")
                    .data(interactionDetailResponse)
                    .build());
//            if(post.getAuthorType().equals(User.PROFILE)){
//                Profile profile = profileService.getProfileById(post.getAuthorId());
//                userPost = userService.getUserById(profile.getUser().getUserId());
//            }else if(post.getAuthorType().equals(User.PAGE)){
//                Page page = pageService.getPageById(post.getAuthorId());
//                userPost = userService.getUserById(page.getUser().getUserId());
//            }
//            if(post.getPrivacy().equals(Post.PRIVATE)){
//                User currentUser = (User) authentication.getPrincipal();
//                // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
//                if (!currentUser.getUserId().equals(userPost.getUserId())) {
//                    return ResponseEntity.badRequest().body((ResponseObject.builder()
//                            .message("Unauthorized")
//                            .status(HttpStatus.FORBIDDEN).build()));
//                }
//                return ResponseEntity.ok(ResponseObject.builder()
//                        .status(HttpStatus.OK)
//                        .message("Get post interaction successfully")
//                        .data(interactionDetailResponse)
//                        .build());
//            }else{
//
//            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }
    @GetMapping("/post/detail/{post_id}")
    public ResponseEntity<ResponseObject> getInteractionDetailPost(@PathVariable("post_id") Long postId, Authentication authentication) {
        try {
            Post post = postService.getPostById(postId);
            User userPost = null;
            InteractionDetailResponse interactionDetailResponse = interactionService.getInteractionDetailPost(postId);
            if(post.getAuthorType().equals(User.PROFILE)){
                Profile profile = profileService.getProfileById(post.getAuthorId());
                userPost = userService.getUserById(profile.getUser().getUserId());
            }else if(post.getAuthorType().equals(User.PAGE)){
                Page page = pageService.getPageById(post.getAuthorId());
                userPost = userService.getUserById(page.getUser().getUserId());
            }
            if(post.getPrivacy().equals(Post.PRIVATE)){
                User currentUser = (User) authentication.getPrincipal();
                // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
                if (!currentUser.getUserId().equals(userPost.getUserId())) {
                    return ResponseEntity.badRequest().body((ResponseObject.builder()
                            .message("Unauthorized")
                            .status(HttpStatus.FORBIDDEN).build()));
                }
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Get post interaction successfully")
                        .data(interactionDetailResponse)
                        .build());
            }else{
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Get post interaction successfully")
                        .data(interactionDetailResponse)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }
    @GetMapping("/media/detail/{media_id}")
    public ResponseEntity<ResponseObject> getInteractionDetailMedia(@PathVariable("media_id") Long mediaId, Authentication authentication) {
        try {
            Media media = mediaService.getMediaById(mediaId);
            Post post = postService.getPostById(media.getPost().getId());
            User userPost = null;
            InteractionDetailResponse interactionDetailResponse = interactionService.getInteractionDetailMedia(mediaId);
            if(post.getAuthorType().equals(User.PROFILE)){
                Profile profile = profileService.getProfileById(post.getAuthorId());
                userPost = userService.getUserById(profile.getUser().getUserId());
            }else if(post.getAuthorType().equals(User.PAGE)){
                Page page = pageService.getPageById(post.getAuthorId());
                userPost = userService.getUserById(page.getUser().getUserId());
            }
            if(post.getPrivacy().equals(Post.PRIVATE)){
                User currentUser = (User) authentication.getPrincipal();
                // Kiểm tra xem userId có khớp với người dùng đang đăng nhập không
                if (!currentUser.getUserId().equals(userPost.getUserId())) {
                    return ResponseEntity.badRequest().body((ResponseObject.builder()
                            .message("Unauthorized")
                            .status(HttpStatus.FORBIDDEN).build()));
                }
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Get media interaction successfully")
                        .data(interactionDetailResponse)
                        .build());
            }else{
                return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Get media interaction successfully")
                        .data(interactionDetailResponse)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }

}
