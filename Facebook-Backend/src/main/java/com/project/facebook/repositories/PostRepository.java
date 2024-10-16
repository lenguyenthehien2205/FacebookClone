package com.project.facebook.repositories;

import java.util.List;

import com.project.facebook.responses.post.PostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.facebook.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findByAuthor_UserIdIn(List<Long> userIds);
//    @Query(value = "SELECT latest_posts.*, \n" +
//            "    CASE \n" +
//            "        WHEN latest_posts.author_type = 'profile' THEN \n" +
//            "            CASE \n" +
//            "                WHEN prof.display_format = 'firstname_lastname' THEN CONCAT(prof.first_name, ' ', prof.last_name)\n" +
//            "                ELSE CONCAT(prof.last_name, ' ', prof.first_name)\n" +
//            "            END \n" +
//            "        WHEN latest_posts.author_type = 'page' THEN pg.page_name \n" +
//            "    END AS author_name, \n" +
//            "    CASE \n" +
//            "        WHEN latest_posts.author_type = 'profile' THEN prof.is_online \n" +
//            "        WHEN latest_posts.author_type = 'page' THEN 0 \n" +
//            "    END AS is_online, \n" +
//            "    CASE \n" +
//            "        WHEN latest_posts.author_type = 'profile' THEN pb.avatar \n" +
//            "        WHEN latest_posts.author_type = 'page' THEN pb.avatar \n" +
//            "    END AS avatar \n" +
//            "FROM ( \n" +
//            "    SELECT p.* \n" +
//            "    FROM posts p \n" +
//            "    WHERE p.id NOT IN (:fetchedIds) \n" +
//            "    AND p.privacy != 'only me' \n" +
//            "    AND (p.author_id = :userId OR \n" +
//            "         p.author_id IN ( \n" +
//            "             SELECT f.second_user_id FROM friends f \n" +
//            "             WHERE f.first_user_id = :userId \n" +
//            "             UNION \n" +
//            "             SELECT f.first_user_id FROM friends f \n" +
//            "             WHERE f.second_user_id = :userId \n" +
//            "         ) \n" +
//            "    ) \n" +
//            "    ORDER BY p.created_at DESC \n" +
//            "    LIMIT :limit \n" +
//            ") AS latest_posts \n" +
//            "LEFT JOIN profiles prof ON latest_posts.author_id = prof.id AND latest_posts.author_type = 'profile' \n" +
//            "LEFT JOIN pages pg ON latest_posts.author_id = pg.id AND latest_posts.author_type = 'page' \n" +
//            "LEFT JOIN page_bases pb ON (prof.base_id = pb.id AND latest_posts.author_type = 'profile') \n" +
//            "    OR (pg.base_id = pb.id AND latest_posts.author_type = 'page') \n" +
//            "ORDER BY RAND() \n" +
//            "LIMIT 25", nativeQuery = true)
//    List<PostResponse> getLatestRandomFetchedFriendPosts(@Param("userId") Long userId,
//                                                         @Param("limit") int limit,
//                                                         @Param("fetchedIds") List<Long> fetchedIds);

    @Query(value = "SELECT latest_posts.* FROM ( " +
            "    SELECT p.* " +
            "    FROM posts p " +
            "    WHERE p.id NOT IN (:fetchedIds) " +
            "    AND p.privacy != 'only me' " +
            "    AND (p.author_id = :profileId OR " +
            "         p.author_id IN ( " +
            "             SELECT f.second_profile_id FROM friends f " +
            "             WHERE f.first_profile_id = :profileId " +
            "             UNION " +
            "             SELECT f.first_profile_id FROM friends f " +
            "             WHERE f.second_profile_id = :profileId " +
            "         ) " +
            "    ) " +
            "    ORDER BY p.created_at DESC " +
            "    LIMIT :limit " +
            ") AS latest_posts " +
            "ORDER BY RAND() LIMIT 25", nativeQuery = true)
    List<Post> getLatestRandomFetchedFriendPosts(@Param("profileId") Long profileId,
                                                 @Param("limit") int limit,
                                                 @Param("fetchedIds") List<Long> fetchedIds);
    @Query(value = "SELECT latest_posts.* FROM ( " +
            "    SELECT p.* " +
            "    FROM posts p " +
            "        WHERE p.privacy != 'only me' \n" +
            "        AND (p.author_id = :profileId OR \n" +
            "             p.author_id IN (\n" +
            "                 SELECT f.second_profile_id FROM friends f \n" +
            "                 WHERE f.first_profile_id = :profileId \n" +
            "                 UNION \n" +
            "                 SELECT f.first_profile_id FROM friends f \n" +
            "                 WHERE f.second_profile_id = :profileId \n" +
            "             )\n" +
            "        )\n" +
            "    ORDER BY p.created_at DESC " +
            "    LIMIT :limit " +
            ") AS latest_posts " +
            "ORDER BY RAND() LIMIT 25", nativeQuery = true)
    List<Post> getLatestRandomFriendPosts(@Param("profileId") Long profileId, @Param("limit") int limit);

}

