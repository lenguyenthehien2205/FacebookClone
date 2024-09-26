package com.project.facebook.repositories;

import com.project.facebook.models.Media;
import com.project.facebook.responses.media.MediaResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByPost_PostId(Long postId);
}
