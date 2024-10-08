package com.project.facebook.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.facebook.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor_UserIdIn(List<Long> userIds);
    // Lấy bài viết mới nhất cho mỗi người đăng có bài viết chưa được lấy
//    @Query(value = "SELECT p.* FROM post p " +
//            "WHERE p.author_id IN (" +
//            "   SELECT DISTINCT p2.author_id FROM post p2 " +
//            "   WHERE p2.id NOT IN (:fetchedIds) " +  // Loại bỏ những bài viết đã được lấy
//            "   GROUP BY p2.author_id " +
//            "   HAVING COUNT(p2.id) > 0 " +  // Chỉ chọn những người đăng còn bài viết chưa được lấy
//            "   ORDER BY RAND() LIMIT :authorLimit" +
//            ") " +
//            "AND p.created_at = (SELECT MAX(p3.created_at) FROM post p3 WHERE p3.author_id = p.author_id) " +
//            "AND p.id NOT IN (:fetchedIds)", nativeQuery = true)
//    List<Post> getRandomAuthorsLatestPosts(@Param("fetchedIds") Set<Long> fetchedIds, @Param("authorLimit") int authorLimit);
//    @Query(value = "SELECT p.* FROM post p " +
//            "WHERE p.author_id IN (" +
//            "   SELECT DISTINCT p2.author_id FROM post p2 ")
//            "   WHERE (:fetchedIds IS NULL OR CARDINALITY(:fetchedIds) = 0 OR p2.post_id NOT IN (:fetchedIds)) " )
//            "   GROUP BY p2.author_id " +
//            "   HAVING COUNT(p2.id) > 0 " +
//            "   ORDER BY RAND() LIMIT :authorLimit" +
//            ") " +
//            "AND p.created_at = (SELECT MAX(p3.created_at) FROM post p3 WHERE p3.author_id = p.author_id) " +
//            "AND (:fetchedIds IS NULL OR CARDINALITY(:fetchedIds) = 0 OR p.id NOT IN (:fetchedIds))", nativeQuery = true)
//    @Query(value = "SELECT * FROM posts p WHERE p.post_id NOT IN (:fetchedIds) LIMIT :limit", nativeQuery = true)
//    List<Post> getRandomAuthorsLatestPosts(@Param("fetchedIds") List<Long> fetchedIds, @Param("limit") int limit);
    // Lấy bài viết mới nhất cho mỗi người đăng có bài viết chưa được lấy
    @Query(value = "SELECT * FROM ( \n" +
            "   SELECT p.* FROM posts p \n" +
            "   WHERE p.post_id NOT IN (:fetchedIds)" +
            "   AND p.privacy != 'only me' \n" +
            "   AND (p.author_id = :userId OR \n" +
            "         p.author_id IN ( \n" +
            "             SELECT f.second_user_id FROM friends f \n" +
            "             WHERE f.first_user_id = :userId \n" +
            "             UNION \n" +
            "             SELECT f.first_user_id FROM friends f \n" +
            "             WHERE f.second_user_id = :userId \n" +
            "         )) \n" +
            "   ORDER BY p.created_at DESC \n" +
            "   LIMIT :limit) AS latest_posts \n" +
            "ORDER BY RAND() LIMIT 0, 25", nativeQuery = true)
    List<Post> getLatestRandomFetchedFriendPosts(@Param("userId") Long userId, @Param("limit") int limit, @Param("fetchedIds") List<Long> fetchedIds);


    @Query(value = "SELECT * FROM ( \n" +
            "   SELECT p.* FROM posts p \n" +
            "   WHERE (p.author_id = :userId OR \n" +
            "         p.author_id IN ( \n" +
            "             SELECT f.second_user_id FROM friends f \n" +
            "             WHERE f.first_user_id = :userId \n" +
            "             UNION \n" +
            "             SELECT f.first_user_id FROM friends f \n" +
            "             WHERE f.second_user_id = :userId \n" +
            "         )) \n" +
            "   AND p.privacy != 'only me' \n" +
            "   ORDER BY p.created_at DESC \n" +
            "   LIMIT :limit) AS latest_posts \n" +
            "ORDER BY RAND() LIMIT 0, 25", nativeQuery = true)
    List<Post> getLatestRandomFriendPosts(@Param("userId") Long userId, @Param("limit") int limit);

}

