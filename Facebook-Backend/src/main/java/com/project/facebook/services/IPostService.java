package com.project.facebook.services;

import java.util.List;

import com.project.facebook.dtos.MediaDTO;
import com.project.facebook.dtos.PostDTO;
import com.project.facebook.models.Media;
import com.project.facebook.models.Post;
import com.project.facebook.responses.post.PostResponse;

public interface IPostService {
    Post createPost(PostDTO postDTO) throws Exception;
    Post updatePost(Long postId, PostDTO postDTO) throws Exception;
    void deletePost(Long postId) throws Exception;
    Post getPostById(Long postId) throws Exception;
//    List<PostResponse> getFriendPosts(Long userId) throws Exception;
    Media createPostMedia(Long id, MediaDTO mediaDTO) throws Exception;
    List<PostResponse> getLatestRandomFetchedFriendPosts(Long userId, int limit, List<Long> fetchedIds);
}
