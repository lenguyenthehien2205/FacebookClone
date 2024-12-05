package com.project.facebook.repositories;

import com.project.facebook.models.Media;
import com.project.facebook.projections.medias.MediaImageProfileProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    int countByPostId(Long postId);
    List<Media> findByPostId(Long postId);
    @Query(value = """
            SELECT m.media_id, m.url FROM medias m
            JOIN posts p ON p.id = m.post_id
            WHERE m.media_type = 'image'
            AND p.author_type = 'profile'
            AND p.author_id = :profile_id
            ORDER BY p.created_at DESC, m.media_id ASC
            LIMIT :limit;
            """, nativeQuery = true)
    List<MediaImageProfileProjection> getImagesByProfileId(@Param("profile_id") Long profileId, @Param("limit") int limit);
}
