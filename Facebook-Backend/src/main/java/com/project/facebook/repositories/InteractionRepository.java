package com.project.facebook.repositories;

import com.project.facebook.models.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    List<Interaction> findByPostMediaIdAndPostMediaType(Long id, String type);
    int countByPostMediaIdAndPostMediaType(Long id, String type);

    @Query(value = "SELECT interaction_type FROM interactions WHERE post_media_id = :postMediaId AND post_media_type = 'post'", nativeQuery = true)
    List<String> findInteractionTypesByPostMediaId(@Param("postMediaId") Long postMediaId);

    @Query(value = "SELECT interaction_type FROM interactions WHERE post_media_id = :mediaId AND post_media_type = 'media'", nativeQuery = true)
    List<String> findInteractionTypesByMediaId(@Param("mediaId") Long mediaId);
}