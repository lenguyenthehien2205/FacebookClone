package com.project.facebook.responses.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.Post;
import com.project.facebook.responses.BaseResponse;
import com.project.facebook.responses.interaction.InteractionResponse;
import com.project.facebook.responses.media.MediaPostResponse;
import com.project.facebook.responses.media.MediaResponse;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse extends BaseResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("author_id")
    private Long authorId;

    @JsonProperty("author_type")
    private String authorType;

    @JsonProperty("post_type")
    private String postType;

    @JsonProperty("author_name")
    private String authorName;

    private String content;

    private String privacy;

    private String avatar;

    @JsonProperty("medias")
    private List<MediaResponse> mediaResponses;

    @JsonProperty("interaction_response")
    private InteractionResponse interactionResponses;

    @JsonProperty("is_online")
    private Boolean isOnline;

    @JsonProperty("is_active")
    private Boolean isActive;

    public static PostResponse fromPost(Post post){
        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .authorId(post.getAuthorId())
                .authorType(post.getAuthorType())
                .postType(post.getPostType())
                .privacy(post.getPrivacy())
                .content(post.getContent())
                .isActive(post.isActive())
                .build();
        postResponse.setUpdatedAt(post.getUpdatedAt());
        postResponse.setCreatedAt(post.getCreatedAt());
        return postResponse;
    }
}
