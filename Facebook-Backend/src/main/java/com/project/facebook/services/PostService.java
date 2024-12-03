package com.project.facebook.services;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.project.facebook.models.*;
import com.project.facebook.responses.interaction.InteractionResponse;
import org.springframework.stereotype.Service;

import com.project.facebook.dtos.MediaDTO;
import com.project.facebook.dtos.PostDTO;
import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.exceptions.InvalidParamException;
import com.project.facebook.repositories.FriendRepository;
import com.project.facebook.repositories.InteractionRepository;
import com.project.facebook.repositories.MediaRepository;
import com.project.facebook.repositories.PageBaseRepository;
import com.project.facebook.repositories.PageRepository;
import com.project.facebook.repositories.PostRepository;
import com.project.facebook.repositories.ProfileRepository;
import com.project.facebook.repositories.UserRepository;
import com.project.facebook.responses.media.MediaPostResponse;
import com.project.facebook.responses.media.MediaResponse;
import com.project.facebook.responses.post.PostResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService{
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FriendRepository friendRepository;
    private final MediaRepository mediaRepository;
    private final InteractionRepository interactionRepository;
    private final ProfileRepository profileRepository;
    private final PageRepository pageRepository;
    private final PageBaseRepository pageBaseRepository;
    private final InteractionService interactionService;
    // ok
    @Override
    public Post createPost(PostDTO postDTO) throws Exception {
        Long authorId = postDTO.getAuthorId();
        if(postDTO.getAuthorType().equals(User.PROFILE)){
            Profile profile = profileRepository.findById(authorId)
                    .orElseThrow(() -> new DataNotFoundException(String.format("Not found profile with id = %s", authorId)));
        }else if(postDTO.getAuthorType().equals(User.PAGE)){
            Page page = pageRepository.findById(authorId)
                    .orElseThrow(() -> new DataNotFoundException(String.format("Not found page with id = %s", authorId)));
        }
        String authorType = postDTO.getAuthorType();
        if(!authorType.equals(User.PROFILE) && !authorType.equals(User.PAGE)){
            throw new DataNotFoundException("Author type not found");
        }
        String privacy = postDTO.getPrivacy();
        if(!privacy.equals(Post.PUBLIC) && !privacy.equals(Post.FRIENDS) && !privacy.equals(Post.PRIVATE)){
            throw new DataNotFoundException("Privacy not found");
        }
        Post newPost = Post
                .builder()
                .authorId(authorId)
                .authorType(authorType)
                .postType(postDTO.getPostType())
                .privacy(privacy)
                .content(postDTO.getContent())
                .isActive(true)
                .build();
        return postRepository.save(newPost);
    }
    // chua xong
    @Override
    public Post updatePost(Long postId, PostDTO postDTO) throws Exception{
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));
        if(!postDTO.getPrivacy().equals(Post.PUBLIC) && !postDTO.getPrivacy().equals(Post.FRIENDS) && !postDTO.getPrivacy().equals(Post.PRIVATE)){
            throw new DataNotFoundException("Privacy not found");
        }
        existingPost.setContent(postDTO.getContent());
        existingPost.setPrivacy(postDTO.getPrivacy());
        return postRepository.save(existingPost);
    }
    // ok
    @Override
    public void deletePost(Long postId) throws Exception{
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));
        if(!existingPost.isActive()){
            throw new DataNotFoundException("Post not found");
        }
        existingPost.setActive(false);
        postRepository.save(existingPost);
    }
    // ok
    @Override
    public Post getPostById(Long postId) throws Exception{
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));
        return existingPost;
    }
//    @Override
//    public List<PostResponse> getFriendPosts(Long userId) throws Exception {
//        User existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy người dùng"));
//        List<User> friends = friendRepository.findAllFriendsByUserId(userId);
//        List<Long> friendIds = friends.stream()
//                .map(User::getUserId)
//                .collect(Collectors.toList());
//        List<Post> friendPosts = postRepository.findByAuthor_UserIdIn(friendIds);
//        return friendPosts.stream()
//                .map(post -> {
//                    List<MediaResponse> mediaResponses = mediaRepository.findByPost_PostId(post.getPostId())
//                            .stream()
//                            .map(MediaResponse::fromMedia)
//                            .collect(Collectors.toList());
//                    List<ReactionResponse> reactionResponses = reactionRepository.findByPost_PostId(post.getPostId())
//                            .stream()
//                            .map(ReactionResponse::fromReaction)
//                            .collect(Collectors.toList());
//                    return PostResponse.fromPost(post, mediaResponses, reactionResponses);
//                })
//                .collect(Collectors.toList());
//    }
    //ok
    @Override
    public Media createPostMedia(Long postId, MediaDTO mediaDTO) throws Exception {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Can't find post"));
        Media postMedia = Media.builder()
                .post(existingPost)
                .url(mediaDTO.getUrl())
                .note(mediaDTO.getNote())
                .mediaType(mediaDTO.getMediaType())
                .build();
        int size = mediaRepository.findByPostId(postId).size();
        if(size >= Media.MAXIMUM_MEDIA_PER_POST){
            throw new InvalidParamException("Number of media must be <= "+size);
        }
        return mediaRepository.save(postMedia);
    }

    // ok
