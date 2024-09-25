package com.project.facebook.services;

import com.project.facebook.dtos.PostDTO;
import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.Post;
import com.project.facebook.models.User;
import com.project.facebook.repositories.PostRepository;
import com.project.facebook.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService{
    private final UserRepository userRepository;
    private final PostRepository postRepository;
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
}
