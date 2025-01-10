package com.project.facebook.repositories;

import java.util.List;
import java.util.Optional;

import com.project.facebook.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.facebook.models.Friend;
import com.project.facebook.models.Profile;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END " +
            "FROM friends f " +
            "WHERE (f.sender_id = :senderId AND f.receiver_id = :receiverId) " +
            "OR (f.sender_id = :receiverId AND f.receiver_id = :senderId)", nativeQuery = true)
    int existsFriendship(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    @Query("SELECT p FROM Profile p WHERE p.id IN " +
            "(SELECT CASE WHEN f.senderProfile.id = :profileId THEN f.receiverProfile.id " +
            "            WHEN f.receiverProfile.id = :profileId THEN f.senderProfile.id END " +
            "FROM Friend f WHERE f.senderProfile.id = :profileId OR f.receiverProfile.id = :profileId)")
    List<Profile> findAllFriendsByProfileId(@Param("profileId") Long profileId);

    // Truy vấn số lượng bạn bè tổng của profile bằng native SQL
    @Query(value = "SELECT COUNT(DISTINCT f.friend_id) FROM friends f WHERE (f.sender_id = :profileId OR f.receiver_id = :profileId) AND f.is_active = 1", nativeQuery = true)
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
                  WHEN sender_id = :profileId1 THEN receiver_id
                  WHEN receiver_id = :profileId1 THEN sender_id
                END AS friend_id
              FROM friends
              WHERE (:profileId1 = sender_id OR :profileId1 = receiver_id) AND is_active = 1
            ) AS friends_of_id1
            JOIN (
              SELECT\s
                CASE\s
                  WHEN sender_id = :profileId2 THEN receiver_id
                  WHEN receiver_id = :profileId2 THEN sender_id
                END AS friend_id
              FROM friends
              WHERE (:profileId2 = sender_id OR :profileId2 = receiver_id) AND is_active = 1
            ) AS friends_of_id2
            ON friends_of_id1.friend_id = friends_of_id2.friend_id;
                    """, nativeQuery = true)
    int countMutualFriendsByPathNameAndProfileId(
            @Param("profileId1") Long profileId1,
            @Param("profileId2") Long profileId2
    );

    // neu dung native query thi phai dung projection moi tra ve duoc entity, hoac dung JPQL
    @Query("SELECT f FROM Friend f WHERE " +
            "(f.senderProfile.id = :profileId1 AND f.receiverProfile.id = :profileId2) " +
            "OR (f.receiverProfile.id = :profileId1 AND f.senderProfile.id = :profileId2)")
    Optional<Friend> findFriendshipByUsers(@Param("profileId1") Long profileId1, @Param("profileId2") Long profileId2);
}
