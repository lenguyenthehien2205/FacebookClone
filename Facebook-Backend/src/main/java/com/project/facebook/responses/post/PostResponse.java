package com.project.facebook.responses.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.Media;
import com.project.facebook.models.Post;
import com.project.facebook.repositories.MediaRepository;
import com.project.facebook.responses.BaseResponse;
import com.project.facebook.responses.media.MediaResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class PostResponse extends BaseResponse {
    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("author_id")
    private Long authorId;

    @JsonProperty("author_name")
    private String authorName;

    private String content;

    private String privacy;

    @JsonProperty("author_type")
    private String authorType;

    private List<MediaResponse> medias;
    public static PostResponse fromPost(Post post, List<MediaResponse> mediaResponses) {
        PostResponse postResponse = PostResponse.builder()
                .postId(post.getPostId())
                .content(post.getContent())
                .authorId(post.getAuthor().getUserId())
                .authorName(post.getAuthor().getUsername())
                .privacy(post.getPrivacy())
                .authorType(post.getAuthorType())
                .medias(mediaResponses)
                .build();
        postResponse.setCreatedAt(post.getCreatedAt());
        postResponse.setUpdatedAt(post.getUpdatedAt());
        return postResponse;
    }
}
