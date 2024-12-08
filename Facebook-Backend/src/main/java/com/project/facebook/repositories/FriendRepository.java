package com.project.facebook.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.facebook.models.Friend;
import com.project.facebook.models.Profile;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END " +
            "FROM friends f " +
            "WHERE (f.first_profile_id = :firstProfileId AND f.second_profile_id = :secondProfileId) " +
            "OR (f.first_profile_id = :secondProfileId AND f.second_profile_id = :firstProfileId)", nativeQuery = true)
    int existsFriendship(@Param("firstProfileId") Long firstProfileId, @Param("secondProfileId") Long secondProfileId);

    @Query("SELECT p FROM Profile p WHERE p.id IN " +
            "(SELECT CASE WHEN f.firstProfile.id = :profileId THEN f.secondProfile.id " +
            "            WHEN f.secondProfile.id = :profileId THEN f.firstProfile.id END " +
            "FROM Friend f WHERE f.firstProfile.id = :profileId OR f.secondProfile.id = :profileId)")
    List<Profile> findAllFriendsByProfileId(@Param("profileId") Long profileId);

    // Truy vấn số lượng bạn bè tổng của profile bằng native SQL
    @Query(value = "SELECT COUNT(DISTINCT f.friend_id) FROM friends f WHERE (f.first_profile_id = :profileId OR f.second_profile_id = :profileId) AND f.is_active = 1", nativeQuery = true)
    int countTotalFriends(@Param("profileId") Long profileId);

    // Truy vấn số lượng bạn bè chung giữa 2 profile bằng native SQL
    @Query(value = """
            SELECT CASE
                    WHEN :profileId1 = :profileId2 THEN 0
                    ELSE COUNT(*)\s
                   END AS mutual_friend_count
            FROM (
              SELECT\s
                CASE\s
                  WHEN first_profile_id = :profileId1 THEN second_profile_id
                  WHEN second_profile_id = :profileId1 THEN first_profile_id
                END AS friend_id
              FROM friends
              WHERE (:profileId1 = first_profile_id OR :profileId1 = second_profile_id) AND is_active = 1
            ) AS friends_of_id1
            JOIN (
              SELECT\s
                CASE\s
                  WHEN first_profile_id = :profileId2 THEN second_profile_id
                  WHEN second_profile_id = :profileId2 THEN first_profile_id
                END AS friend_id
              FROM friends
              WHERE (:profileId2 = first_profile_id OR :profileId2 = second_profile_id) AND is_active = 1
            ) AS friends_of_id2
            ON friends_of_id1.friend_id = friends_of_id2.friend_id;
                    """, nativeQuery = true)
    int countMutualFriendsByPathNameAndProfileId(
            @Param("profileId1") Long profileId1,
            @Param("profileId2") Long profileId2
    );
}
