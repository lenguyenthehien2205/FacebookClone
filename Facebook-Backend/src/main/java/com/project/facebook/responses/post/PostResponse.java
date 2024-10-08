package com.project.facebook.responses.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.facebook.models.Media;
import com.project.facebook.models.Post;
import com.project.facebook.repositories.MediaRepository;
import com.project.facebook.responses.BaseResponse;
import com.project.facebook.responses.media.MediaResponse;
import com.project.facebook.responses.reaction.ReactionResponse;
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

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("display_format")
    private String displayFormat;

    private String content;

    private String privacy;

    @JsonProperty("author_type")
    private String authorType;

    private String avatar;

    private List<MediaResponse> medias;

    private List<ReactionResponse> reactions;

    @JsonProperty("is_online")
    private Boolean isOnline;
    public static PostResponse fromPost(Post post, List<MediaResponse> mediaResponses, List<ReactionResponse> reactionResponses) {
        PostResponse postResponse = PostResponse.builder()
                .postId(post.getPostId())
                .content(post.getContent())
                .authorId(post.getAuthor().getUserId())
                .authorName(post.getAuthor().getUsername())
                .firstName(post.getAuthor().getFirstName())
                .lastName(post.getAuthor().getLastName())
                .displayFormat(post.getAuthor().getDisplayFormat())
                .privacy(post.getPrivacy())
                .authorType(post.getAuthorType())
                .avatar(post.getAuthor().getAvatar())
                .medias(mediaResponses)
                .reactions(reactionResponses)
                .isOnline(post.getAuthor().isOnline())
                .build();
        postResponse.setCreatedAt(post.getCreatedAt());
        postResponse.setUpdatedAt(post.getUpdatedAt());
        return postResponse;
    }
}