//    public List<PostResponseCompleted> getLatestRandomFriendPosts(Long userId, int limit, List<Long> fetchedIds) {
//        List<PostResponse> posts;
//        if (fetchedIds == null || fetchedIds.isEmpty()) {
//            // Nếu fetchedIds rỗng, gọi truy vấn không có điều kiện NOT IN
//            posts = postRepository.getLatestRandomFriendPosts(userId, limit);
//        } else {
//            // Nếu fetchedIds không rỗng, gọi truy vấn với điều kiện NOT IN
//            posts = postRepository.getLatestRandomFetchedFriendPosts(userId, limit, fetchedIds);
//        }
//        List<PostResponseCompleted> postsCompleted = posts.stream()
//                .map(PostResponseCompleted::toCompletedResponse)
//                .collect(Collectors.toList());
//
//        postsCompleted.forEach(postResponse -> {
//            List<MediaResponse> mediaResponses = mediaRepository.findByPostId(postResponse.getId())
//                    .stream()
//                    .map(MediaResponse::fromMedia)
//                    .collect(Collectors.toList());
//            postResponse.setMedias(mediaResponses);
//
//            List<ReactionResponse> reactionResponses = reactionRepository.findByPostId(postResponse.getId())
//                    .stream()
//                    .map(ReactionResponse::fromReaction)
//                    .collect(Collectors.toList());
//            postResponse.setReactions(reactionResponses);
//        });
//        return postsCompleted;
//    }
    @Override
    public List<PostResponse> getLatestRandomFetchedFriendPosts(Long userId, int limit, List<Long> fetchedIds) {
        List<Post> posts;
        if (fetchedIds == null || fetchedIds.isEmpty()) {
            posts = postRepository.getLatestRandomFriendPosts(userId, limit);
        } else {
            posts = postRepository.getLatestRandomFetchedFriendPosts(userId, limit, fetchedIds);
        }

        List<PostResponse> postResponses = posts.stream().map(post -> {
            PostResponse postResponse = PostResponse.fromPost(post);

            // Fetch author information based on author_type
            if (post.getAuthorType().equals(User.PROFILE)) {
                Optional<Profile> profOpt = profileRepository.findById(post.getAuthorId());
                if (profOpt.isPresent()) {
                    Profile prof = profOpt.get();
                    postResponse.setAuthorName(prof.getDisplayFormat().equals("firstname_lastname")
                            ? prof.getFirstName() + " " + prof.getLastName()
                            : prof.getLastName() + " " + prof.getFirstName());
                    postResponse.setIsOnline(prof.getIsOnline() ? true : false);
                }
            } else if (post.getAuthorType().equals(User.PAGE)) {
                Optional<Page> pageOpt = pageRepository.findById(post.getAuthorId());
                if (pageOpt.isPresent()) {
                    Page page = pageOpt.get();
                    postResponse.setAuthorName(page.getPageName());
                    postResponse.setIsOnline(false); // Pages are not online
                }
            }

            // Fetch avatar from PageBaseRepo
            Optional<PageBase> pageBaseOpt = pageBaseRepository.findById(post.getAuthorId());
            if (pageBaseOpt.isPresent()) {
                postResponse.setAvatar(pageBaseOpt.get().getAvatar());
            }

            List<Media> medias = mediaRepository.findByPostId(post.getId());
            List<MediaResponse> mediaResponses = medias.stream()
                    .map(MediaResponse::fromMedia)
                    .collect(Collectors.toList());
            postResponse.setMediaResponses(mediaResponses);

            InteractionResponse interactionResponses = interactionService.getInteractionPost(post.getId());
            postResponse.setInteractionResponses(interactionResponses);

            return postResponse;
        }).collect(Collectors.toList());
        return postResponses;
    }
}
