package com.project.facebook.repositories;

import com.project.facebook.models.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    List<Interaction> findByPostMediaIdAndPostMediaType(Long id, String type);
    List<Interaction> findByPostMediaIdAndPostMediaTypeAndInteractionType(Long postMediaId, String postMediaType, String interactionType);
    int countByPostMediaIdAndPostMediaType(Long id, String type);
    int countByPostMediaIdAndPostMediaTypeAndInteractionType(Long postMediaId, String postMediaType, String interactionType);

    @Query(value = "SELECT interaction_type FROM interactions WHERE post_media_id = :postId AND post_media_type = 'post'", nativeQuery = true)
    List<String> findInteractionTypesByPostId(@Param("postId") Long postId);

    @Query(value = "SELECT interaction_type FROM interactions WHERE post_media_id = :mediaId AND post_media_type = 'media'", nativeQuery = true)
    List<String> findInteractionTypesByMediaId(@Param("mediaId") Long mediaId);
}