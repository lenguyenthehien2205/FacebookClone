package com.project.facebook.repositories;

import com.project.facebook.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // Truy vấn thông tin profile theo pathName
    // Truy vấn thông tin profile theo pathName bằng native SQL
    @Query(value = "SELECT p.* FROM profiles p JOIN page_bases pb ON p.base_id = pb.id WHERE pb.path_name = :pathName", nativeQuery = true)
    Profile findProfileByPathName(@Param("pathName") String pathName);

    @Query(value = """
            SELECT\s
              CASE\s
                WHEN f.sender_id = :profile_id THEN f.receiver_id
                ELSE f.sender_id
              END AS friend_id
            FROM friends f
            WHERE (:profile_id = f.sender_id OR :profile_id = f.receiver_id)\s
              AND f.is_active = 1
            LIMIT :limit;
            """, nativeQuery = true)
    List<Long> getProfileIdFriends(@Param("profile_id") Long profileId, @Param("limit") int limit);
    @Query(value = "SELECT pb.avatar FROM profiles p JOIN page_bases pb ON p.base_id = pb.id WHERE p.id IN :friendIds", nativeQuery = true)
    List<String> getAvatarsByFriendIds(@Param("friendIds") List<Long> friendIds);

    @Query(value = "SELECT p.* FROM profiles p " +
            "WHERE p.first_name LIKE %:keyword% " +
            "OR p.last_name LIKE %:keyword% " +
            "OR CONCAT(p.first_name, ' ', p.last_name) LIKE %:keyword% "+
            "OR CONCAT(p.last_name, ' ', p.first_name) LIKE %:keyword%", nativeQuery = true)
    List<Profile> searchByKeyWord(String keyword);
}
