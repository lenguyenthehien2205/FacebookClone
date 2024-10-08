package com.project.facebook.repositories;

import com.project.facebook.models.Media;
import com.project.facebook.models.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    List<Reaction> findByPost_PostId(Long postId);
}
