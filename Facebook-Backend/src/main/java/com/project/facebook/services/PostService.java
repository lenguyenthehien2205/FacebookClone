package com.project.facebook.services;

import java.util.List;
import java.util.stream.Collectors;

import com.project.facebook.dtos.MediaDTO;
import com.project.facebook.exceptions.InvalidParamException;
import com.project.facebook.models.Media;
import org.springframework.stereotype.Service;

import com.project.facebook.dtos.PostDTO;
import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.Post;
import com.project.facebook.models.User;
import com.project.facebook.repositories.FriendRepository;
import com.project.facebook.repositories.MediaRepository;
import com.project.facebook.repositories.PostRepository;
import com.project.facebook.repositories.UserRepository;
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
    @Override
    public Post createPost(PostDTO postDTO) throws Exception {
        User author = userRepository.findById(postDTO.getAuthorId())
                .orElseThrow(() -> new  DataNotFoundException("Author not found"));
        String authorType = postDTO.getAuthorType();
        if(!authorType.equals("user") && !authorType.equals("page")){
            throw new DataNotFoundException("Author type not found");
        }
        String privacy = postDTO.getPrivacy();
        if(!privacy.equals("public") && !privacy.equals("friends") && !privacy.equals("only me")){
            throw new DataNotFoundException("Privacy not found");
        }
        Post newPost = Post
                .builder()
                .author(author)
                .authorType(authorType)
                .privacy(privacy)
                .content(postDTO.getContent())
                .isActive(true)
                .build();
        return postRepository.save(newPost);
    }
    @Override
    public Post updatePost(Long postId, PostDTO postDTO) throws Exception{
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));
        if(!postDTO.getPrivacy().equals("public") && !postDTO.getPrivacy().equals("friends") && !postDTO.getPrivacy().equals("only me")){
            throw new DataNotFoundException("Privacy not found");
        }
        existingPost.setContent(postDTO.getContent());
        existingPost.setPrivacy(postDTO.getPrivacy());
        return postRepository.save(existingPost);
    }

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
    @Override
    public Post getPostById(Long postId) throws Exception{
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));
        return existingPost;
    }
    @Override
    public List<PostResponse> getFriendPosts(Long userId) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy người dùng"));
        List<User> friends = friendRepository.findAllFriendsByUserId(userId);
        List<Long> friendIds = friends.stream()
                .map(User::getUserId)
                .collect(Collectors.toList());
        List<Post> friendPosts = postRepository.findByAuthor_UserIdIn(friendIds);
        return friendPosts.stream()
                .map(post -> {
                    List<MediaResponse> mediaResponses = mediaRepository.findByPost_PostId(post.getPostId())
                            .stream()
                            .map(MediaResponse::fromMedia)
                            .collect(Collectors.toList());
                    return PostResponse.fromPost(post, mediaResponses);
                })
                .collect(Collectors.toList());
    }
    @Override
    public Media createPostMedia(Long postId, MediaDTO mediaDTO) throws Exception {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Can't find post"));
        Media postMedia = Media.builder()
                .post(existingPost)
                .url(mediaDTO.getUrl())
                .mediaType(mediaDTO.getMediaType())
                .build();
        int size = mediaRepository.findByPost_PostId(postId).size();
        if(size >= Media.MAXIMUM_MEDIA_PER_POST){
            throw new InvalidParamException("Number of media must be <= "+size);
        }
        return mediaRepository.save(postMedia);
    }
}
