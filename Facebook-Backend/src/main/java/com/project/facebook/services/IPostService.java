package com.project.facebook.services;

import com.project.facebook.dtos.PostDTO;
import com.project.facebook.models.Post;

public interface IPostService {
    Post createPost(PostDTO postDTO) throws Exception;

}
