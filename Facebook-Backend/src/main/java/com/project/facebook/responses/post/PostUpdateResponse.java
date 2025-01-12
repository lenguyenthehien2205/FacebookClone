package com.project.facebook.responses.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.Post;
import com.project.facebook.responses.media.MediaResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PostUpdateResponse {
    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("author_name")
    private String authorName;

    private String privacy;

    @JsonProperty("content")
    private String content;

    @JsonProperty("medias")
    private List<MediaResponse> medias;

    public static PostUpdateResponse fromPost(Post post){
        PostUpdateResponse postUpdateResponse = PostUpdateResponse.builder()
                .postId(post.getId())
                .privacy(post.getPrivacy())
                .content(post.getContent())
                .build();
        return postUpdateResponse;
    }
}
