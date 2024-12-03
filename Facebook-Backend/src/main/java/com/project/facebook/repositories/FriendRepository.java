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
//    @Query(value = "SELECT p.* FROM profiles p WHERE p.id IN (" +
//            "SELECT CASE " +
//            "    WHEN f.first_profile_id = :profileId THEN f.second_profile_id " +
//            "    WHEN f.second_profile_id = :profileId THEN f.first_profile_id " +
//            "END " +
//            "FROM friends f " +
//            "WHERE f.first_profile_id = :profileId OR f.second_profile_id = :profileId)",
//            nativeQuery = true)
//    List<Profile> findAllFriendsByProfileId(@Param("profileId") Long profileId);
}
