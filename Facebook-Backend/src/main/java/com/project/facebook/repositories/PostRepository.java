package com.project.facebook.repositories;

import com.project.facebook.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor_UserIdIn(List<Long> userIds);
}
